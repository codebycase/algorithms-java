package a18_the_honors_question;

import java.util.Collections;
import java.util.List;

/**
 * Let A be an array of length n. Design an algorithm to find the smallest positive integer which is
 * not present in A. You don't need to preserve the contents of A. For example, if A = {3, 5, 4, -1,
 * 5, 1, -1}, the smallest positive integer not present in A is 2.
 * 
 * @author lchen
 *
 */
public class FirstMissingPositiveEntry {
	public static int findFirstMissingPositive(List<Integer> A) {
		// first pass to save values to proper positions
		for (int i = 0; i < A.size(); i++) {
			while (0 < A.get(i) && A.get(i) <= A.size() && !A.get(A.get(i) - 1).equals(A.get(i))) {
				Collections.swap(A, i, A.get(i) - 1);
			}
		}
		// second pass through A to find the missing entry
		for (int i = 0; i < A.size(); i++) {
			if (A.get(i) != i + 1)
				return i + 1;
		}
		// if not found the entry between 1 and A.size()
		return A.size() + 1;
	}
}
