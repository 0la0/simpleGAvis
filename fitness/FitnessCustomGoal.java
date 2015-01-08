package fitness;

import main.BinaryStringHelper;
import main.Population;

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
				this.goal[i] = (int) Math.round(max * goal[i]);
			}
		}
	}
	
	/*
	 * Fitness = sum of squared distances from goal
	 */
	@Override
	public void calcFitness (Population p) {
		if (this.goal.length != p.numGenes) {
			System.out.println("fitness goal != number of genes => fatal error");
			System.exit(0);
		}
		p.totalFitness = 0;
		int maxVal = (int) Math.pow(BinaryStringHelper.maxVal, 2);
		for (int i = 0; i < p.size; i++) {
			int fitness = 0;
			for (int j = 0; j < p.individuals[i].numGenes; j++) {
				fitness += (maxVal - Math.pow(p.individuals[i].genome[j] - this.goal[j], 2));
			}
			p.individuals[i].rawFitness = fitness;
			p.totalFitness += fitness;
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

}
