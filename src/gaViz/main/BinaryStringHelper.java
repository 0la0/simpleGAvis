package gaViz.main;

public class BinaryStringHelper {

	public static int geneLength;
	public static int maxVal;
	
	public static void setStringLength (int length) {
		geneLength = length;
		maxVal = (int) Math.pow(2, length);
	}
	
	public static String intToBinaryString (int num) {
		String s = Integer.toBinaryString(num);
		if (s.length() < geneLength){
			int numZeros = geneLength - s.length();
			String zeros = "";
			for (int i = 0; i < numZeros; i++) {
				zeros += "0";
			}
			s = zeros + s;
		}
		return s;
	}
	
	public static int binaryStringToInt (String num) {
		return Integer.parseInt(num, 2);
	}
	
}
