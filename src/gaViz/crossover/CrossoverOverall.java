package gaViz.crossover;
import java.util.ArrayList;
import java.util.List;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;


public class CrossoverOverall implements ICrossover{

	//perform one crossover across all genes
	public void crossover(Population p) {
		List<Individual> childPopulation = new ArrayList<Individual>();
		int geneLength = BinaryStringHelper.geneLength;
		int numGenes = p.getNumGenes();
		for (int i = 0; i < p.getSize(); i += 2) {
			Individual parent1 = p.getIndividual(i + 0);
			Individual parent2 = p.getIndividual(i + 1);
			Individual child1 = new Individual(numGenes);
			Individual child2 = new Individual(numGenes);
			String parentString1 = "";
			String parentString2 = "";
			for (int j = 0; j < numGenes; j++) {
				parentString1 += BinaryStringHelper.intToBinaryString(parent1.getGene(j));
				parentString2 += BinaryStringHelper.intToBinaryString(parent2.getGene(j));
			}
			int crossoverIndex = 1 + (int) Math.floor( (parentString1.length() - 1) * Math.random());
			String p1_1 = parentString1.substring(0, crossoverIndex);
			String p1_2 = parentString1.substring(crossoverIndex, parentString1.length());
			String p2_1 = parentString2.substring(0, crossoverIndex);
			String p2_2 = parentString2.substring(crossoverIndex, parentString2.length());
			String c1 = p1_1 + p2_2;
			String c2 = p1_2 + p2_1;
			for (int j = 0; j < numGenes; j++) {
				int gene1 = BinaryStringHelper.binaryStringToInt(c1.substring(j * geneLength, (j * geneLength) + geneLength));
				int gene2 = BinaryStringHelper.binaryStringToInt(c2.substring(j * geneLength, (j * geneLength) + geneLength));
				
				child1.setGene( j, gene1 );
				child2.setGene( j, gene2 );
			}
			
			childPopulation.add(child1);
			childPopulation.add(child2);
		}
		p.setIndividuals(childPopulation);
	}

}
