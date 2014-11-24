package mutate;

import main.Population;

public interface IMutate {

	public void setMutateThreshold(float mutateThreshold);
	
	public void mutate(Population p);
	
}
