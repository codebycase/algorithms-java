package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import util.Point;
import util.State;
import util.Vertex;

public class GraphBootCamp {

	/**
	 * Implement a routine that takes an n x m boolean array A together with an entry (x, y) and flips
	 * the color of the region associated with (x, y).
	 * 
	 */
	// Deep-first Search
	public static void flipColorDFS(List<List<Boolean>> A, int x, int y) {
		final int[][] DIRS = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		boolean color = A.get(x).get(y);

		A.get(x).set(y, !color); // flip
		for (int[] dir : DIRS) {
			int nextX = x + dir[0], nextY = y + dir[1];
			if (nextX >= 0 && nextX < A.size() && nextY >= 0 && nextY < A.get(nextX).size()
					&& A.get(nextX).get(nextY) == color) {
				flipColorDFS(A, nextX, nextY);
			}
		}
	}

	// Bread-first Search
	public static void flipColorBFS(List<List<Boolean>> A, int x, int y) {
		final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		boolean color = A.get(x).get(y);

		Queue<Point> queue = new LinkedList<>();
		A.get(x).set(y, !A.get(x).get(y)); // flip
		queue.add(new Point(x, y));
		while (!queue.isEmpty()) {
			Point curr = queue.element();
			for (int[] dir : DIRS) {
				Point next = new Point(curr.x + dir[0], curr.y + dir[1]);
				if (next.x >= 0 && next.x < A.size() && next.y >= 0 && next.y < A.get(x).size()
						&& A.get(next.x).get(next.y) == color) {
					A.get(next.x).set(next.y, !color);
					queue.add(next);
				}
			}
		}
		queue.remove();
	}

	public static void fillEnclosedRegions(List<List<Character>> board) {
		// starting from first or last columns
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i).get(0) == 'W')
				markBoundaryRegion(i, 0, board);
			if (board.get(i).get(board.get(i).size() - 1) == 'W')
				markBoundaryRegion(i, board.get(i).size() - 1, board);
		}
		// starting from first or last rows
		for (int j = 0; j < board.get(0).size(); j++) {
			if (board.get(0).get(j) == 'W')
				markBoundaryRegion(0, j, board);
			if (board.get(board.size() - 1).get(j) == 'W')
				markBoundaryRegion(board.size() - 1, j, board);
		}
		// marks the enclosed white regions as black
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.size(); j++) {
				board.get(i).set(j, board.get(i).get(j) != 'T' ? 'B' : 'W');
			}
		}
	}

	private static void markBoundaryRegion(int i, int j, List<List<Character>> board) {
		Queue<Point> queue = new LinkedList<>();
		queue.add(new Point(i, j));
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			if (curr.x >= 0 && curr.x < board.size() && curr.y >= 0 && curr.y < board.get(curr.x).size()
					&& board.get(curr.x).get(curr.y) == 'W') {
				board.get(curr.x).set(curr.y, 'T');
				queue.add(new Point(curr.x - 1, curr.y));
				queue.add(new Point(curr.x + 1, curr.y));
				queue.add(new Point(curr.x, curr.y + 1));
				queue.add(new Point(curr.x, curr.y - 1));
			}
		}
	}

	/**
	 * One deadlock detection algorithm makes use of a "wait-for" graph: Processes are represented as
	 * nodes, and an edge from process P to Q implies P is waiting for Q to release its lock on the
	 * resource. A cycle in this graph implies the possibility of a deadlock.
	 * 
	 * Write a program that takes as input a directed graph and checks if the graph contains a cycle.
	 * 
	 * @param graph
	 * @return
	 */
	public static boolean isDeadlocked(List<Vertex> graph) {
		for (Vertex vertex : graph) {
			if (vertex.state == State.UNVISITED && hasCycle(vertex)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasCycle(Vertex current) {
		if (current.state == State.VISITING) {
			return true;
		}

		current.state = State.VISITING;
		for (Vertex next : current.edges) {
			if (next.state != State.VISITED && hasCycle(next)) {
				return true;
			}
		}
		current.state = State.VISITED;

		return false;
	}

	/**
	 * In a directed graph, we start at some node and every turn, walk along a directed edge of the
	 * graph. If we reach a node that is terminal, We say our starting node is eventually safe.
	 * 
	 * @param graph
	 * @return
	 */
	// This is a classic "white-gray-black" DFS algorithm
	public List<Integer> eventualSafeNodes(int[][] graph) {
		int N = graph.length;
		int[] color = new int[N];
		List<Integer> ans = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			if (hasNoCycle(i, color, graph))
				ans.add(i);
		}

		return ans;
	}

	// colors: WHITE 0, GRAY 1, BLACK 2;
	private boolean hasNoCycle(int node, int[] color, int[][] graph) {
		if (color[node] > 0)
			return color[node] == 2;

		color[node] = 1;
		for (int nei : graph[node]) {
			if (color[nei] == 2)
				continue;
			if (color[nei] == 1 || !hasNoCycle(nei, color, graph))
				return false;
		}

		color[node] = 2;
		return true;
	}

	public static Vertex cloneGraph(Vertex graph) {
		if (graph == null)
			return null;

		Map<Vertex, Vertex> map = new HashMap<>();
		Queue<Vertex> queue = new LinkedList<>();
		queue.add(graph);
		map.put(graph, new Vertex(graph.id));
		while (!queue.isEmpty()) {
			Vertex v = queue.remove();
			for (Vertex e : v.edges) {
				if (!map.containsKey(e)) {
					map.put(e, new Vertex(e.id));
					queue.add(e);
				}
				map.get(v).edges.add(map.get(e));
			}
		}

		return map.get(graph);
	}

	private static List<Integer> copyLabels(List<Vertex> edges) {
		List<Integer> labels = new ArrayList<>();
		for (Vertex e : edges) {
			labels.add(e.id);
		}
		return labels;
	}

	private static void checkGraph(Vertex node, List<Vertex> g) {
		Set<Vertex> vertexSet = new HashSet<>();
		Queue<Vertex> q = new LinkedList<>();
		q.add(node);
		vertexSet.add(node);
		while (!q.isEmpty()) {
			Vertex vertex = q.remove();
			assert (vertex.id < g.size());
			List<Integer> label1 = copyLabels(vertex.edges), label2 = copyLabels(g.get(vertex.id).edges);
			Collections.sort(label1);
			Collections.sort(label2);
			assert (label1.size() == label2.size());
			assert (Arrays.equals(label1.toArray(), label2.toArray()));
			for (Vertex e : vertex.edges) {
				if (!vertexSet.contains(e)) {
					vertexSet.add(e);
					q.add(e);
				}
			}
		}
	}

	public static void main(String[] args) {
		List<List<Character>> A = Arrays.asList(Arrays.asList('B', 'B', 'B', 'B'), Arrays.asList('W', 'B', 'W', 'B'),
				Arrays.asList('B', 'W', 'W', 'B'), Arrays.asList('B', 'B', 'B', 'B'));
		fillEnclosedRegions(A);
		List<List<Character>> golden = Arrays.asList(Arrays.asList('B', 'B', 'B', 'B'),
				Arrays.asList('W', 'B', 'B', 'B'), Arrays.asList('B', 'B', 'B', 'B'),
				Arrays.asList('B', 'B', 'B', 'B'));
		assert (A.equals(golden));
		// Test Clone Graph
		Random r = new Random();
		for (int times = 0; times < 1000; ++times) {
			List<Vertex> G = new ArrayList<>();
			int n;
			if (args.length == 1) {
				n = Integer.parseInt(args[0]);
			} else {
				n = r.nextInt(100) + 2;
			}
			for (int i = 0; i < n; ++i) {
				G.add(new Vertex(i));
			}
			int m = r.nextInt(n * (n - 1) / 2) + 1;
			boolean[][] doesEdgeExist = new boolean[n][n];
			// Make the graph become connected.
			for (int i = 1; i < n; ++i) {
				G.get(i - 1).edges.add(G.get(i));
				G.get(i).edges.add(G.get(i - 1));
				doesEdgeExist[i - 1][i] = doesEdgeExist[i][i - 1] = true;
			}
			// Generate some edges randomly.
			m -= (n - 1);
			while (m-- > 0) {
				int a, b;
				do {
					a = r.nextInt(n);
					b = r.nextInt(n);
				} while (a == b || doesEdgeExist[a][b]);
				doesEdgeExist[a][b] = doesEdgeExist[b][a] = true;
				G.get(a).edges.add(G.get(b));
				G.get(b).edges.add(G.get(a));
			}
			Vertex res = cloneGraph(G.get(0));
			checkGraph(res, G);
		}
	}

}
