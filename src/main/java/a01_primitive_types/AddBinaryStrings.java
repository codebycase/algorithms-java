package a01_primitive_types;

/**
 * Given two binary strings, return their sum (also a binary string).
 * 
 * For example, a = "11" b = "1" Return "100".
 * 
 * @author lchen
 *
 */
public class AddBinaryStrings {
	public String addBinary(String a, String b) {
		StringBuilder builder = new StringBuilder();
		int i = a.length() - 1;
		int j = b.length() - 1;
		int carray = 0;
		while (i >= 0 || j >= 0) {
			int sum = carray;
			if (i >= 0) {
				sum += a.charAt(i) - '0';
				i--;
			}
			if (j >= 0) {
				sum += b.charAt(j) - '0';
				j--;
			}
			builder.append(sum % 2);
			carray = sum / 2;
		}
		if (carray != 0)
			builder.append(carray);
		return builder.reverse().toString();
	}
}
