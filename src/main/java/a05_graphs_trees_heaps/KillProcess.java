package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class KillProcess {
	public static List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
		List<Integer> list = new ArrayList<>();
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < ppid.size(); i++) {
			if (ppid.get(i) > 0) {
				graph.putIfAbsent(ppid.get(i), new ArrayList<>());
				graph.get(ppid.get(i)).add(pid.get(i));
			}
		}
		// killProcessDfs(graph, kill, list);
		killProcessBfs(graph, kill, list);
		return list;
	}

	private static void killProcessDfs(Map<Integer, List<Integer>> graph, int kill, List<Integer> list) {
		list.add(kill);
		if (graph.containsKey(kill)) {
			for (int next : graph.get(kill)) {
				killProcessDfs(graph, next, list);
			}
		}
	}

	private static void killProcessBfs(Map<Integer, List<Integer>> graph, int kill, List<Integer> list) {
		Queue<Integer> queue = new ArrayDeque<>();
		queue.offer(kill);
		while (!queue.isEmpty()) {
			int id = queue.poll();
			list.add(id);
			if (graph.containsKey(id)) {
				for (int next : graph.get(kill)) {
					queue.offer(next);
				}
			}
		}
	}

	public List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> result = new ArrayList<>();
		if (nums.length == 0)
			return result;

		// Avoid using hash map
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] < min)
				min = nums[i];
			if (nums[i] > max)
				max = nums[i];
		}
		int[] data = new int[max - min + 1];
		for (int i = 0; i < nums.length; i++) {
			data[nums[i] - min]++;
		}

		// Index is frequency
		@SuppressWarnings("unchecked")
		List<Integer>[] bucket = new ArrayList[nums.length + 1];
		for (int i = 0; i < data.length; i++) {
			if (data[i] > 0) {
				if (bucket[data[i]] == null) {
					bucket[data[i]] = new ArrayList<Integer>();
				}
				List<Integer> list = bucket[data[i]];
				list.add(i + min);
				bucket[data[i]] = list;
			}
		}
		for (int i = nums.length; i >= 0 && result.size() < k; i--) {
			if (bucket[i] != null)
				result.addAll(bucket[i]);
		}
		return result;
	}

	public static void main(String[] args) {
		List<Integer> pid = Arrays.asList(1, 3, 10, 5);
		List<Integer> ppid = Arrays.asList(3, 0, 5, 3);
		System.out.println(killProcess(pid, ppid, 5));
	}
}
