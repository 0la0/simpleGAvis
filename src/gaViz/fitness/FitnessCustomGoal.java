package gaViz.fitness;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;

public class FitnessCustomGoal implements IFitness{

	private int[] goal;
	
	public FitnessCustomGoal (int[] goal) {
		this.setGoal(goal);
	}
	
	public FitnessCustomGoal (double[] goal) {
		this.setGoal(goal);
	}
	
	public void setGoal (int[] goal) {
		this.goal = goal;
	}
	
	public void setGoal (double[] goal) {
		int max = BinaryStringHelper.maxVal;
		
		this.goal = Arrays.stream(goal).mapToInt(goalElement -> {
			int intMappedGoal;
			if (goalElement <= 0) {
				intMappedGoal = 0;
			}
			else if (goalElement >= 1) {
				intMappedGoal = max;
			}
			else {
				intMappedGoal = (int) Math.floor(max * goalElement);
			}
			return intMappedGoal;
		}).toArray();
		
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
		
		int maxVal = (int) Math.pow(BinaryStringHelper.maxVal, 2);
		
		int totalPopulationFitness = Arrays.stream(p.getIndividuals()).mapToInt(individual -> {
			
			AtomicInteger geneIndex = new AtomicInteger(0);
			int individualFitness = individual.getGenome().stream()
					.mapToInt(gene -> {
						int goalState = this.goal[geneIndex.getAndIncrement()];
						int geneFitness = maxVal - (int) Math.pow(gene - goalState, 2);
						return geneFitness;
					})
					.sum();
			/*
			int individualFitness = Arrays.stream(individual.getGenome())
					.map(gene -> {
						int goalState = this.goal[geneIndex.getAndIncrement()];
						int geneFitness = maxVal - (int) Math.pow(gene - goalState, 2);
						return geneFitness;
					})
					.sum();
			*/
			//set raw fitness of the individual in question
			individual.setRawFitness(individualFitness);
			
			return individualFitness;
			
		}).sum();
		
		p.setTotalFitness(totalPopulationFitness);

	}

	@Override
	public double evaluate (Population p) {
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
					return  maxGeneVal - Math.pow(individual.getGene(geneIndex) - this.goal[geneIndex], 2);
				})
				.sum();
		
		double fitnessVal = (fitnessSum) / (maxPopVal * 1.0);
		return fitnessVal;
	}
	
}
