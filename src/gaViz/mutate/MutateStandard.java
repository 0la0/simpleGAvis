package gaViz.mutate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;

public class MutateStandard implements IMutate{

	private double mutateThreshold;
	
	public MutateStandard (double mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}
	
	public void mutate (Population p) {
		
		List<Individual> mutatedIndividuals = Arrays.stream(p.getIndividuals()).map( nonMutatedIndividual -> {
			
			int[] mutatedGenome = Arrays.stream(nonMutatedIndividual.getGenome()).map(gene -> {
				char[] mutatedGene = BinaryStringHelper.intToBinaryString(gene).toCharArray();
				for (int i = 0; i < mutatedGene.length; i++) {
					if (Math.random() < this.mutateThreshold) {
						//flip binary character
						if (mutatedGene[i] == '0') {
							mutatedGene[i] = '1';
						} else {
							mutatedGene[i] = '0';
						}
					}
				}
				int mutatedGeneIntVal = BinaryStringHelper.binaryStringToInt(new String(mutatedGene));
				return mutatedGeneIntVal;
			}).toArray();
			
			Individual mutatedInd = new Individual(mutatedGenome);
			return mutatedInd;
		
		}).collect(Collectors.toList());
		
		
		//TODO: figure out how to stream map to POJO array
		// or just change arrays to lists
		AtomicInteger cnt = new AtomicInteger(0);
		Individual[] mutatedArr = new Individual[mutatedIndividuals.size()];
		mutatedIndividuals.forEach(individual -> {
			mutatedArr[cnt.getAndIncrement()] = individual;
		});
		
		p.setIndividuals(mutatedArr);
	}

	@Override
	public void setMutateThreshold (double mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}

}
