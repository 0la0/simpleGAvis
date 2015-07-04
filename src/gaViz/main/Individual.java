package gaViz.main;

import java.util.Arrays;

public class Individual {

	private int numGenes;
	private int[] genome;
	private int rawFitness = 0;
	private float probability = 0;
	private float cumulativeLowerBound;
	private float cumulativeUpperBound;
	
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
	
	public int getNumGenes () {
		return this.numGenes;
	}
	
	public int[] getGenome () {
		return this.genome;
	}
	
	public int getRawFitness () {
		return this.rawFitness;
	}
	
	public void setRawFitness (int rawFitness) {
		this.rawFitness = rawFitness;
	}
	
	public float getProbability () {
		return this.probability;
	}
	
	public void setProbability (float probability) {
		this.probability = probability;
	}
	
	public float getCumulativeLowerBound () {
		return this.cumulativeLowerBound;
	}
	
	public void setCumulativeLowerBound (float lb) {
		this.cumulativeLowerBound = lb;
	}
	
	public float getCumulativeUpperBound () {
		return this.cumulativeUpperBound;
	}
	
	public void setCumulativeUpperBound (float ub) {
		this.cumulativeUpperBound = ub;
	}
	public int getGene (int index) {
		if (index < 0 || index >= this.genome.length) {
			System.out.println("Individual.getGene indexOutOfBounds");
			return 0;
		}
		return this.genome[index];
	}
	
	public void setGene (int index, int val) {
		if (index < 0 || index >= this.genome.length) {
			System.out.println("Individual.setGene indexOutOfBounds");
		}
		this.genome[index] = val;
	}
	
	public int getGeneSum () {
		return Arrays.stream(this.genome).sum();
	}
	
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.genome.length; i++) {
			sb.append("gene " + i + ": ");
			sb.append(BinaryStringHelper.intToBinaryString(this.genome[i]));
		}
		return sb.toString();
	}
	
}
