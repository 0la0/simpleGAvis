package gaViz.breed;
import gaViz.main.Individual;
import gaViz.main.Population;


public class BreedStandard implements IBreeder{

	public Population breed (Population p) {
		
		//generate the mating pool from p
		Population child = new Population(p.size, p.numGenes);
		for (int i = 0; i < child.size; i++) {
			Individual individual = new Individual(p.getProbabilisticIndividual().genome);
			child.addIndividual(i, individual);
		}
		return child;
	}

}
