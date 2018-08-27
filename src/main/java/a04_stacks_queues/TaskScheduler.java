package a04_stacks_queues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where
 * different letters represent different tasks.Tasks could be done without original order. Each task
 * could be done in one interval. For each interval, CPU could finish one task or just be idle.
 * 
 * However, there is a non-negative cooling interval n that means between two same tasks, there must
 * be at least n intervals that CPU are doing different tasks or just be idle.
 * 
 * You need to return the least number of intervals the CPU will take to finish all the given tasks.
 * 
 * <pre>
 * Example 1:
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
 * </pre>
 * 
 * @author lchen
 *
 */
public class TaskScheduler {
	public int leastInterval(char[] tasks, int n) {
		int[] counts = new int[26];
		for (char c : tasks)
			counts[c - 'A']++;
		Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
		for (int c : counts) {
			if (c > 0)
				queue.offer(c);
		}
		int time = 0;
		while (!queue.isEmpty()) {
			List<Integer> temp = new ArrayList<>();
			int i = 0;
			while (i <= n) {
				if (!queue.isEmpty()) {
					if (queue.peek() > 1)
						temp.add(queue.poll() - 1);
					else
						queue.poll();
				}
				time++;
				if (queue.isEmpty() && temp.isEmpty()) // last round, no more tasks left!
					break;
				i++;
			}
			for (int t : temp) {
				queue.offer(t);
			}
		}
		return time;
	}

	public int leastInterval2(char[] tasks, int n) {
		int[] counts = new int[26];
		for (char c : tasks)
			counts[c - 'A']++;
		Arrays.sort(counts);
		int maxVal = counts[25] - 1; // the idle slots need not to be considered for the last round!
		int idleSlots = maxVal * n;
		for (int i = 24; i >= 0 && counts[i] > 0; i--) {
			idleSlots -= Math.min(counts[i], maxVal);
		}
		return idleSlots > 0 ? idleSlots + tasks.length : tasks.length;
	}
}
