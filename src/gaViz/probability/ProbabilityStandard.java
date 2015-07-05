package gaViz.probability;

import gaViz.main.Population;

public class ProbabilityStandard implements IProbability{

	public void calc(Population p) {
		
		double totalPopulationFitness = p.getTotalFitness() * 1.0;
		//AtomicDouble cumulativeSum = new AtomicDouble(0);
		CumulativeSum cumulative = new CumulativeSum();
		
		p.getIndividuals().stream().forEach(individual -> {
			double individualFitness = (float) (individual.getRawFitness() / totalPopulationFitness);
			individual.setProbability( (float) individualFitness);
			//set cumulative bounds
			individual.setCumulativeLowerBound((float) cumulative.sum);
			cumulative.increment(individualFitness);
			individual.setCumulativeUpperBound((float) cumulative.sum);
		});
		
	}
	
	private class CumulativeSum {
		
		double sum = 0;
		
		public void increment (double val) {
			this.sum += val;
		}
		
	}

}
