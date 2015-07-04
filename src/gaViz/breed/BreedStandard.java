package gaViz.breed;
import java.util.Arrays;

import gaViz.main.Individual;
import gaViz.main.Population;


public class BreedStandard implements IBreeder{

	public Population breed (Population p) {	
		//generate the mating pool from p
		Individual[] individuals = new Individual[p.getSize()];
		for (int i = 0; i < p.getSize(); i++) {
			individuals[i] = new Individual(p.getProbabilisticIndividual().getGenome());
		}
		return new Population(individuals);
	}

}
