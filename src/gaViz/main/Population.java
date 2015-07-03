package gaViz.main;

import java.util.Arrays;
import java.util.Comparator;


public class Population {
	
	private int numGenes;
	private int size;
	private Individual[] individuals;
	private int totalFitness = 0;
	
	public Population (int size, int numGenes) {
		this.size = size;
		this.numGenes = numGenes;
		this.individuals = new Individual[size];
	}
	
	public Population (int size, int numGenes, int maxSize) {
		this.size = size;
		this.numGenes = numGenes;
		this.individuals = new Individual[size];
		for (int i = 0; i < this.size; i++) {
			individuals[i] = new Individual(numGenes, maxSize);
		}
	}


	//private int numGenes;
	public int getNumGenes () {
		return this.numGenes;
	}
	
	//private int size;
	public int getSize () {
		return this.size;
	}
	
	public Individual[] getIndividuals () {
		return this.individuals;
	}
	
	public Individual getIndividual (int index) {
		if (index < 0 || index >= this.size) return null;
		return this.individuals[index];
	}
	
	public void setIndividual (int index, Individual individual) {
		if (individual == null) {
			System.out.println("Population.setIndividual error: cannot set null value");
			return;
		}
		if (index < 0 || index >= this.individuals.length) {
			System.out.println("Population.setIndividual error: indexOutOfBounds");
			return;
		}
		this.individuals[index] = individual;
	}
	
	public void setIndividuals (Individual[] individuals) {
		this.individuals = individuals;
		this.size = individuals.length;
	}
	
	public int getTotalFitness () {
		return this.totalFitness;
	}
	
	public void setTotalFitness (int totalFitness) {
		this.totalFitness = totalFitness;
	}
	
	public void addIndividual (int index, Individual individual) {
		if (index < 0 || index >= size) {
			System.out.println("err: addIndividual index outOfBounds");
			return;
		}
		this.individuals[index] = individual;
	}
	
	public Individual getProbabilisticIndividual () {
		float randVal = (float) Math.random();
		for (int i = 0; i < size; i++) {
			if (randVal >= this.individuals[i].getCumulativeLowerBound() && 
				randVal <= this.individuals[i].getCumulativeUpperBound() ) {
				return this.individuals[i];
			}
		}
		return this.individuals[this.individuals.length - 1];
	}
	
	public void sort (Comparator compare) {
		Arrays.sort(this.individuals, compare);
	}
	
	public String toString () {
		String about = "Population size: " + this.size + "\n";
		about += "Number of genes per individual: " + this.numGenes + "\n";
		about += "Total raw fitness of population: " + this.totalFitness + "\n";
		return about;
	}
	
}
