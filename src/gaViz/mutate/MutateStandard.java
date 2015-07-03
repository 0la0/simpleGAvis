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
		for (int i = 0; i < p.getSize(); i++) {
			Individual nonMutatedInd = p.getIndividual(i);
			Individual mutatedInd = new Individual(p.getNumGenes());
			for (int j = 0; j < p.getNumGenes(); j++) {
				char[] paramArr = BinaryStringHelper.intToBinaryString( nonMutatedInd.getGene(j) ).toCharArray();
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
				int geneVal = BinaryStringHelper.binaryStringToInt(new String(paramArr));
				mutatedInd.setGene(j, geneVal);
			}
			p.setIndividual(i, mutatedInd);
		}
	}

	@Override
	public void setMutateThreshold (float mutateThreshold) {
		this.mutateThreshold = mutateThreshold;
	}

}
