package gaViz.fitness;
import gaViz.main.Population;


public class FitnessSquared implements IFitness{

	@Override
	public void setGoal(int[] goal) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Fitness Squared is the function f(x) = x^2
	 * this is the fitness function from the
	 * "simple GA example"
	 */
	public void calcFitness(Population p) {
		p.totalFitness = 0;
		for (int i = 0; i < p.size; i++) {
			int fitness = 0;
			for (int j = 0; j < p.individuals[i].numGenes; j++) {
				fitness += Math.pow(p.individuals[i].genome[j], 2);
			}
			p.individuals[i].rawFitness = fitness;
			p.totalFitness += fitness;
		}
	}

	@Override
	public float evaluate(Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString () {
		return "gene value squared";
	}

	@Override
	public void setGoal(float[] goal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getGoal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
