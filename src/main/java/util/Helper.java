package util;

public class Helper {

	public static void main(String[] args) {
		String input = "[ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ]";
		input = input.replaceAll("\\[", "{");
		input = input.replaceAll("\\]", "}");
		input = input.replaceAll("(\\d+) ", "$1, ");
		System.out.println(input);
	}
}
