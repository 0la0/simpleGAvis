package gaViz.fitness;

import java.util.Arrays;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;

public class FitnessCustomGoal implements IFitness{

	private int[] goal;
	//private float[] realGoal;
	
	public FitnessCustomGoal (int[] goal) {
		this.setGoal(goal);
	}
	
	public FitnessCustomGoal (float[] goal) {
		//this.realGoal = goal;
		this.setGoal(goal);
	}
	
	public void setGoal (int[] goal) {
		this.goal = goal;
	}
	
	public void setGoal (float[] goal) {
		this.goal = new int[goal.length];
		int max = BinaryStringHelper.maxVal;
		for (int i = 0; i < goal.length; i++) {
			if (goal[i] <= 0) {
				this.goal[i] = 0;
			}
			else if (goal[i] >= 1) {
				this.goal[i] = max;
			}
			else {
				this.goal[i] = (int) Math.floor(max * goal[i]);
			}
		}
	}
	
	/*
	 * Fitness = sum of squared distances from goal
	 */
	@Override
	public void calcFitness (Population p) {
		if (this.goal.length != p.getNumGenes()) {
			System.out.println("fitness goal != number of genes => fatal error");
			System.exit(0);
		}
		p.setTotalFitness(0);
		int maxVal = (int) Math.pow(BinaryStringHelper.maxVal, 2);
		for (int i = 0; i < p.getSize(); i++) {
			int fitness = 0;
			for (int j = 0; j < p.getIndividual(i).getNumGenes(); j++) {
				fitness += (maxVal - Math.pow(p.getIndividual(i).getGenome()[j] - this.goal[j], 2));
			}
			p.getIndividual(i).setRawFitness(fitness);
			p.setTotalFitness(p.getTotalFitness() + fitness);
		}
	}

	@Override
	public float evaluate (Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString () {
		return "custom goal";
	}

	@Override
	public int[] getGoal() {
		return this.goal;
	}
	
	public double getGeneFitness (Population p, int geneIndex) {
		if (geneIndex < 0 || geneIndex >= p.getNumGenes()) {
			return -1;
		}
		p.setTotalFitness(0);
		
		int maxGeneVal = (int) Math.pow(BinaryStringHelper.maxVal, 2);
		int maxPopVal = maxGeneVal * p.getSize();
		
		double fitnessSum = Arrays.stream(p.getIndividuals())
				.mapToDouble(individual -> {
					return  maxGeneVal - Math.pow(individual.getGenome()[geneIndex] - this.goal[geneIndex], 2);
				})
				.sum();
		
		double fitnessVal = (fitnessSum) / (maxPopVal * 1.0);
		return fitnessVal;
	}
	
}
