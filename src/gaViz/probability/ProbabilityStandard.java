package gaViz.probability;

import gaViz.main.Population;

public class ProbabilityStandard implements IProbability{

	public void calc(Population p) {
		float totalProb = 0;
		for (int i = 0; i < p.size; i++) {
			//calc individual probability
			p.individuals[i].probability = (float) ( p.individuals[i].rawFitness / (p.totalFitness * 1.0) );
			//set cumulative probability
			p.individuals[i].cumulativeLowerBound = totalProb;
			totalProb += p.individuals[i].probability;
			p.individuals[i].cumulativeUpperBound = totalProb;
		}
	}

}
