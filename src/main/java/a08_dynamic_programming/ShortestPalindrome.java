package a08_dynamic_programming;

public class ShortestPalindrome {

	public String shortestPalindrome(String s) {
		if (s == null || s.length() <= 1)
			return s;
		String temp = s + "#" + new StringBuilder(s).reverse().toString();
		int[] position = new int[temp.length()];

		// skip index 0 as we will not match a string with itself
		for (int i = 1; i < position.length; i++) {
			// compare prefix with current
			int prefix = position[i - 1];
			while (prefix > 0 && temp.charAt(prefix) != temp.charAt(i))
				prefix = position[prefix - 1];
			position[i] = prefix + ((temp.charAt(prefix) == temp.charAt(i)) ? 1 : 0);
		}
		// reverse the remain part and add to the front
		return new StringBuilder(s.substring(position[position.length - 1])).reverse().toString() + s;
	}

}
