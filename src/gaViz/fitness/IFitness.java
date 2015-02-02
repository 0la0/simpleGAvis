package gaViz.fitness;
import gaViz.main.Population;


public interface IFitness {
	
	public void setGoal(int[] goal);
	
	public void setGoal(float[] goal);
	
	public int[] getGoal ();
	
	public void calcFitness(Population p);
	
	public float evaluate(Population p);

}
