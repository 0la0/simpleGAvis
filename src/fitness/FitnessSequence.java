package src.fitness;
import src.main.BinaryStringHelper;
import src.main.Population;


public class FitnessSequence implements IFitness {

	int[] goal;
	String[] binaryGoal;
	
	public FitnessSequence () {}
	
	public FitnessSequence (int[] goal) {
		this.setGoal(goal);
	}
	
	@Override
	public void setGoal(int[] goal) {
		this.goal = goal;
		this.binaryGoal = new String[goal.length];
		for (int i = 0; i < goal.length; i++) {
			this.binaryGoal[i] = BinaryStringHelper.intToBinaryString(goal[i]);
		}
	}

	@Override
	public void setGoal(float[] goal) {
		// TODO Auto-generated method stub
	}

	@Override
	public int[] getGoal() {
		return this.goal;
	}

	@Override
	/*
	 * FOR EACH BINARY CHARACTER IN A GENE:
	 * 	+0 IF THE CHARACTER IS NOT THE GOAL STATE
	 *  +1 IF THE CHARACTER IS THE CORRECT GOAL STATE
	 *  SQUARE SUM
	 */
	public void calcFitness(Population p) {
		p.totalFitness = 0;
		//for each individual in the population
		for (int i = 0; i < p.size; i++) {
			int fitness = 0;
			//for each gene in an individual's genome
			for (int j = 0; j < p.individuals[i].numGenes; j++) {
				int compareScore = this.compareStrings(
						BinaryStringHelper.intToBinaryString(p.individuals[i].genome[j]).toCharArray(),
						this.binaryGoal[j].toCharArray());
				fitness += (int) Math.pow(compareScore, 2);
			}
			p.individuals[i].rawFitness = fitness;
			p.totalFitness += fitness;
		}
	}
	
	private int compareStrings (char[] s1, char[] s2) {
		int compareScore = 0;
		for (int i = 0; i < s1.length; i++) {
			if (s1[i] == s2[i]) compareScore++;
		}
		return compareScore;
	}

	@Override
	public float evaluate(Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString () {
		return "Sequence Matching";
	}

}
