package print;
import javax.swing.JTextArea;

import main.Population;


public class PrintNumericRepresentation implements IPrint{

	public void printPopulation (Population p, JTextArea textArea) {
		for (int i = 0; i < p.size; i++) {
			textArea.append(new String(String.format("indiv: %1d ", i)));
			for (int j = 0; j < p.numGenes; j++) {
				textArea.append(new String(String.format("gene %1d: %3d ", j, p.individuals[i].genome[j])));
			}
			textArea.append(new String(String.format("fitness %4d: ", p.individuals[i].rawFitness)));
			textArea.append(new String(String.format("probability %4.3f: ", p.individuals[i].probability)));
			textArea.append(new String(String.format("LB %4.3f: UB %4.3f: \n", p.individuals[i].cumulativeLowerBound, p.individuals[i].cumulativeUpperBound)));
		}
		textArea.append(new String(String.format("population fitness %3d: ", p.totalFitness)));
	}
	
}
