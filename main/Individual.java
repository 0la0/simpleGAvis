package main;

public class Individual {

	public int numGenes;
	public int[] genome;
	public int rawFitness = 0;
	public float probability = 0;
	public float cumulativeLowerBound;
	public float cumulativeUpperBound;
	
	public Individual (int numGenes) {
		this.numGenes = numGenes;
		this.genome = new int[numGenes];
	}
	
	public Individual (int numGenes, int maxVal) {
		this.numGenes = numGenes;
		this.genome = new int[numGenes];
		//initialize individual with random values
		for (int i = 0; i < numGenes; i++) {
			this.genome[i] = (int) Math.floor( maxVal * Math.random() );
		}
	}
	
	//initialize individual with set values
	public Individual (int[] genome) {
		this.numGenes = genome.length;
		this.genome = genome;
	}
	
	public int getGeneSum () {
		int sum = 0;
		for (int i = 0; i < this.genome.length; i++) {
			sum += this.genome[i];
		}
		return sum;
	}
	
}
