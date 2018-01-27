package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * 
 * Some courses may have prerequisites, for example to take course 0 you have to first take course
 * 1, which is expressed as a pair: [0,1]
 * 
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of
 * courses you should take to finish all courses.
 * 
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to
 * finish all courses, return an empty array.
 * 
 * For example:
 * 
 * 2, [[1,0]]
 * 
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0. So
 * the correct course order is [0,1]
 * 
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * 
 * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1
 * and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course
 * order is [0,1,2,3]. Another correct ordering is[0,2,1,3].
 * 
 * Note: The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
 * Read more about how a graph is represented. You may assume that there are no duplicate edges in
 * the input prerequisites.
 * 
 * @author lchen
 *
 */
public class CourseSchedule {
	public int[] findOrderInBFS(int numCourses, int[][] prerequisites) {
		// initialize directed graph
		int[] indegrees = new int[numCourses];
		List<List<Integer>> adjacents = new ArrayList<>(numCourses);
		for (int i = 0; i < numCourses; i++) {
			adjacents.add(new ArrayList<>());
		}
		for (int[] edge : prerequisites) {
			indegrees[edge[0]]++;
			adjacents.get(edge[1]).add(edge[0]);
		}
		// breadth first search
		int[] order = new int[numCourses];
		Queue<Integer> toVisit = new ArrayDeque<>();
		for (int i = 0; i < indegrees.length; i++) {
			if (indegrees[i] == 0)
				toVisit.offer(i);
		}
		int visited = 0;
		while (!toVisit.isEmpty()) {
			int from = toVisit.poll();
			order[visited++] = from;
			for (int to : adjacents.get(from)) {
				indegrees[to]--;
				if (indegrees[to] == 0)
					toVisit.offer(to);
			}
		}
		// should visited all courses
		return visited == indegrees.length ? order : new int[0];
	}

	// track cycle with three states
	public int[] findOrderInDFS(int numCourses, int[][] prerequisites) {
		// initialize directed graph
		List<List<Integer>> adjacents = new ArrayList<>(numCourses);
		for (int i = 0; i < numCourses; i++) {
			adjacents.add(new ArrayList<>());
		}
		for (int[] edge : prerequisites) {
			adjacents.get(edge[1]).add(edge[0]);
		}
		int[] states = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
		Stack<Integer> stack = new Stack<>();
		for (int from = 0; from < numCourses; from++) {
			if (!topologicalSort(adjacents, from, stack, states))
				return new int[0];
		}
		int i = 0;
		int[] order = new int[numCourses];
		while (!stack.isEmpty()) {
			order[i++] = stack.pop();
		}
		return order;
	}

	private boolean topologicalSort(List<List<Integer>> adjacents, int from, Stack<Integer> stack, int[] states) {
		if (states[from] == 1)
			return false;
		if (states[from] == 2)
			return true;
		states[from] = 1; // visiting
		for (Integer to : adjacents.get(from)) {
			if (!topologicalSort(adjacents, to, stack, states))
				return false;
		}
		states[from] = 2; // visited
		stack.push(from);
		return true;
	}

	public static void main(String[] args) {
		CourseSchedule solution = new CourseSchedule();
		int numCourses = 4;
		int[][] prerequisites = new int[][] { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
		int[] order = solution.findOrderInBFS(numCourses, prerequisites);
		assert Arrays.toString(order).equals("[0, 1, 2, 3]");
		System.out.println(Arrays.toString(order));
		order = solution.findOrderInDFS(numCourses, prerequisites);
		assert Arrays.toString(order).equals("[0, 2, 1, 3]");
		System.out.println(Arrays.toString(order));
	}

}
