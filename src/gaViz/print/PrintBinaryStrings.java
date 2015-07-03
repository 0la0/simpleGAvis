package gaViz.print;
import javax.swing.JTextArea;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Population;


public class PrintBinaryStrings implements IPrint{

	public void printPopulation(Population p, JTextArea textArea) {
		for (int i = 0; i < p.getSize(); i++) {
			textArea.append(new String(String.format("indiv: %1d", i)));
			for (int j = 0; j < p.getNumGenes(); j++) {
				textArea.append(new String(String.format(" gene " + j + ": " + BinaryStringHelper.intToBinaryString(p.getIndividual(i).genome[j]))));
			}
			textArea.append(new String(String.format(" fitness %4d: \n",p.getIndividual(i).rawFitness)));
		}
		textArea.append(new String(String.format( "population fitness %3d: ", p.getTotalFitness() )));
	}

}
