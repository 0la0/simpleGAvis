package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


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
	
	public AnimationDriver (Options options) {
		this.options = options;
		this.goal = (int) Math.pow(2, this.options.geneLength);
		
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
		
		switch (child.numGenes) {
		case 2:
			this.animateXY();
			break;
		case 3:
			//--individuals as columns--//
			this.animateRGBcolumns();
			if (this.timerCnt % 500 == 0) {
				float r = (float) Math.random();
				float g = (float) Math.random();
				float b = (float) Math.random();
				this.options.fitnessObj.setGoal(new float[]{r, g, b});
				System.out.println("Goal color set to: " + r + ", " + g + ", " + b);
			}
			break;
		case 5:
			this.animateRGBXY();
			break;
		default:
			System.out.println("animation driver not availabe for this number of genes");
			imgFrame.dispose();
			try {
				this.timer.stop();
			} catch (NullPointerException e) {
				
			}
			
		}
		this.timerCnt++;
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
		
		this.imgGraphics.setPaint(new Color(255, 255, 255, 10));

		for (int i = 0; i < this.child.individuals.length; i++) {
			float normalX = (float) (this.child.individuals[i].genome[0] / (goal * 1.0));
			float normalY = (float) (this.child.individuals[i].genome[1] / (goal * 1.0));
			
			int x = (int) Math.floor(w * normalX);
			int y = (int) Math.floor(h * normalY);
			this.imgGraphics.fillRect(x, y, 1, 1);
		}
		animationPanel.setBufferedImage(this.img);
	}
	
	/*
	 * Three genes - mapped as RGB, individuals are columns
	 */
	private void animateRGBcolumns () {
		
		if (this.img == null) {
			int w = (int) (child.size * 2);
			int h = 1;
			this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.imgGraphics = this.img.createGraphics();
		}
		
		this.child.sort(this.populationSort);
		for (int i = 0; i < this.child.individuals.length; i++) {
			float normalR = (float) (this.child.individuals[i].genome[0] / (this.goal * 1.0));
			float normalG = (float) (this.child.individuals[i].genome[1] / (this.goal * 1.0));
			float normalB = (float) (this.child.individuals[i].genome[2] / (this.goal * 1.0));
			int red = (int) Math.floor(255 * normalR);
			int green = (int) Math.floor(255 * normalG);
			int blue = (int) Math.floor(255 * normalB);
			this.imgGraphics.setColor(new Color(red, green, blue));

			this.imgGraphics.fillRect(i + this.child.size, 0, 1, 1);
			this.imgGraphics.fillRect(this.child.size - i, 0, 1, 1);
		}
		animationPanel.setBufferedImage(this.img);
	}
	
	/*
	 * Five genes, mapped to RGB, then x and y position in image plane
	 */
	private void animateRGBXY () {
		int w = BinaryStringHelper.maxVal;
		int h = BinaryStringHelper.maxVal;
		if (this.img == null) {
			this.img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			this.imgGraphics = this.img.createGraphics();
			this.imgGraphics.setColor(Color.BLACK);
			this.imgGraphics.fillRect(0,  0,  w,  h);
		}
		for (int i = 0; i < this.child.individuals.length; i++) {		
			float normalR = (float) (this.child.individuals[i].genome[0] / (this.goal * 1.0));
			float normalG = (float) (this.child.individuals[i].genome[1] / (this.goal * 1.0));
			float normalB = (float) (this.child.individuals[i].genome[2] / (this.goal * 1.0));
			float normalX = (float) (this.child.individuals[i].genome[3] / (this.goal * 1.0));
			float normalY = (float) (this.child.individuals[i].genome[4] / (this.goal * 1.0));
			
			int red = (int) Math.floor(255 * normalR);
			int green = (int) Math.floor(255 * normalG);
			int blue = (int) Math.floor(255 * normalB);
			int x = (int) Math.floor(w * normalX);
			int y = (int) Math.floor(h * normalY);
			
			this.imgGraphics.setPaint(new Color(red, green, blue, 10));
			this.imgGraphics.fillRect(x, y, 2, 2);
		}
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
		imgFrame.setLocation(200, 30);
		imgFrame.setVisible(true);
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
		
		public void setBufferedImage (BufferedImage img) {
			this.img = img;
			this.repaint();
		}
		
		protected void paintComponent (Graphics g) {
			g.drawImage(this.img, 0, 0, w, h, null);
		}
		
	}
	
}
