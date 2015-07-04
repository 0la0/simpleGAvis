package gaViz.probability;

import java.util.Arrays;

import gaViz.main.Population;

public class ProbabilityStandard implements IProbability{

	public void calc(Population p) {
		
		double totalPopulationFitness = p.getTotalFitness() * 1.0;
		//AtomicDouble cumulativeSum = new AtomicDouble(0);
		//final float cumulativeSum = 0;
		CumulativeSum cumulative = new CumulativeSum();
		
		Arrays.stream(p.getIndividuals()).forEach(individual -> {
			double individualFitness = (float) (individual.getRawFitness() / totalPopulationFitness);
			individual.setProbability( (float) individualFitness);
			//set cumulative bounds
			individual.setCumulativeLowerBound((float) cumulative.sum);
			cumulative.increment(individualFitness);
			individual.setCumulativeUpperBound((float) cumulative.sum);
		});
		
		/*
		float totalProb = 0;
		
		//for each individual calculate fitness
		for (int i = 0; i < p.getSize(); i++) {
			//calc individual probability
			//individual fitness / population fitness?
			float probability = (float) ( p.getIndividual(i).getRawFitness() / (p.getTotalFitness() * 1.0) );
			p.getIndividual(i).setProbability(probability);
			//set cumulative probability
			p.getIndividual(i).setCumulativeLowerBound(totalProb);
			totalProb += probability;
			p.getIndividual(i).setCumulativeUpperBound(totalProb);
		}
		*/
	}
	
	private class CumulativeSum {
		
		double sum = 0;
		
		public void increment (double val) {
			this.sum += val;
		}
		
	}

}
