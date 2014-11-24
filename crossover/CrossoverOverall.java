package crossover;
import main.BinaryStringHelper;
import main.Individual;
import main.Population;


public class CrossoverOverall implements ICrossover{

	//perform one crossover across all genes
	public void crossover(Population p) {
		int geneLength = BinaryStringHelper.geneLength;
		int numGenes = p.numGenes;
		for (int i = 0; i < p.size; i += 2) {
			Individual parent1 = p.individuals[i + 0];
			Individual parent2 = p.individuals[i + 1];
			Individual child1 = new Individual(numGenes);
			Individual child2 = new Individual(numGenes);
			String parentString1 = "";
			String parentString2 = "";
			for (int j = 0; j < numGenes; j++) {
				parentString1 += BinaryStringHelper.intToBinaryString(parent1.genome[j]);
				parentString2 += BinaryStringHelper.intToBinaryString(parent2.genome[j]);
			}
			int crossoverIndex = 1 + (int) Math.floor( (parentString1.length() - 1) * Math.random());
			String p1_1 = parentString1.substring(0, crossoverIndex);
			String p1_2 = parentString1.substring(crossoverIndex, parentString1.length());
			String p2_1 = parentString2.substring(0, crossoverIndex);
			String p2_2 = parentString2.substring(crossoverIndex, parentString2.length());
			String c1 = p1_1 + p2_2;
			String c2 = p1_2 + p2_1;
			for (int j = 0; j < numGenes; j++) {
				child1.genome[j] = BinaryStringHelper.binaryStringToInt(c1.substring(j * geneLength, (j * geneLength) + geneLength));
				child2.genome[j] = BinaryStringHelper.binaryStringToInt(c2.substring(j * geneLength, (j * geneLength) + geneLength));
			}
			
			p.individuals[i + 0] = child1;
			p.individuals[i + 1] = child2;
		}
	}

}
