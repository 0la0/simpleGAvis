package gaViz.mutate;

import gaViz.main.BinaryStringHelper;
import gaViz.main.Individual;
import gaViz.main.Population;

public class MutateStandard implements IMutate{

	private float mutateThreshold;
	
	public MutateStandard (float mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}
	
	public void mutate (Population p) {
		for (int i = 0; i < p.size; i++) {
			Individual nonMutatedInd = p.individuals[i];
			Individual mutatedInd = new Individual(p.numGenes);
			for (int j = 0; j < p.numGenes; j++) {
				char[] paramArr = BinaryStringHelper.intToBinaryString(nonMutatedInd.genome[j]).toCharArray();
				for (int k = 0; k < paramArr.length; k++) {
					if (Math.random() < this.mutateThreshold) {
						//flip binary character
						if (paramArr[k] == '0') {
							paramArr[k] = '1';
						} else {
							paramArr[k] = '0';
						}
					}
				}
				mutatedInd.genome[j] = BinaryStringHelper.binaryStringToInt(new String(paramArr));
			}
			p.individuals[i] = mutatedInd;
		}
	}

	@Override
	public void setMutateThreshold (float mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}

}
