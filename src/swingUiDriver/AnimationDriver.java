package swingUiDriver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gaViz.fitness.FitnessCustomGoal;
import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Options;
import gaViz.main.Population;
import gaViz.main.PopulationComparator;


public class AnimationDriver {

	private Options options;
	private int goal;
	private AnimationPanel animationPanel;
	private Population parent; 
	private Population child;
	private Timer timer;
	private int timerCnt = 1;
	private BufferedImage img = null;
	private Graphics2D imgGraphics = null;
	private int animationStepTime = 17;
	private Comparator populationSort = new PopulationComparator();
	private JFrame imgFrame = new JFrame();
	private boolean isCustomGoal = false;
	private boolean isFullscreen = false;
	private int prevX = 200; //---location of JFrame
	private int prevY = 30;	 //---location of JFrame
	private int prevWidth = 500; //---dimension of JFrame
	private int prevHeight = 500;//---dimension of JFrame
	private boolean renderGoal = true;
	
	public AnimationDriver (Options options) {
		this.options = options;
		this.goal = (int) Math.pow(2, this.options.geneLength);
		
		if (this.options.fitnessObj instanceof FitnessCustomGoal) {
			this.isCustomGoal = true;
		}
		
		this.buildAnimationFrame(500, 500);
		this.initPopulation();
		this.generate();
		
		this.timer = new Timer(animationStepTime, new ActionListener() {
	        public void actionPerformed(ActionEvent evt) {
	    		generate();
	        }
	    });
	    this.timer.start();
	}
	
	
	private void initPopulation () {
		parent = new Population(this.options.populationSize, this.options.numGenes, this.goal);
		this.options.fitnessObj.calcFitness(parent);
		this.options.probabilityObj.calc(parent);
	}
	
	private void createNewGoal () {
		double[] nGoal = new double[this.child.getNumGenes()];
		for (int i = 0; i < nGoal.length; i++) {
			nGoal[i] = Math.random();
		}
		this.options.fitnessObj.setGoal(nGoal);
	}
	
	private void generate () {
		//long startTime = System.nanoTime();
		
		//------------------------------------------//
		child = this.options.breederObj.breed(parent);
		this.options.crossoverObj.crossover(child);
		this.options.mutateObj.mutate(child);
		this.options.fitnessObj.calcFitness(child);
		this.options.probabilityObj.calc(child);
		parent = child;	
		//------------------------------------------//
		
		switch (child.getNumGenes()) {
		case 2:
			this.animateXY();
			break;
		case 3:
			this.animateRGBcolumns();
			break;
		case 5:
			this.animateRGBXY();
			break;
		default:
			System.out.println("animation driver not availabe for this number of genes");
			imgFrame.dispose();
			try {
				this.timer.stop();
			} catch (NullPointerException e) {}
		}
		
		if (this.timerCnt++ % 400 == 0) this.createNewGoal();
		//System.out.println("Total iteration time: " + ((System.nanoTime() - startTime) / 1000000.0) );
	}
	
	/*
	 * Two genes, mapped to x and y position in image plane
	 */
	private void animateXY () {
		int w = BinaryStringHelper.maxVal;
		int h = BinaryStringHelper.maxVal;
		if (this.img == null) {
			this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.imgGraphics = this.img.createGraphics();
			this.imgGraphics.setColor(Color.BLACK);
			this.imgGraphics.fillRect(0,  0,  w,  h);
		}
		
		this.imgGraphics.setColor(Color.BLACK);
		this.imgGraphics.fillRect(0,  0,  w,  h);
		
		if (this.isCustomGoal && this.renderGoal) {
			this.imgGraphics.setPaint(new Color(100, 100, 255));
			int[] goalPosition = this.options.fitnessObj.getGoal();
			this.imgGraphics.fillRect(goalPosition[0] - 5, goalPosition[1] - 5, 10, 10);
			this.imgGraphics.setPaint(new Color(255, 255, 255, 100));
		}
		
		this.child.getIndividuals().forEach(individual -> {
			double normalX = individual.getGene(0) / (goal * 1.0);
			double normalY = individual.getGene(1) / (goal * 1.0);
			
			int x = (int) Math.floor(w * normalX);
			int y = (int) Math.floor(h * normalY);
			
			this.imgGraphics.setPaint(new Color(255, 255, 255, 100));
			this.imgGraphics.fillRect(x - 1, y - 1, 2, 2);
		});
		
		animationPanel.setBufferedImage(this.img);
	}
	
	/*
	 * Three genes - mapped as RGB, individuals are columns
	 */
	private void animateRGBcolumns () {
		
		if (this.img == null) {
			int w = (int) (child.getSize() * 2);
			int h = 1;
			this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.imgGraphics = this.img.createGraphics();
		}
		
		this.child.sort(this.populationSort);
		
		for (int i = 0; i < this.child.getIndividuals().size(); i++) {
			Individual individual = this.child.getIndividual(i);
			/*
			int[] rgb = Arrays.stream(individual.getGenome()).map(gene -> {
				double normalVal = gene / (this.goal * 1.0);
				return (int) Math.floor(255 * normalVal);
			}).toArray();
			*/
			int[] rgb = individual.getGenome().stream().mapToInt(gene -> {
				double normalVal = gene / (this.goal * 1.0);
				return (int) Math.floor(255 * normalVal);
			}).toArray();

			this.imgGraphics.setColor(new Color(rgb[0], rgb[1], rgb[2]));
			this.imgGraphics.fillRect(i + this.child.getSize(), 0, 1, 1);
			this.imgGraphics.fillRect(this.child.getSize() - i, 0, 1, 1);
		}
		animationPanel.setBufferedImage(this.img);
	}
	
	/*
	 * Five genes, mapped to RGB, then x and y position in image plane
	 */
	private void animateRGBXY () {
		int w = BinaryStringHelper.maxVal;
		int h = BinaryStringHelper.maxVal;
		int opacityMult = 20;
		if (this.img == null) {
			this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.imgGraphics = this.img.createGraphics();
			this.imgGraphics.setColor(Color.BLACK);
			this.imgGraphics.fillRect(0,  0,  w,  h);
		}
		
		//---CLEAR BUFFERED IMAGE TO BLACK---//
		this.imgGraphics.setColor(Color.BLACK);
		this.imgGraphics.fillRect(0,  0,  w,  h);
		
		//---RENDER GOAL STATE---//
		if (this.isCustomGoal && this.renderGoal) {
			int[] goalPosition = this.options.fitnessObj.getGoal();
			this.imgGraphics.setPaint(new Color(
					goalPosition[0], goalPosition[1], goalPosition[2]));
			this.imgGraphics.fillRect(goalPosition[3] - 5, goalPosition[4] - 5, 10, 10);
			//opacityMult = 20;
		}
		
		this.child.getIndividuals().stream().forEach(individual -> {
			double[] normalVals = individual.getGenome().stream().mapToDouble(gene -> {
				return gene / (this.goal * 1.0);
			}).toArray();
			
			int red = (int) Math.floor(255 * normalVals[0]);
			int green = (int) Math.floor(255 * normalVals[1]);
			int blue = (int) Math.floor(255 * normalVals[2]);
			int x = (int) Math.floor(w * normalVals[3]);
			int y = (int) Math.floor(h * normalVals[4]);
			
			this.imgGraphics.setPaint(new Color(red, green, blue, 10 * opacityMult));
			this.imgGraphics.fillRect(x, y, 2, 2);
		});
		animationPanel.setBufferedImage(this.img);
	}
	
	private void buildAnimationFrame (int w, int h) {
		this.animationPanel = new AnimationPanel(w, h);
		this.animationPanel.setMinimumSize(new Dimension(w, h));
		this.animationPanel.setPreferredSize(new Dimension(w, h));
		
		imgFrame.add(this.animationPanel);
		imgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgFrame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent event) {
	           timer.stop();
	        }
	    });
		imgFrame.setSize(w, h);
		imgFrame.pack();
		imgFrame.setLocation(prevX, prevY);
		imgFrame.setVisible(true);
		
		//---JFRAME RESIZE LISTENER---//
		imgFrame.addComponentListener(new ComponentAdapter () {
			public void componentResized(ComponentEvent e) {
		    	prevWidth = imgFrame.getContentPane().getSize().width;;
		        prevHeight = imgFrame.getContentPane().getSize().height;
		        animationPanel.updateSize(prevWidth, prevHeight);
		    }
		});
		//---KEY LISTENERS---//
		//   -FULLSCREEN: SPACEBAR AND ESC
		//   -RE-INITIALIZE POPULATION (SCATTER): S
		//	 -TOGGLE GOAL STATE RENDERING: G
		imgFrame.addKeyListener(new KeyListener() {
        	public void keyPressed(KeyEvent arg0) {
        		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
        			toggleFullscreen();
        		}
        		else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
        			toggleFullscreen();
        		}
        		else if (arg0.getKeyCode() == KeyEvent.VK_S) {
        			initPopulation();
        		}
        		else if (arg0.getKeyCode() == KeyEvent.VK_G) {
        			renderGoal = !renderGoal;
        		}
        		else if (arg0.getKeyCode() == KeyEvent.VK_N) {
        			createNewGoal();
        		}
    		}
    		@Override
    		public void keyReleased(KeyEvent arg0) {}
    		@Override
    		public void keyTyped(KeyEvent arg0) {}
        });
	}
	
	private class AnimationPanel extends JPanel {
		
		private BufferedImage img;
		private int w;
		private int h;
		
		public AnimationPanel(int w, int h) {
			this.w = w;
			this.h = h;
		}
		
		public AnimationPanel(int w, int h, BufferedImage img){
			this.w = w;
			this.h = h;
			this.img = img;
		}
		
		//---JPanel already has setSize and resize method names---//
		public void updateSize (int w, int h) {
			this.w = w;
			this.h = h;
		}
		
		public void setBufferedImage (BufferedImage img) {
			this.img = img;
			this.repaint();
		}
		
		protected void paintComponent (Graphics g) {
			g.drawImage(this.img, 0, 0, w, h, null);
		}
	}
	
	private void toggleFullscreen(){
		if (!isFullscreen) {
			prevX = imgFrame.getX();
			prevY = imgFrame.getY();
			imgFrame.dispose(); 
			imgFrame.setUndecorated(true);
			imgFrame.setBounds(0, 0, imgFrame.getToolkit().getScreenSize().width, imgFrame.getToolkit().getScreenSize().height);
			imgFrame.setVisible(true);
        	isFullscreen = true;
        	animationPanel.updateSize(imgFrame.getToolkit().getScreenSize().width, imgFrame.getToolkit().getScreenSize().height);
		} else {
			prevWidth = 500;
			prevHeight = 500;
			imgFrame.setVisible(true);
			imgFrame.setBounds(prevX, prevY, prevWidth, prevHeight);
			imgFrame.dispose();
			imgFrame.setUndecorated(false);
			imgFrame.setVisible(true);
			isFullscreen = false;
			animationPanel.updateSize(prevWidth, prevHeight);
       }
	}
	
}
