/******************************************************************************
 *  Compilation:  javac BipartiteMatching.java
 *  Execution:    java BipartiteMatching V1 V2 E
 *  Dependencies: BipartiteX.java
 *
 *  Find a maximum cardinality matching (and minimum cardinality vertex cover)
 *  in a bipartite graph using the alternating path algorithm.
 *
 ******************************************************************************/

package a05_graphs_trees_heaps;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * The {@code BipartiteMatching} class represents a data type for computing a <em>maximum
 * (cardinality) matching</em> and a <em>minimum (cardinality) vertex cover</em> in a bipartite
 * graph. A <em>bipartite graph</em> in a graph whose vertices can be partitioned into two disjoint
 * sets such that every edge has one endpoint in either set. A <em>matching</em> in a graph is a
 * subset of its edges with no common vertices. A <em>maximum matching</em> is a matching with the
 * maximum number of edges. A <em>perfect matching</em> is a matching which matches all vertices in
 * the graph. A <em>vertex cover</em> in a graph is a subset of its vertices such that every edge is
 * incident to at least one vertex. A <em>minimum vertex cover</em> is a vertex cover with the
 * minimum number of vertices. By Konig's theorem, in any biparite graph, the maximum number of
 * edges in matching equals the minimum number of vertices in a vertex cover. The maximum matching
 * problem in <em>nonbipartite</em> graphs is also important, but all known algorithms for this more
 * general problem are substantially more complicated.
 * <p>
 * This implementation uses the <em>alternating path algorithm</em>. It is equivalent to reducing to
 * the maximum flow problem and running the augmenting path algorithm on the resulting flow network,
 * but it does so with less overhead. The order of growth of the running time in the worst case is
 * (<em>E</em> + <em>V</em>) <em>V</em>, where <em>E</em> is the number of edges and <em>V</em> is
 * the number of vertices in the graph. It uses extra space (not including the graph) proportional
 * to <em>V</em>.
 * <p>
 * See also {@link HopcroftKarp}, which solves the problem in O(<em>E</em> sqrt(<em>V</em>)) using
 * the Hopcroft-Karp algorithm and <a href =
 * "https://algs4.cs.princeton.edu/65reductions/BipartiteMatchingToMaxflow.java.html">BipartiteMatchingToMaxflow</a>,
 * which solves the problem in O(<em>E V</em>) time via a reduction to maxflow.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/65reductions">Section
 * 6.5</a> <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BipartiteMatching {
	private static final int UNMATCHED = -1;

	private final int V; // number of vertices in the graph
	private BipartiteX bipartition; // the bipartition
	private int cardinality; // cardinality of current matching
	private int[] mate; // mate[v] = w if v-w is an edge in current matching
						// = -1 if v is not in current matching
	private boolean[] inMinVertexCover; // inMinVertexCover[v] = true iff v is in min vertex cover
	private boolean[] marked; // marked[v] = true iff v is reachable via alternating path
	private int[] edgeTo; // edgeTo[v] = w if v-w is last edge on path to w

	/**
	 * Determines a maximum matching (and a minimum vertex cover) in a bipartite graph.
	 */
	public BipartiteMatching(Graph G) {
		bipartition = new BipartiteX(G);
		if (!bipartition.isBipartite()) {
			throw new IllegalArgumentException("graph is not bipartite");
		}

		this.V = G.V();

		// initialize empty matching
		mate = new int[V];
		for (int v = 0; v < V; v++)
			mate[v] = UNMATCHED;

		// alternating path algorithm
		while (hasAugmentingPath(G)) {

			// find one endpoint t in alternating path
			int t = -1;
			for (int v = 0; v < G.V(); v++) {
				if (!isMatched(v) && edgeTo[v] != -1) {
					t = v;
					break;
				}
			}

			// update the matching according to alternating path in edgeTo[] array
			for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
				int w = edgeTo[v];
				mate[v] = w;
				mate[w] = v;
			}
			cardinality++;
		}

		// find min vertex cover from marked[] array
		inMinVertexCover = new boolean[V];
		for (int v = 0; v < V; v++) {
			if (bipartition.color(v) && !marked[v])
				inMinVertexCover[v] = true;
			if (!bipartition.color(v) && marked[v])
				inMinVertexCover[v] = true;
		}

	}

	/*
	 * is there an augmenting path?
	 *   - if so, upon termination adj[] contains the level graph;
	 *   - if not, upon termination marked[] specifies those vertices reachable via an alternating
	 *     path from one side of the bipartition
	 *
	 * an alternating path is a path whose edges belong alternately to the matching and not
	 * to the matching
	 *
	 * an augmenting path is an alternating path that starts and ends at unmatched vertices
	 *
	 * this implementation finds a shortest augmenting path (fewest number of edges), though there
	 * is no particular advantage to do so here
	 */
	private boolean hasAugmentingPath(Graph G) {
		marked = new boolean[V];

		edgeTo = new int[V];
		for (int v = 0; v < V; v++)
			edgeTo[v] = -1;

		// breadth-first search (starting from all unmatched vertices on one side of bipartition)
		Queue<Integer> queue = new LinkedList<Integer>();
		for (int v = 0; v < V; v++) {
			if (bipartition.color(v) && !isMatched(v)) {
				queue.offer(v);
				marked[v] = true;
			}
		}

		// run BFS, stopping as soon as an alternating path is found
		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int w : G.adj(v)) {
				// either (1) forward edge not in matching or (2) backward edge in matching
				if (isResidualGraphEdge(v, w) && !marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					if (!isMatched(w))
						return true;
					queue.offer(w);
				}
			}
		}

		return false;
	}

	// is the edge v-w a forward edge not in the matching or a reverse edge in the matching?
	private boolean isResidualGraphEdge(int v, int w) {
		if ((mate[v] != w) && bipartition.color(v))
			return true;
		if ((mate[v] == w) && !bipartition.color(v))
			return true;
		return false;
	}

	/**
	 * Returns the vertex to which the specified vertex is matched in the maximum matching computed
	 * by the algorithm.
	 */
	public int mate(int v) {
		validate(v);
		return mate[v];
	}

	/**
	 * Returns true if the specified vertex is matched in the maximum matching computed by the
	 * algorithm.
	 */
	public boolean isMatched(int v) {
		validate(v);
		return mate[v] != UNMATCHED;
	}

	/**
	 * Returns the number of edges in a maximum matching.
	 */
	public int size() {
		return cardinality;
	}

	/**
	 * Returns true if the graph contains a perfect matching. That is, the number of edges in a
	 * maximum matching is equal to one half of the number of vertices in the graph (so that every
	 * vertex is matched).
	 */
	public boolean isPerfect() {
		return cardinality * 2 == V;
	}

	/**
	 * Returns true if the specified vertex is in the minimum vertex cover computed by the
	 * algorithm.
	 */
	public boolean inMinVertexCover(int v) {
		validate(v);
		return inMinVertexCover[v];
	}

	private void validate(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	class BipartiteX {
		private static final boolean WHITE = false;

		private boolean isBipartite; // is the graph bipartite?
		private boolean[] color; // color[v] gives vertices on one side of bipartition
		private boolean[] marked; // marked[v] = true if v has been visited in DFS
		private int[] edgeTo; // edgeTo[v] = last edge on path to v
		private Queue<Integer> cycle; // odd-length cycle

		/**
		 * Determines whether an undirected graph is bipartite and finds either a bipartition or an
		 * odd-length cycle.
		 */
		public BipartiteX(Graph G) {
			isBipartite = true;
			color = new boolean[G.V()];
			marked = new boolean[G.V()];
			edgeTo = new int[G.V()];

			for (int v = 0; v < G.V() && isBipartite; v++) {
				if (!marked[v]) {
					bfs(G, v);
				}
			}
			assert check(G);
		}

		private void bfs(Graph G, int s) {
			Queue<Integer> q = new LinkedList<>();
			color[s] = WHITE;
			marked[s] = true;
			q.offer(s);

			while (!q.isEmpty()) {
				int v = q.poll();
				for (int w : G.adj(v)) {
					if (!marked[w]) {
						marked[w] = true;
						edgeTo[w] = v;
						color[w] = !color[v];
						q.offer(w);
					} else if (color[w] == color[v]) {
						isBipartite = false;

						// to form odd cycle, consider s-v path and s-w path
						// and let x be closest node to v and w common to two paths
						// then (w-x path) + (x-v path) + (edge v-w) is an odd-length cycle
						// Note: distTo[v] == distTo[w];
						cycle = new LinkedList<Integer>();
						Stack<Integer> stack = new Stack<Integer>();
						int x = v, y = w;
						while (x != y) {
							stack.push(x);
							cycle.offer(y);
							x = edgeTo[x];
							y = edgeTo[y];
						}
						stack.push(x);
						while (!stack.isEmpty())
							cycle.offer(stack.pop());
						cycle.offer(w);
						return;
					}
				}
			}
		}

		/**
		 * Returns true if the graph is bipartite.
		 */
		public boolean isBipartite() {
			return isBipartite;
		}

		/**
		 * Returns the side of the bipartite that vertex {@code v} is on.
		 */
		public boolean color(int v) {
			validateVertex(v);
			if (!isBipartite)
				throw new UnsupportedOperationException("Graph is not bipartite");
			return color[v];
		}

		/**
		 * Returns an odd-length cycle if the graph is not bipartite, and {@code null} otherwise.
		 */
		public Iterable<Integer> oddCycle() {
			return cycle;
		}

		private boolean check(Graph G) {
			// graph is bipartite
			if (isBipartite) {
				for (int v = 0; v < G.V(); v++) {
					for (int w : G.adj(v)) {
						if (color[v] == color[w]) {
							System.err.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
							return false;
						}
					}
				}
			}
			// graph has an odd-length cycle
			else {
				// verify cycle
				int first = -1, last = -1;
				for (int v : oddCycle()) {
					if (first == -1)
						first = v;
					last = v;
				}
				if (first != last) {
					System.err.printf("cycle begins with %d and ends with %d\n", first, last);
					return false;
				}
			}
			return true;
		}

		// throw an IllegalArgumentException unless {@code 0 <= v < V}
		private void validateVertex(int v) {
			int V = marked.length;
			if (v < 0 || v >= V)
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
		}
	}

}
