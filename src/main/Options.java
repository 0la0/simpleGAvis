package src.main;
import src.probability.IProbability;
import src.mutate.IMutate;
import src.crossover.ICrossover;
import src.fitness.IFitness;
import src.breed.IBreeder;


public class Options {
	
	public int numGenes;
	public int populationSize;
	public int numGenerations;
	public int geneLength;
	public IMutate mutateObj;
	public IFitness fitnessObj;
	public ICrossover crossoverObj;
	public IBreeder breederObj;
	public IProbability probabilityObj;
	
}
