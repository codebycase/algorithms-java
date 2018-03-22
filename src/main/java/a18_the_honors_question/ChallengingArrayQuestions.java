package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ChallengingArrayQuestions {
	/**
	 * You are given an array of positive and negative integers. If a number n at an index is
	 * positive, then move forward n steps. Conversely, if it's negative (-n), move backward n
	 * steps. Assume the first element of the array is forward next to the last element, and the
	 * last element is backward next to the first element. Determine if there is a loop in this
	 * array. A loop starts and ends at a particular index with more than 1 element along the loop.
	 * The loop must be "forward" or "backward'.
	 * 
	 * Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.
	 * 
	 * Example 2: Given the array [-1, 2], there is no loop.
	 */
	public static boolean circularArrayLoop(int[] nums) {
		int n = nums.length;
		for (int i = 0; i < n; i++) {
			// no movement
			if (nums[i] == 0) {
				continue;
			}
			int slow = i, fast = getNext(nums, i);
			// with the same direction, both negative or positive!
			while (nums[fast] * nums[i] > 0 && nums[getNext(nums, fast)] * nums[i] > 0) {
				if (slow == fast) {
					// check for loop with only one element
					if (slow == getNext(nums, slow)) {
						break;
					}
					return true;
				}
				slow = getNext(nums, slow);
				fast = getNext(nums, getNext(nums, fast));
			}
			// loop not found, set all elements along the way to 0
			slow = i;
			int val = nums[i];
			while (nums[slow] * val > 0) {
				int next = getNext(nums, slow);
				nums[slow] = 0;
				slow = next;
			}
		}
		return false;
	}

	private static int getNext(int[] nums, int i) {
		int n = nums.length, x = i + nums[i];
		return x % n + (x >= 0 ? 0 : n);
	}

	/**
	 * Write a program for computing the minimum number of tickets to distribute to the developers,
	 * while ensuring that if a developer has written more lines of code than a neighbor, then he
	 * receives more tickets than his neighbor.
	 */
	public static int calculateBonus(int[] productivity) {
		int[] tickets = new int[productivity.length];
		Arrays.fill(tickets, 1);

		// from left to right
		for (int i = 1; i < productivity.length; i++) {
			if (productivity[i] > productivity[i - 1])
				tickets[i] = tickets[i - 1] + 1;
		}

		// from right to left
		for (int i = productivity.length - 2; i >= 0; i--) {
			if (productivity[i] > productivity[i + 1]) {
				tickets[i] = Math.max(tickets[i], tickets[i + 1] + 1);
			}
		}

		return Arrays.stream(tickets).sum();
	}

	/**
	 * Design an algorithm that takes a sorted array whose length is not know, and a key, and
	 * returns an index of an array element which is equal to the key. Assume that an out-of-bounds
	 * access throws an exception.
	 */
	public static int searchUnboundArray(int[] array, int key) {
		// find a range where key exists if it's present
		int p = 0;
		while (true) {
			try {
				int idx = (1 << p) - 1;
				if (array[idx] == key) {
					return idx;
				} else if (array[idx] > key) {
					break;
				}
			} catch (IndexOutOfBoundsException e) {
				break;
			}
			p++;
		}
		// binary search between indices 2^(p-1) and 2^p - 2, inclusive
		int left = Math.max(0, 1 << (p - 1)), right = (1 << p) - 2;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			try {
				if (array[mid] == key) {
					return mid;
				} else if (array[mid] > key) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			} catch (IndexOutOfBoundsException e) {
				right = mid - 1;
			}
		}
		return -1; // nothing matched!
	}

	/**
	 * Design an algorithm for computing the kth largest element in a sequence of elements.
	 * 
	 */
	public static int findKthLargestUnknownLength(Iterator<Integer> stream, int k) {
		int size = 2 * k - 1; // the bigger the more efficient! but use more space
		List<Integer> candidates = new ArrayList<>(size);
		while (stream.hasNext()) {
			candidates.add(stream.next());
			if (candidates.size() == size) {
				findKthLargest(candidates, 0, candidates.size() - 1, k);
				candidates.subList(k, candidates.size()).clear();
			}
		}
		findKthLargest(candidates, 0, candidates.size() - 1, k);
		return candidates.get(k - 1);
	}

	private static int findKthLargest(List<Integer> nums, int start, int end, int k) {
		if (start > end)
			return Integer.MAX_VALUE;
		int left = start;
		// Pick the last value as pivot or randomize a pivot index and swap with the end index.
		int pivot = nums.get(end);
		for (int i = start; i <= end; i++) {
			if (nums.get(i) > pivot) {
				Collections.swap(nums, left++, i);
			}
		}
		Collections.swap(nums, left, end);

		if (left == k - 1)
			return nums.get(left);
		else if (left < k - 1)
			return findKthLargest(nums, left + 1, end, k);
		else
			return findKthLargest(nums, start, left - 1, k);
	}

	public static void main(String[] args) {
		assert circularArrayLoop(new int[] { 2, -1, 1, 2, 2 }) == true;
		assert calculateBonus(new int[] { 300, 400, 200, 500, 500 }) == 7;
		int[] array = new int[] { -14, -10, 2, 108, 108, 243, 285, 285, 285, 401 };
		assert searchUnboundArray(array, 243) == 5;
		List<Integer> list = Arrays.asList(3, 2, 1, 5, 6, 4, 3, 2, 5, 4, 1);
		System.out.println(findKthLargestUnknownLength(list.iterator(), 3));
	}
}
