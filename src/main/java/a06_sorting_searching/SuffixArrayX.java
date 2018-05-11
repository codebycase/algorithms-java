package a06_sorting_searching;

public class SuffixArrayX {
	private static final int CUTOFF = 5; // cutoff to insertion sort

	private final char[] text;
	private final int[] index; // index[i] = j means text.substring(j) is the ith largest suffix
	private final int n; // number of characters in text;

	public SuffixArrayX(String text) {
		n = text.length();
		text = text + '\0';
		this.text = text.toCharArray();
		this.index = new int[n];
		for (int i = 0; i < n; i++) {
			index[i] = i;
		}
		sort(0, n - 1, 0);
	}

	// 3-way string quicksort lo..hi starting at dth character
	private void sort(int lo, int hi, int d) {
		if (lo + CUTOFF >= hi) {
			insertion(lo, hi, d);
			return;
		}

		int lt = lo, gt = hi;
		char v = text[index[lo] + d];
		int i = lo + 1;
		while (i <= gt) {
			char t = text[index[i] + d];
			if (t < v)
				exch(lt++, i++);
			else if (t > v)
				exch(i, gt--);
			else
				i++;
		}

		sort(lo, lt - 1, d);
		if (v > 0)
			sort(lt, gt, d + 1);
		sort(gt + 1, hi, d);
	}

	// sort from a[lo] to a[hi], starting at the dth character
	private void insertion(int lo, int hi, int d) {
		for (int i = lo; i <= hi; i++) {
			for (int j = i; j > lo && less(index[j], index[j - 1], d); j--) {
				exch(j, j - 1);
			}
		}
	}

	// is text[i+d..n) < text[j+d..n)?
	private boolean less(int i, int j, int d) {
		if (i == j)
			return false;
		i = i + d;
		j = j + d;
		while (i < n && j < n) {
			if (text[i] < text[j])
				return true;
			else if (text[i] > text[j])
				return false;
			i++;
			j++;
		}
		return i > j;
	}

	private void exch(int i, int j) {
		int swap = index[i];
		index[i] = index[j];
		index[j] = swap;
	}

	public int index(int i) {
		if (i < 0 || i >= n)
			throw new IllegalArgumentException();
		return index[i];
	}

	public int lcp(int i) {
		if (i < 1 || i >= n)
			throw new IllegalArgumentException();
		return lcp(index[i], index[i - 1]);
	}

	// longest common prefix of text[i..n) and text[j..n)
	private int lcp(int i, int j) {
		int length = 0;
		while (i < n && j < n) {
			if (text[i] != text[j])
				return length;
			i++;
			j++;
			length++;
		}
		return length;
	}

	// Returns the ith smallest suffix as a string
	public String select(int i) {
		if (i < 0 || i >= n)
			throw new IllegalArgumentException();
		return new String(text, index[i], n - index[i]);
	}

	// Binary search to get rank of currenty query
	public int rank(String query) {
		int lo = 0, hi = n - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = compare(query, index[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	private int compare(String query, int i) {
		int m = query.length();
		int j = 0;
		while (i < n && j < m) {
			if (query.charAt(j) != text[i])
				return query.charAt(j) - text[i];
			i++;
			j++;
		}
		if (i < n)
			return -1;
		if (j < m)
			return +1;
		return 0;
	}
}
