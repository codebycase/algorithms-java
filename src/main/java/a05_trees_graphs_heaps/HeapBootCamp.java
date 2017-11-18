package a05_trees_graphs_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <ul>
 * <li>A heap is specialized binary tree. The key at each node is at least as great as the keys
 * stored at its children.</li>
 * <li>A max-heap can be implemented as an array; the children of the node at index i are at indices
 * 2i + 1 and 2i + 2.</li>
 * <li>A max-heap supports O(log(n)) insertions, O(1) time lookup for the max element, and O(log(n))
 * deletion of the max elements. Searching for arbitrary keys has O(n) time complexity.</li>
 * <li>Deletion is performed by replacing the root’s key with the key at the last leaf and then
 * recovering the heap property by repeatedly exchanging keys with children.</li>
 * <li>Use a heap when all you care about is the K largest or smallest elements, and you do not need
 * to support fast lookup, delete, or search operations for arbitrary elements.</li>
 * <li>A heap is sometimes referred to as a priority queue.</li>
 * </ul>
 * 
 * @author lchen
 *
 */
public class HeapBootCamp {
	/**
	 * There are no more than k elements in the min-heap. <br>
	 * Both extract-min and insert take O(log(k)) time. <br>
	 * Hence, we can do the merge in O(nlog(k)) time.
	 * 
	 * @param sortedArrays
	 * @return
	 */
	public static List<Integer> mergeSortedArrays(List<List<Integer>> sortedArrays) {
		List<Iterator<Integer>> iters = new ArrayList<>();
		for (List<Integer> array : sortedArrays) {
			iters.add(array.iterator());
		}
		Queue<int[]> minHeap = new PriorityQueue<>(sortedArrays.size(), (a, b) -> (a[0] - b[0]));
		for (int i = 0; i < iters.size(); i++) {
			if (iters.get(i).hasNext())
				minHeap.offer(new int[] { iters.get(i).next(), i });
		}
		List<Integer> result = new ArrayList<>();
		while (!minHeap.isEmpty()) {
			int[] element = minHeap.poll();
			result.add(element[0]);
			if (iters.get(element[1]).hasNext())
				minHeap.offer(new int[] { iters.get(element[1]).next(), element[1] });
		}
		return result;
	}

	/**
	 * An array is said to be k-increasing-decreasing if elements repeatedly increase up to a
	 * certain index after which they decrease, then again increase, a total of k times. <br>
	 * 
	 * The solution is to decompose this list to a list of sorted sub arrays, and merge them!
	 * 
	 * @param A
	 */
	public static List<Integer> sortKIncreasingDecreasingArray(List<Integer> A) {
		List<List<Integer>> sortedSubarrays = new ArrayList<>();
		boolean isIncreasing = true;
		int startIdx = 0;
		for (int i = 1; i <= A.size(); i++) {
			if (i == A.size() || (A.get(i - 1) < A.get(i) && !isIncreasing)
					|| (A.get(i - 1) >= A.get(i) && isIncreasing)) {
				List<Integer> subList = A.subList(startIdx, i);
				if (!isIncreasing)
					Collections.reverse(subList);
				sortedSubarrays.add(subList);
				startIdx = i;
				isIncreasing = !isIncreasing;
			}
		}
		return mergeSortedArrays(sortedSubarrays);
	}

	/**
	 * Take as input a very long sequence of numbers and prints the numbers in sorted order. Each
	 * number is at most k away from its correctly sorted position. For example, no number in the
	 * sequence (3, -1, 2, 6, 4, 5, 8) is more than 2 away from its final sorted position. <br>
	 * 
	 * Idea: after we have read k+1 numbers, the smallest number in that group must be smaller than
	 * all following numbers.
	 * 
	 * @param sequence
	 * @param k
	 */
	public static List<Integer> sortAnAmostSortedData(Iterator<Integer> sequence, int k) {
		List<Integer> result = new ArrayList<>();
		Queue<Integer> minHeap = new PriorityQueue<>(k + 1);
		for (int i = 0; i < k && sequence.hasNext(); i++) {
			minHeap.offer(sequence.next());
		}
		while (!minHeap.isEmpty()) {
			if (sequence.hasNext())
				minHeap.offer(sequence.next());
			result.add(minHeap.poll());
		}
		return result;
	}

	/**
	 * Median is the middle value in an ordered integer list. If the size of the list is even, there
	 * is no middle value. So the median is the mean of the two middle value.
	 * 
	 * Examples: [2, 3, 4] , the median is 3; [1, 2, 3, 9], the median is (2 + 3) / 2 = 2.5.
	 * 
	 * @param sequence
	 * @return
	 */
	public static List<Double> computeOnlineMedian(Iterator<Integer> sequence) {
		Queue<Integer> minHeap = new PriorityQueue<>();
		Queue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
		List<Double> result = new ArrayList<>();

		while (sequence.hasNext()) {
			minHeap.add(sequence.next());
			maxHeap.add(minHeap.poll());
			// equal number of elements, or minHeap must have one more!
			if (maxHeap.size() > minHeap.size())
				minHeap.add(maxHeap.poll());
			result.add(minHeap.size() == maxHeap.size() ? 0.5 * (minHeap.peek() + maxHeap.peek())
					: (double) minHeap.peek());
		}

		return result;
	}

	/**
	 * Given a max-heap, represented as an array A, design an algorithm that computes the k largest
	 * elements sorted in the max-heap. <br>
	 * You can't modify the heap.
	 * 
	 * A parent node always stores value greater than or equal to the values stored at its children
	 * (2k + 1 or 2k + 2).
	 * 
	 * @param A
	 * @param k
	 * @return
	 */
	public static List<Integer> computeKLargestInBinaryHeap(List<Integer> A, int k) {
		List<Integer> result = new ArrayList<>();
		if (k <= 0)
			return result;

		// int[] -> index, value
		Queue<int[]> candidateMaxHeap = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
		candidateMaxHeap.offer(new int[] { 0, A.get(0) });

		for (int i = 0; i < k; i++) {
			Integer candidateIdx = candidateMaxHeap.peek()[0];
			result.add(candidateMaxHeap.poll()[1]);

			int leftChildIdx = 2 * candidateIdx + 1;
			if (leftChildIdx < A.size())
				candidateMaxHeap.offer(new int[] { leftChildIdx, A.get(leftChildIdx) });
			int rightChildIdx = 2 * candidateIdx + 2;
			if (rightChildIdx < A.size())
				candidateMaxHeap.offer(new int[] { rightChildIdx, A.get(rightChildIdx) });
		}

		return result;
	}

	public static void main(String[] args) {
		List<List<Integer>> S = Arrays.asList(Arrays.asList(1, 5, 10), Arrays.asList(2, 3, 100),
				Arrays.asList(2, 12, Integer.MAX_VALUE));
		List<Integer> ans = mergeSortedArrays(S);
		assert (ans.equals(Arrays.asList(1, 2, 2, 3, 5, 10, 12, 100, Integer.MAX_VALUE)));

		List<Integer> A = Arrays.asList(1, 2, 3, 2, 1, 4, 5, 10, 9, 4, 4, 1, -1);
		List<Integer> Adup = new ArrayList<>(A);
		ans = sortKIncreasingDecreasingArray(A);
		assert (Adup.size() == ans.size());
		Collections.sort(Adup);
		assert (Adup.equals(ans));

		A = Arrays.asList(2, 1, 5, 4, 3, 9, 8, 7, 6);
		ans = sortAnAmostSortedData(A.iterator(), 3);
		assert (ans.equals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));

		List<Integer> stream = Arrays.asList(1, 0, 3, 5, 2, 0, 1);
		List<Double> result = computeOnlineMedian(stream.iterator());
		assert result.equals(Arrays.asList(1.0, 0.5, 1.0, 2.0, 2.0, 1.5, 1.0));

		List<Integer> maxHeap = Arrays.asList(10, 2, 9, 2, 2, 8, 8, 2, 2, 2, 2, 7, 7, 7, 7);
		ans = computeKLargestInBinaryHeap(maxHeap, 3);
		assert (ans.equals(Arrays.asList(10, 9, 8)));
	}
}
