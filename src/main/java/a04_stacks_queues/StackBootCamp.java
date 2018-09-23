package a04_stacks_queues;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class StackBootCamp {
	public int[] dailyTemperatures(int[] temperatures) {
		Deque<Integer> stack = new ArrayDeque<>();
		int[] ans = new int[temperatures.length];
		for (int i = temperatures.length - 1; i >= 0; i--) {
			while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
				stack.pop();
			}
			ans[i] = stack.isEmpty() ? 0 : stack.peek() - i;
			stack.push(i);
		}
		return ans;
	}

	public static String frequencySort(String s) {
		int[] counts = new int[256];
		for (char c : s.toCharArray())
			counts[c]++;
		@SuppressWarnings("unchecked")
		List<Character>[] buckets = new List[s.length() + 1];
		for (int i = 0; i < counts.length; i++) {
			int frequency = counts[i];
			if (buckets[frequency] == null)
				buckets[frequency] = new ArrayList<>();
			buckets[frequency].add((char) i);
		}
		StringBuilder sb = new StringBuilder();
		for (int pos = buckets.length - 1; pos >= 0; pos--) {
			if (buckets[pos] != null) {
				for (char c : buckets[pos]) {
					for (int i = 0; i < pos; i++)
						sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int i = 0; i < ppid.size(); i++) {
			if (ppid.get(i) > 0) {
				List<Integer> children = map.getOrDefault(ppid.get(i), new ArrayList<Integer>());
				children.add(pid.get(i));
				map.put(ppid.get(i), children);
			}
		}
		Queue<Integer> queue = new LinkedList<>();
		List<Integer> list = new ArrayList<>();
		queue.add(kill);
		while (!queue.isEmpty()) {
			int parent = queue.remove();
			list.add(parent);
			if (map.containsKey(parent))
				for (int id : map.get(parent))
					queue.add(id);
		}
		return list;
	}

	public static void main(String[] args) {
		System.out.println(frequencySort("tree"));
	}
}
