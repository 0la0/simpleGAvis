package gaViz.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class StaticImageDriver {

	private Options options;
	private int goal;
	private ArrayList<Population> generations = new ArrayList<Population>();
	
	public StaticImageDriver (Options options) {
		this.options = options;
		this.goal = (int) Math.pow(2, this.options.geneLength);
		BinaryStringHelper.setStringLength(this.options.geneLength);
		this.run();
		this.sortPopulations();
		this.displayImage();
	}
	
	public void run () {
		// init population
		Population p = new Population(this.options.populationSize, this.options.numGenes, this.goal);
		this.options.fitnessObj.calcFitness(p);
		this.options.probabilityObj.calc(p);
		Population parent = p;
		Population child;
		generations.add(parent);
		int cnt = 0;
		while (cnt++ < this.options.numGenerations) {
			child = this.options.breederObj.breed(parent);
			this.options.crossoverObj.crossover(child);
			this.options.mutateObj.mutate(child);
			this.options.fitnessObj.calcFitness(child);
			this.options.probabilityObj.calc(child);
			generations.add(child);
			parent = child;
		}
	}
	
	private void sortPopulations () {
		Comparator c = new PopulationComparator();
		for (Population p : this.generations) {
			p.sort(c);
		}
	}
	
	public void displayImage () {
		int w = 500;
		int h = 500;
		BufferedImage img = null;
		switch (this.options.numGenes) {
		case 1:
			img = this.getBufferedImageGrey();
			break;
		case 2:
			img = this.getBufferedImageXY();
			break;
		case 3:
			img = this.getBufferedImageRGB();
			break;
		case 5:
			img = this.getBufferedImageRGBXY();
			break;
		default:
			System.out.println("only available representations for number of genes: [1-3] and 5");
			return;
		}
		ImgPanel imgPanel = new ImgPanel(w, h, img);
		imgPanel.setMinimumSize(new Dimension(w, h));
		imgPanel.setPreferredSize(new Dimension(w, h));
		
		JFrame imgFrame = new JFrame();
		imgFrame.add(imgPanel);
		imgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgFrame.setSize(w, h);
		imgFrame.pack();
		imgFrame.setLocation(200, 30);
		imgFrame.setVisible(true);
	}
	
	public BufferedImage getBufferedImageGrey () {
		int w = this.options.populationSize;
		int h = this.options.numGenerations;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  w,  h);
	    g.clearRect(0, 0, w, h);
	    
		for (int i = 0; i < this.generations.size(); i++) {
			Population p = this.generations.get(i);
			for (int j = 0; j < p.getSize(); j++) {
				float normalVal = (float) (p.getIndividual(j).genome[0] / (this.goal * 1.0));
				int val = (int) Math.floor(255 * normalVal);
				g.setColor(new Color(val, val, val));
				g.fillRect(j, i, 1, 1);
			}
		}
		return img;
	}
	
	public BufferedImage getBufferedImageRGB () {
		int w = this.options.populationSize;
		int h = this.options.numGenerations;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  w,  h);
	    g.clearRect(0, 0, w, h);
	    
		for (int i = 0; i < this.generations.size(); i++) {
			Population p = this.generations.get(i);
			for (int j = 0; j < p.getSize(); j++) {
				float normalR = (float) (p.getIndividual(j).genome[0] / (this.goal * 1.0));
				float normalG = (float) (p.getIndividual(j).genome[1] / (this.goal * 1.0));
				float normalB = (float) (p.getIndividual(j).genome[2] / (this.goal * 1.0));
				int red = (int) Math.floor(255 * normalR);
				int green = (int) Math.floor(255 * normalG);
				int blue = (int) Math.floor(255 * normalB);
				g.setColor(new Color(red, green, blue));
				g.fillRect(j, i, 1, 1);
			}
		}
		return img;
	}
	
	public BufferedImage getBufferedImageXY () {
		int w = BinaryStringHelper.maxVal;
		int h = BinaryStringHelper.maxVal;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  w,  h);
	    g.setPaint(new Color(255, 255, 255, 100));
		for (int i = 0; i < this.generations.size(); i++) {
			Population p = this.generations.get(i);
			for (int j = 0; j < p.getSize(); j++) {
				float normalX = (float) (p.getIndividual(j).genome[0] / (this.goal * 1.0));
				float normalY = (float) (p.getIndividual(j).genome[1] / (this.goal * 1.0));
				int x = (int) Math.floor(w * normalX);
				int y = (int) Math.floor(h * normalY);
				g.fillRect(x, y, 1, 1);
			}
		}
		return img;
	}
	
	public BufferedImage getBufferedImageRGBXY () {
		int w = BinaryStringHelper.maxVal;
		int h = BinaryStringHelper.maxVal;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.createGraphics();
		/*
		 * ADD OPTION TO CHANGE BACKGROUND - DO BLACK IF NO COLOR GIVEN
		 */
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  w,  h);
		for (int i = 0; i < this.generations.size(); i++) {
			Population p = this.generations.get(i);
			for (int j = 0; j < p.getSize(); j++) {
				float normalR = (float) (p.getIndividual(j).genome[0] / (this.goal * 1.0));
				float normalG = (float) (p.getIndividual(j).genome[1] / (this.goal * 1.0));
				float normalB = (float) (p.getIndividual(j).genome[2] / (this.goal * 1.0));
				float normalX = (float) (p.getIndividual(j).genome[3] / (this.goal * 1.0));
				float normalY = (float) (p.getIndividual(j).genome[4] / (this.goal * 1.0));
				int red = (int) Math.floor(255 * normalR);
				int green = (int) Math.floor(255 * normalG);
				int blue = (int) Math.floor(255 * normalB);
				int x = (int) Math.floor(w * normalX);
				int y = (int) Math.floor(h * normalY);
				g.setPaint(new Color(red, green, blue, 100));
				g.fillRect(x, y, 1, 1);
			}
		}
		return img;
	}
	
	private class ImgPanel extends JPanel {
		
		private BufferedImage img;
		private int w;
		private int h;
		
		public ImgPanel(int w, int h, BufferedImage img){
			this.w = w;
			this.h = h;
			this.img = img;
			repaint();
		}
		
		protected void paintComponent (Graphics g) {
			g.drawImage(this.img, 0, 0, w, h, null);
		}
	}
	
}
