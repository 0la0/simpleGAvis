package gaViz.main;

import java.util.Comparator;

public class PopulationComparator implements Comparator{

	@Override
	public int compare(Object obj0, Object obj1) {
		
		int compareVar0 = ((Individual) obj0).getGeneSum();
		int compareVar1 = ((Individual) obj1).getGeneSum();
		
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
