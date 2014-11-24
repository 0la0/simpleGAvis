package main;

import java.util.Arrays;
import java.util.Comparator;


public class Population {
	
	public int numGenes;
	public int size;
	public Individual[] individuals;
	public int totalFitness = 0;
	
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
			if (randVal >= this.individuals[i].cumulativeLowerBound && 
				randVal <= this.individuals[i].cumulativeUpperBound) {
				return this.individuals[i];
			}
		}
		return null;
	}
	
	public void sort (Comparator compare) {
		/*
		for (int i = 0; i < this.numGenes; i++) {
			Arrays.sort(this.individuals, new PopulationComparator());
		}
		*/
		Arrays.sort(this.individuals, compare);
	}
	
	public String toString () {
		String about = "Population size: " + this.size + "\n";
		about += "Number of genes per individual: " + this.numGenes + "\n";
		about += "Total raw fitness of population: " + this.totalFitness + "\n";
		return about;
	}
	
}
