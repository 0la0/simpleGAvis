package gaViz.probability;

import gaViz.main.Population;

public class ProbabilityStandard implements IProbability{

	public void calc(Population p) {
		float totalProb = 0;
		for (int i = 0; i < p.getSize(); i++) {
			//calc individual probability
			float probability = (float) ( p.getIndividual(i).getRawFitness() / (p.getTotalFitness() * 1.0) );
			p.getIndividual(i).setProbability(probability);
			//set cumulative probability
			p.getIndividual(i).setCumulativeLowerBound(totalProb);
			totalProb += probability;
			p.getIndividual(i).setCumulativeUpperBound(totalProb);
		}
	}

}
