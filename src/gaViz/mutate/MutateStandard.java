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
		
		//mutate population
		List<Individual> mutatedIndividuals = p.getIndividuals().stream().map( nonMutatedIndividual -> {
			//mutate individual
			List<Integer> mutatedGenome = nonMutatedIndividual.getGenome().stream()
					.map(gene -> {
						char[] geneSequence = BinaryStringHelper.intToBinaryString(gene).toCharArray();
						for (int i = 0; i < geneSequence.length; i++) {
							if (Math.random() < this.mutateThreshold) {
								//flip binary character
								geneSequence[i] = geneSequence[i] == '0' ? '1' : '0';
							}
						}
						int mutatedGene = BinaryStringHelper.binaryStringToInt(new String(geneSequence));
						return mutatedGene;
					})
					.collect(Collectors.toList());
			
			
			Individual mutatedInd = new Individual(mutatedGenome);
			return mutatedInd;
		
		}).collect(Collectors.toList());
		
		
		//TODO: figure out how to stream map to POJO array
		// or just change arrays to lists
		/*
		AtomicInteger cnt = new AtomicInteger(0);
		Individual[] mutatedArr = new Individual[mutatedIndividuals.size()];
		mutatedIndividuals.forEach(individual -> {
			mutatedArr[cnt.getAndIncrement()] = individual;
		});
		*/
		
		p.setIndividuals(mutatedIndividuals);
	}
	
	//private void mutateIndividual 

	@Override
	public void setMutateThreshold (double mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}

}
