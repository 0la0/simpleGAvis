package gaViz.main;
import gaViz.probability.IProbability;
import gaViz.mutate.IMutate;
import gaViz.crossover.ICrossover;
import gaViz.fitness.IFitness;
import gaViz.breed.IBreeder;


public class GaConfigOptions {
	
	public int numGenes;
	public int populationSize;
	public int numGenerations;
	public int geneLength;
	public int numGensToSave;
	public IMutate mutateObj;
	public IFitness fitnessObj;
	public ICrossover crossoverObj;
	public IBreeder breederObj;
	public IProbability probabilityObj;
	
}
