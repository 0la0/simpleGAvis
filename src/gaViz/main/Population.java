package gaViz.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Population {
	
	private int numGenes;
	private int size;
	private List<Individual> individuals;
	private int totalFitness = 0;
	
	public Population (int size, int numGenes) {
		this.size = size;
		this.numGenes = numGenes;
		this.individuals = new ArrayList<Individual>();
	}
	
	public Population (int size, int numGenes, int maxSize) {
		this.size = size;
		this.numGenes = numGenes;
		this.individuals = new ArrayList<Individual>();
		for (int i = 0; i < this.size; i++) {
			this.individuals.add(new Individual(numGenes, maxSize));
		}
	}
	
	public Population (List<Individual> individuals) {
		this.size = individuals.size();
		this.numGenes = individuals.get(0).getNumGenes();
		this.individuals = individuals;
	}

	public int getNumGenes () {
		return this.numGenes;
	}
	
	public int getSize () {
		return this.size;
	}
	
	public List<Individual> getIndividuals () {
		return this.individuals;
	}
	
	public Individual getIndividual (int index) {
		if (index < 0 || index >= this.size) return null;
		return this.individuals.get(index);
	}
	
	public void setIndividual (int index, Individual individual) {
		if (individual == null) {
			System.out.println("Population.setIndividual error: cannot set null value");
			return;
		}
		if (index < 0 || index >= this.individuals.size()) {
			System.out.println("Population.setIndividual error: indexOutOfBounds");
			return;
		}
		this.individuals.set(index, individual);
	}
	
	public void setIndividuals (List<Individual> individuals) {
		this.individuals = individuals;
		this.size = individuals.size();
	}
	
	public int getTotalFitness () {
		return this.totalFitness;
	}
	
	public void setTotalFitness (int totalFitness) {
		this.totalFitness = totalFitness;
	}
	
	public Individual getProbabilisticIndividual () {
		double randVal = Math.random();
		for (int i = 0; i < size; i++) {
			if (randVal >= this.individuals.get(i).getCumulativeLowerBound() && 
				randVal <= this.individuals.get(i).getCumulativeUpperBound() ) {
				return this.individuals.get(i);
			}
		}
		return this.individuals.get(this.individuals.size() - 1);
	}
	
	public void sort (Comparator compare) {
		Collections.sort(this.individuals, compare);
	}
	
	public String toString () {
		String about = "Population size: " + this.size + "\n";
		about += "Number of genes per individual: " + this.numGenes + "\n";
		about += "Total raw fitness of population: " + this.totalFitness + "\n";
		return about;
	}
	
	public Population copy () {
		List<Individual> individualClone = this.individuals.stream()
				.map(individual -> individual.copy())
				.collect(Collectors.toList());
		return new Population(individualClone);
	}
	
}
