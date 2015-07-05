package gaViz.crossover;
import java.util.ArrayList;
import java.util.List;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;


public class CrossoverGenenome implements ICrossover{

	//crossover once per gene
	public void crossover(Population p) {
		
		List<Individual> childPopulation = new ArrayList<Individual>();
		
		for (int i = 0; i < p.getSize(); i += 2) {
			Individual parent1 = p.getIndividual(i + 0);
			Individual parent2 = p.getIndividual(i + 1);
			List<Integer> childGenome1 = new ArrayList<Integer>();
			List<Integer> childGenome2 = new ArrayList<Integer>();
			//Individual child1 = new Individual( p.getNumGenes() );
			//Individual child2 = new Individual( p.getNumGenes() );
			for (int j = 0; j < p.getNumGenes(); j++) {
				String p1 = BinaryStringHelper.intToBinaryString(parent1.getGene(j));
				String p2 = BinaryStringHelper.intToBinaryString(parent2.getGene(j));
				int crossoverIndex = 1 + (int) Math.floor( (p1.length() - 1) * Math.random() );
				String p1_1 = p1.substring(0, crossoverIndex);
				String p1_2 = p1.substring(crossoverIndex, p1.length());
				String p2_1 = p2.substring(0, crossoverIndex);
				String p2_2 = p2.substring(crossoverIndex, p2.length());
				String c1 = p1_1 + p2_2;
				String c2 = p2_1 + p1_2;
				//child1.setGene( j, BinaryStringHelper.binaryStringToInt(c1) );
				//child2.setGene( j, BinaryStringHelper.binaryStringToInt(c2) );
				childGenome1.add(BinaryStringHelper.binaryStringToInt(c1));
				childGenome2.add(BinaryStringHelper.binaryStringToInt(c2));
			}
			//Individual child1 = new Individual(childGenome1);
			//Individual child2 = new Individual(childGenome2);
			//p.setIndividual( i + 0, new Individual(childGenome1) );
			//p.setIndividual( i + 1, new Individual(childGenome2) );
			childPopulation.add(new Individual(childGenome1));
			childPopulation.add(new Individual(childGenome2));
		}
		//return new Population(childPopulation);
		p.setIndividuals(childPopulation);
	}
	
	/*
	//crossover once per gene
	public void crossover(Population p) {
		for (int i = 0; i < p.getSize(); i += 2) {
			Individual parent1 = p.getIndividual(i + 0);
			Individual parent2 = p.getIndividual(i + 1);
			List<Integer> childGenome1 = new ArrayList<Integer>();
			List<Integer> childGenome2 = new ArrayList<Integer>();
			Individual child1 = new Individual( p.getNumGenes() );
			Individual child2 = new Individual( p.getNumGenes() );
			for (int j = 0; j < p.getNumGenes(); j++) {
				String p1 = BinaryStringHelper.intToBinaryString(parent1.getGene(j));
				String p2 = BinaryStringHelper.intToBinaryString(parent2.getGene(j));
				int crossoverIndex = 1 + (int) Math.floor( (p1.length() - 1) * Math.random() );
				String p1_1 = p1.substring(0, crossoverIndex);
				String p1_2 = p1.substring(crossoverIndex, p1.length());
				String p2_1 = p2.substring(0, crossoverIndex);
				String p2_2 = p2.substring(crossoverIndex, p2.length());
				String c1 = p1_1 + p2_2;
				String c2 = p2_1 + p1_2;
				child1.setGene( j, BinaryStringHelper.binaryStringToInt(c1) );
				child2.setGene( j, BinaryStringHelper.binaryStringToInt(c2) );
			}
			p.setIndividual(i + 0, child1);
			p.setIndividual(i + 1, child2);
		}
	}
	*/
	
}
