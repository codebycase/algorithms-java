package a06_sorting_searching;

import java.util.Arrays;

public class LongestCommonSubstring {

	static class SuffixArray {
		private Suffix[] suffixes;

		public SuffixArray(String text) {
			int n = text.length();
			this.suffixes = new Suffix[n];
			for (int i = 0; i < n; i++) {
				suffixes[i] = new Suffix(text, i);
			}
			Arrays.sort(suffixes);
		}

		private class Suffix implements Comparable<Suffix> {
			private final String text;
			private final int index;

			private Suffix(String text, int index) {
				this.text = text;
				this.index = index;
			}

			private int length() {
				return text.length() - index;
			}

			private char charAt(int i) {
				return text.charAt(index + i);
			}

			public int compareTo(Suffix that) {
				if (this == that)
					return 0;
				int n = Math.min(this.length(), that.length());
				for (int i = 0; i < n; i++) {
					if (this.charAt(i) < that.charAt(i))
						return -1;
					if (this.charAt(i) > that.charAt(i))
						return +1;
				}
				return this.length() - that.length();
			}

			public String toString() {
				return text.substring(index);
			}
		}

		public int index(int i) {
			if (i < 0 || i >= suffixes.length)
				throw new IllegalArgumentException();
			return suffixes[i].index;
		}
	}

	// return the longest common prefix of suffix s[p..] and suffix t[q..]
	public static String lcp(String s, int p, String t, int q) {
		int n = Math.min(s.length() - p, t.length() - q);
		for (int i = 0; i < n; i++) {
			if (s.charAt(p + i) != t.charAt(q + i))
				return s.substring(p, p + i);
		}
		return s.substring(p, p + n);
	}

	private static int compare(String s, int p, String t, int q) {
		int n = Math.min(s.length() - p, t.length() - q);
		for (int i = 0; i < n; i++) {
			if (s.charAt(p + i) != t.charAt(q + i))
				return s.charAt(p + i) - t.charAt(q + i);
		}
		if (s.length() - p < t.length() - q)
			return -1;
		else if (s.length() - p > t.length() - q)
			return +1;
		else
			return 0;
	}

	// Compute the suffix array of each string and apply a merging operation to determin the lcs
	public static String lcs(String s, String t) {
		SuffixArray suffix1 = new SuffixArray(s);
		SuffixArray suffix2 = new SuffixArray(t);

		String lcs = "";
		int i = 0, j = 0;
		while (i < s.length() && j < t.length()) {
			int p = suffix1.index(i);
			int q = suffix2.index(j);
			String x = lcp(s, p, t, q);
			if (x.length() > lcs.length())
				lcs = x;
			if (compare(s, p, t, q) < 0)
				i++;
			else
				j++;
		}
		return lcs;
	}

	// Compute suffix array (3-way string quicksort) of a concatenated string
	public static String lcs2(String s, String t) {
		int n1 = s.length();

		String text = s + '\1' + t;
		int n = text.length();

		SuffixArrayX suffix = new SuffixArrayX(text);

		String lcs = "";
		for (int i = 1; i < n; i++) {
			// adjacent suffixes both from first text string
			if (suffix.index(i) < n1 && suffix.index(i - 1) < n1)
				continue;
			// adjacent suffixes both from second text string
			if (suffix.index(i) > n1 && suffix.index(i - 1) > n1)
				continue;

			// check if adjacent suffixes longer common substring
			int length = suffix.lcp(i);
			if (length > lcs.length()) {
				lcs = text.substring(suffix.index(i), suffix.index(i) + length);
			}
		}
		return lcs;
	}

	public static void main(String[] args) {
		String s = "it was the best of times";
		String t = "no, it was the worst of times";
		System.out.println("'" + lcs(s, t) + "'");
		System.out.println("'" + lcs2(s, t) + "'");
	}

}
