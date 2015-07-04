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
		p.setTotalFitness(0);
		for (int i = 0; i < p.getSize(); i++) {
			int fitness = 0;
			for (int j = 0; j < p.getIndividual(i).getNumGenes(); j++) {
				fitness += Math.pow(p.getIndividual(i).getGenome()[j], 2);
			}
			p.getIndividual(i).setRawFitness(fitness);
			p.setTotalFitness(p.getTotalFitness() + fitness);
		}
	}

	@Override
	public double evaluate(Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString () {
		return "gene value squared";
	}

	@Override
	public void setGoal(double[] goal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] getGoal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
