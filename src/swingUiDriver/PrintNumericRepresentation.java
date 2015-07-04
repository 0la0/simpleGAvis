package swingUiDriver;
import javax.swing.JTextArea;

import gaViz.main.Population;


public class PrintNumericRepresentation implements IPrint{

	public void printPopulation (Population p, JTextArea textArea) {
		for (int i = 0; i < p.getSize(); i++) {
			textArea.append(new String(String.format("indiv: %1d ", i)));
			for (int j = 0; j < p.getNumGenes(); j++) {
				textArea.append(new String(String.format("gene %1d: %3d ", j, p.getIndividual(i).getGene(j) )));
			}
			textArea.append(new String(String.format("fitness %4d: ", p.getIndividual(i).getRawFitness() )));
			textArea.append(new String(String.format("probability %4.3f: ", p.getIndividual(i).getProbability() )));
			textArea.append(new String(String.format("LB %4.3f: UB %4.3f: \n", p.getIndividual(i).getCumulativeLowerBound(), p.getIndividual(i).getCumulativeUpperBound() )));
		}
		textArea.append(new String(String.format("population fitness %3d: ", p.getTotalFitness() )));
	}
	
}
