package a05_graphs_trees_heaps;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DegreesOfSeparation {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] visited; // marked[v] = is there an s-v path
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
	private int[] indegrees; // indegrees[v] = number of edges shortest s-v path

	public DegreesOfSeparation(SymbolGraph graph, int source) {
		visited = new boolean[graph.numVertices()];
		indegrees = new int[graph.numVertices()];
		edgeTo = new int[graph.numVertices()];
		validateVertex(source);
		bfs(graph, source);
	}

	private void bfs(SymbolGraph graph, int source) {
		Queue<Integer> queque = new LinkedList<Integer>();
		for (int v = 0; v < graph.numVertices(); v++)
			indegrees[v] = INFINITY;
		indegrees[source] = 0;
		visited[source] = true;
		queque.offer(source);

		while (!queque.isEmpty()) {
			int v = queque.poll();
			for (int w : graph.adjacents(v)) {
				if (!visited[w]) {
					edgeTo[w] = v;
					indegrees[w] = indegrees[v] + 1;
					visited[w] = true;
					queque.offer(w);
				}
			}
		}
	}

	public boolean hasPathTo(int v) {
		validateVertex(v);
		return visited[v];
	}

	/**
	 * Returns the number of edges in a shortest path between the source and target
	 */
	public int indegress(int vertex) {
		validateVertex(vertex);
		return indegrees[vertex];
	}

	public Iterable<Integer> pathTo(int vertex) {
		validateVertex(vertex);
		if (!hasPathTo(vertex))
			return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = vertex; indegrees[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int vertex) {
		int V = visited.length;
		if (vertex < 0 || vertex >= V)
			throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (V - 1));
	}

	public String getPathToTarget(SymbolGraph sg, String target) {
		StringBuilder builder = new StringBuilder();
		if (sg.contains(target)) {
			int t = sg.indexOf(target);
			if (hasPathTo(t)) {
				for (int v : pathTo(t)) {
					if (builder.length() > 0)
						builder.append(" -> ");
					builder.append(sg.nameOf(v));
				}
			} else {
				builder.append(target).append(" not connected.");
			}
		} else {
			builder.append(target).append(" not in database.");
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		SymbolGraph graph = new SymbolGraph("data/movies.txt", "/");
		String sourceName = "Bacon, Kevin";
		if (!graph.contains(sourceName)) {
			System.out.println(sourceName + " not in database.");
			return;
		}

		int source = graph.indexOf(sourceName);
		DegreesOfSeparation bfs = new DegreesOfSeparation(graph, source);

		String target = "Kidman, Nicole";
		String result = bfs.getPathToTarget(graph, target);
		String answer = "Kidman, Nicole -> Cold Mountain (2003) -> Sutherland, Donald (I) -> Animal House (1978) -> Bacon, Kevin";
		assert result.equals(answer);

		target = "Grant, Cary";
		result = bfs.getPathToTarget(graph, target);
		answer = "Grant, Cary -> Charade (1963) -> Matthau, Walter -> JFK (1991) -> Bacon, Kevin";
		assert result.equals(answer);

		target = "Richie, Chen";
		result = bfs.getPathToTarget(graph, target);
		answer = "Richie, Chen not in database.";
		assert result.equals(answer);
	}
}
