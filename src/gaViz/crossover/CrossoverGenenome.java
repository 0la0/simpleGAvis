package gaViz.crossover;
import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;


public class CrossoverGenenome implements ICrossover{

	//crossover once per gene
	public void crossover(Population p) {
		for (int i = 0; i < p.size; i += 2) {
			Individual parent1 = p.individuals[i + 0];
			Individual parent2 = p.individuals[i + 1];
			Individual child1 = new Individual(p.numGenes);
			Individual child2 = new Individual(p.numGenes);
			for (int j = 0; j < p.numGenes; j++) {
				String p1 = BinaryStringHelper.intToBinaryString(parent1.genome[j]);
				String p2 = BinaryStringHelper.intToBinaryString(parent2.genome[j]);
				int crossoverIndex = 1 + (int) Math.floor( (p1.length() - 1) * Math.random() );
				String p1_1 = p1.substring(0, crossoverIndex);
				String p1_2 = p1.substring(crossoverIndex, p1.length());
				String p2_1 = p2.substring(0, crossoverIndex);
				String p2_2 = p2.substring(crossoverIndex, p2.length());
				String c1 = p1_1 + p2_2;
				String c2 = p2_1 + p1_2;
				child1.genome[j] = BinaryStringHelper.binaryStringToInt(c1);
				child2.genome[j] = BinaryStringHelper.binaryStringToInt(c2);
			}
			p.individuals[i + 0] = child1;
			p.individuals[i + 1] = child2;
		}
	}
	
}
