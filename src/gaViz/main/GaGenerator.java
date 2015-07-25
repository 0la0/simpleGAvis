package gaViz.main;

public class GaGenerator {
	
	private GaConfigOptions options;
	private Generations generations;
	
	public GaGenerator (GaConfigOptions options) {
		this.options = options;
		BinaryStringHelper.setStringLength(this.options.geneLength);
		this.generations = new Generations(options.numGenerations);
		
		this.randomRestart();
		//this.createNewPopulation(); //necessary???
	}
	
	
	public void randomRestart () {
		Population firstPopulation = new Population(
				this.options.populationSize, this.options.numGenes, this.getGoalSize());
		this.options.fitnessObj.calcFitness(firstPopulation);
		this.options.probabilityObj.calc(firstPopulation);
		this.generations.add(firstPopulation);
	}
	
	public Population createNewPopulation () {
		Population child = this.options.breederObj.breed(generations.getLatestPopulation());
		this.options.crossoverObj.crossover(child);
		this.options.mutateObj.mutate(child);
		this.options.fitnessObj.calcFitness(child);
		this.options.probabilityObj.calc(child);
		
		this.generations.add(child);
		return child;
	}
	
	public int getGoalSize () {
		return (int) Math.pow(2, this.options.geneLength);
	}
	
	public Population getLatestPopulation () {
		return this.generations.getLatestPopulation();
	}
	
	public Generations getGenerations () {
		return this.generations;
	}


}
