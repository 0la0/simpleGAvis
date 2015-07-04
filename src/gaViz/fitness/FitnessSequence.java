package gaViz.fitness;
import gaViz.main.BinaryStringHelper;
import gaViz.main.Population;


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
	public void setGoal(double[] goal) {
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
		p.setTotalFitness(0);
		//for each individual in the population
		for (int i = 0; i < p.getSize(); i++) {
			int fitness = 0;
			//for each gene in an individual's genome
			for (int j = 0; j < p.getIndividual(i).getNumGenes(); j++) {
				int compareScore = this.compareStrings(
						BinaryStringHelper.intToBinaryString(p.getIndividual(i).getGene(j)).toCharArray(),
						this.binaryGoal[j].toCharArray());
				fitness += (int) Math.pow(compareScore, 2);
			}
			p.getIndividual(i).setRawFitness(fitness);
			p.setTotalFitness(p.getTotalFitness() + fitness);
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
	public double evaluate(Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString () {
		return "Sequence Matching";
	}

}
