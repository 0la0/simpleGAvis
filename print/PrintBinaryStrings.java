package print;
import javax.swing.JTextArea;

import main.BinaryStringHelper;
import main.Population;


public class PrintBinaryStrings implements IPrint{

	public void printPopulation(Population p, JTextArea textArea) {
		for (int i = 0; i < p.size; i++) {
			textArea.append(new String(String.format("indiv: %1d", i)));
			for (int j = 0; j < p.numGenes; j++) {
				textArea.append(new String(String.format(" gene " + j + ": " + BinaryStringHelper.intToBinaryString(p.individuals[i].genome[j]))));
			}
			textArea.append(new String(String.format(" fitness %4d: \n",p.individuals[i].rawFitness)));
		}
		textArea.append(new String(String.format("population fitness %3d: ", p.totalFitness)));
	}

}
