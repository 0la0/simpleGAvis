package main;
import probability.IProbability;
import mutate.IMutate;
import crossover.ICrossover;
import fitness.IFitness;
import breed.IBreeder;


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
