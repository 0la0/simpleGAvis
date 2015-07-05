package gaViz.breed;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gaViz.main.Individual;
import gaViz.main.Population;


public class BreedStandard implements IBreeder{

	public Population breed (Population p) {
		//generate the mating pool from parent population
		List<Individual> parents = p.getIndividuals().stream().map(ind -> {
			return new Individual(p.getProbabilisticIndividual().getGenome());
		}).collect(Collectors.toList());
		return new Population(parents);
	}

}
