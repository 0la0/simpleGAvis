package gaViz.mutate;

import gaViz.main.Population;

public interface IMutate {

	public void setMutateThreshold(double mutateThreshold);
	
	public void mutate(Population p);
	
}
