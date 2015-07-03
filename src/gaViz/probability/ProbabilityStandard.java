package gaViz.probability;

import gaViz.main.Population;

public class ProbabilityStandard implements IProbability{

	public void calc(Population p) {
		float totalProb = 0;
		for (int i = 0; i < p.getSize(); i++) {
			//calc individual probability
			p.getIndividual(i).probability = (float) ( p.getIndividual(i).rawFitness / (p.getTotalFitness() * 1.0) );
			//set cumulative probability
			p.getIndividual(i).cumulativeLowerBound = totalProb;
			totalProb += p.getIndividual(i).probability;
			p.getIndividual(i).cumulativeUpperBound = totalProb;
		}
	}

}
