package gaViz.fitness;
import gaViz.main.BinaryStringHelper;
import gaViz.main.Population;


public class FitnessNumOnes implements IFitness{

	@Override
	public void setGoal(int[] goal) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * This fitness function is the total number of "ones"
	 * in the binary string of the integer
	 */
	public void calcFitness (Population p) {
		p.setTotalFitness(0);
		for (int i = 0; i < p.getSize(); i++) {
			int fitness = 0;
			for (int j = 0; j < p.getIndividual(i).numGenes; j++) {
				char[] binaryGene = BinaryStringHelper.intToBinaryString(p.getIndividual(i).genome[j]).toCharArray();
				for (int k = 0; k < binaryGene.length; k++) {
					if (binaryGene[k] == '1') {
						fitness++;
					}
				}
			}
			fitness = (int) Math.pow(fitness, 2);
			p.getIndividual(i).rawFitness = fitness;
			p.setTotalFitness(p.getTotalFitness() + fitness);
		}
	}
	
	@Override
	public float evaluate(Population p) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString () {
		return "number of ones in binary gene, squared";
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
