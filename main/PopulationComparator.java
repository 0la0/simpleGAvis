package main;

import java.util.Comparator;

public class PopulationComparator implements Comparator{

	@Override
	public int compare(Object obj0, Object obj1) {
		
		Individual ind0 = (Individual) obj0;
		Individual ind1 = (Individual) obj1;
		
		int compareVar0;
		int compareVar1;
		
		//compareVar0 = ind0.rawFitness;
		//compareVar1 = ind1.rawFitness;
		
		compareVar0 = ind0.getGeneSum();
		compareVar1 = ind1.getGeneSum();
		
		if (compareVar0 < compareVar1) {
			return -1;
		}
		else if (compareVar0 > compareVar1) {
			return 1;
		}
		else {
			return 0;
		}
	}
	

}
