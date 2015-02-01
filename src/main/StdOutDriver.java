package src.main;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.print.IPrint;


public class StdOutDriver {

	private Options options;
	private int goal;
	private IPrint printObj;
	private JTextArea textArea;
	
	public StdOutDriver (Options options, IPrint printObj) {
		this.options = options;
		this.options = options;
		this.goal = (int) Math.pow(2, this.options.geneLength);
		BinaryStringHelper.setStringLength(this.options.geneLength);
		this.printObj = printObj;
		this.createOutputUI();
		this.run();
	}
	
	public void run () {
		Population p = new Population(this.options.populationSize, this.options.numGenes, this.goal);
		this.options.fitnessObj.calcFitness(p);
		this.options.probabilityObj.calc(p);
		this.textArea.append("fitness function used: " + this.options.fitnessObj.toString() + "\n");
		
		this.printObj.printPopulation(p, this.textArea);
		Population parent = p;
		Population child;
		
		int cnt = 0;
		while (cnt++ < this.options.numGenerations) {
			child = this.options.breederObj.breed(parent);
			this.options.crossoverObj.crossover(child);
			this.options.fitnessObj.calcFitness(child);
			this.textArea.append("\npopulation fitness pre mutate: " + child.totalFitness);
			this.options.mutateObj.mutate(child);
			this.options.fitnessObj.calcFitness(child);
			this.options.probabilityObj.calc(child);
			
			this.textArea.append("\n\n\t generation number: " + (cnt) + "\n");
			this.printObj.printPopulation(child, this.textArea);
			parent = child;
		}
	}
	
	private void createOutputUI () {
		this.textArea = new JTextArea(40, 100);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		this.textArea.setEditable(false);
		
		JFrame imgFrame = new JFrame();
		imgFrame.add(scrollPane);
		imgFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		imgFrame.setSize(500, 500);
		imgFrame.pack();
		imgFrame.setLocation(10, 10);
		imgFrame.setVisible(true);
	}
	
}
