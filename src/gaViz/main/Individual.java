package gaViz.main;

import java.util.ArrayList;
import java.util.List;

public class Individual {

	private int numGenes;
	private List<Integer> genome;
	//private int[] genome;
	private int rawFitness = 0;
	private double probability = 0;
	private double cumulativeLowerBound;
	private double cumulativeUpperBound;
	
	public Individual (int numGenes) {
		this.numGenes = numGenes;
		//this.genome = new int[numGenes];
		this.genome = new ArrayList<Integer>();
	}
	
	public Individual (int numGenes, int maxVal) {
		this.numGenes = numGenes;
		//this.genome = new int[numGenes];
		this.genome = new ArrayList<Integer>();
		//initialize individual with random values
		for (int i = 0; i < numGenes; i++) {
			int randomVal = (int) Math.floor( maxVal * Math.random() );
			this.genome.add(randomVal);
			//this.genome[i] = (int) Math.floor( maxVal * Math.random() );
		}
	}
	
	//initialize individual with set values
	public Individual (List<Integer> genome) {
		//this.numGenes = genome.length;
		this.numGenes = genome.size();
		this.genome = genome;
	}
	
	public int getNumGenes () {
		return this.numGenes;
	}
	
	public List<Integer> getGenome () {
		return this.genome;
	}
	
	public int getRawFitness () {
		return this.rawFitness;
	}
	
	public void setRawFitness (int rawFitness) {
		this.rawFitness = rawFitness;
	}
	
	public double getProbability () {
		return this.probability;
	}
	
	public void setProbability (double probability) {
		this.probability = probability;
	}
	
	public double getCumulativeLowerBound () {
		return this.cumulativeLowerBound;
	}
	
	public void setCumulativeLowerBound (double lb) {
		this.cumulativeLowerBound = lb;
	}
	
	public double getCumulativeUpperBound () {
		return this.cumulativeUpperBound;
	}
	
	public void setCumulativeUpperBound (double ub) {
		this.cumulativeUpperBound = ub;
	}
	
	public int getGene (int index) {
		if (index < 0 || index >= this.genome.size()) {
			System.out.println("Individual.getGene indexOutOfBounds");
			return 0;
		}
		//return this.genome[index];
		return this.genome.get(index);
	}
	
	public void setGene (int index, int val) {
		if (index < 0 || index >= this.genome.size()) {
			System.out.println("Individual.setGene indexOutOfBounds");
			System.out.println("index: " + index);
		}
		//this.genome[index] = val;
		this.genome.set(index, val);
	}
	
	public void addGene (int val) {
		this.genome.add(val);
	}
	
	public int getGeneSum () {
		//return Arrays.stream(this.genome).sum();
		return this.genome.stream().mapToInt(gene -> gene).sum();
	}
	
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.genome.size(); i++) {
			sb.append("gene " + i + ": ");
			sb.append(BinaryStringHelper.intToBinaryString(this.genome.get(i)));
		}
		return sb.toString();
	}
	
}
