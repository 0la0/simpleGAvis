package gaViz.fitness;
import gaViz.main.Population;


public interface IFitness {
	
	public void setGoal(int[] goal);
	
	public void setGoal(double[] goal);
	
	public int[] getGoal ();
	
	public void calcFitness(Population p);
	
	public double evaluate(Population p);

}
