/******************************************************************************
 *  Compilation:  javac FordFulkerson.java
 *  Execution:    java FordFulkerson V E
 *  Dependencies: FlowNetwork.java FlowEdge.java Queue.java
 *  Data files:   https://algs4.cs.princeton.edu/65maxflow/tinyFN.txt
 *
 *  Ford-Fulkerson algorithm for computing a max flow and 
 *  a min cut using shortest augmenting path rule.
 *
 ******************************************************************************/

package a05_graphs_trees_heaps;

import java.util.LinkedList;
import java.util.Queue;

import util.Bag;

/**
 * The {@code FordFulkerson} class represents a data type for computing a <em>maximum st-flow</em>
 * and <em>minimum st-cut</em> in a flow network.
 * <p>
 * This implementation uses the <em>Ford-Fulkerson</em> algorithm with the <em>shortest augmenting
 * path</em> heuristic. The constructor takes time proportional to <em>E V</em> (<em>E</em> +
 * <em>V</em>) in the worst case and extra space (not including the network) proportional to
 * <em>V</em>, where <em>V</em> is the number of vertices and <em>E</em> is the number of edges. In
 * practice, the algorithm will run much faster. Afterwards, the {@code inCut()} and {@code value()}
 * methods take constant time.
 * <p>
 * If the capacities and initial flow values are all integers, then this implementation guarantees
 * to compute an integer-valued maximum flow. If the capacities and floating-point numbers, then
 * floating-point roundoff error can accumulate.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/64maxflow">Section
 * 6.4</a> of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class FordFulkerson {
	private static final double FLOATING_POINT_EPSILON = 1E-11;

	private final int V; // number of vertices
	private boolean[] marked; // marked[v] = true if s->v path in residual graph
	private FlowEdge[] edgeTo; // edgeTo[v] = last edge on shortest residual s->v path
	private double value; // current value of max flow

	/**
	 * Compute a maximum flow and minimum cut in the network {@code G} from vertex {@code s} to
	 * vertex {@code t}.
	 */
	public FordFulkerson(FlowNetwork G, int s, int t) {
		V = G.V();
		validate(s);
		validate(t);
		if (s == t)
			throw new IllegalArgumentException("Source equals sink");
		if (!isFeasible(G, s, t))
			throw new IllegalArgumentException("Initial flow is infeasible");

		// while there exists an augmenting path, use it
		value = excess(G, t);
		while (hasAugmentingPath(G, s, t)) {
			// compute bottleneck capacity
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
			}
			// augment flow
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				edgeTo[v].addResidualFlowTo(v, bottle);
			}
			value += bottle;
		}
	}

	public double value() {
		return value;
	}

	public boolean inCut(int v) {
		validate(v);
		return marked[v];
	}

	private void validate(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	// is there an augmenting path?
	// if so, upon termination edgeTo[] will contain a parent-link representation of such a path
	// this implementation finds a shortest augmenting path (fewest number of edges),
	// which performs well both in theory and in practice
	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
		edgeTo = new FlowEdge[G.V()];
		marked = new boolean[G.V()];
		// breadth-first search
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(s);
		marked[s] = true;
		while (!queue.isEmpty() && !marked[t]) {
			int v = queue.poll();
			for (FlowEdge e : G.adj(v)) {
				int w = e.other(v);
				// if residual capacity from v to w
				if (e.residualCapacityTo(w) > 0) {
					if (!marked[w]) {
						edgeTo[w] = e;
						marked[w] = true;
						queue.offer(w);
					}
				}
			}
		}
		// is there an augmenting path?
		return marked[t];
	}

	// return excess flow at vertex v
	private double excess(FlowNetwork G, int v) {
		double excess = 0.0;
		for (FlowEdge e : G.adj(v)) {
			if (v == e.from())
				excess -= e.flow();
			else
				excess += e.flow();
		}
		return excess;
	}

	// return excess flow at vertex v
	private boolean isFeasible(FlowNetwork G, int s, int t) {
		// check that capacity constraints are satisfied
		for (int v = 0; v < G.V(); v++) {
			for (FlowEdge e : G.adj(v)) {
				if (e.flow() < -FLOATING_POINT_EPSILON || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
					System.err.println("Edge does not satisfy capacity constraints: " + e);
					return false;
				}
			}
		}
		// check that net flow into a vertex equals zero, except at source and sink
		if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
			System.err.println("Excess at source = " + excess(G, s));
			System.err.println("Max flow         = " + value);
			return false;
		}
		if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON) {
			System.err.println("Excess at sink   = " + excess(G, t));
			System.err.println("Max flow         = " + value);
			return false;
		}
		for (int v = 0; v < G.V(); v++) {
			if (v == s || v == t)
				continue;
			else if (Math.abs(excess(G, v)) > FLOATING_POINT_EPSILON) {
				System.err.println("Net flow out of " + v + " doesn't equal zero");
				return false;
			}
		}
		return true;
	}

	public class FlowNetwork {
		private final int V;
		private int E;
		private Bag<FlowEdge>[] adj;

		/**
		 * Initializes an empty flow network with {@code V} vertices and 0 edges.
		 */
		public FlowNetwork(int V) {
			if (V < 0)
				throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
			this.V = V;
			this.E = 0;
			adj = (Bag<FlowEdge>[]) new Bag[V];
			for (int v = 0; v < V; v++)
				adj[v] = new Bag<FlowEdge>();
		}

		public int V() {
			return V;
		}

		public int E() {
			return E;
		}

		private void validateVertex(int v) {
			if (v < 0 || v >= V)
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
		}

		public void addEdge(FlowEdge e) {
			int v = e.from();
			int w = e.to();
			validateVertex(v);
			validateVertex(w);
			adj[v].add(e);
			adj[w].add(e);
			E++;
		}

		public Iterable<FlowEdge> adj(int v) {
			validateVertex(v);
			return adj[v];
		}

		// return list of all edges - excludes self loops
		public Iterable<FlowEdge> edges() {
			Bag<FlowEdge> list = new Bag<FlowEdge>();
			for (int v = 0; v < V; v++)
				for (FlowEdge e : adj(v)) {
					if (e.to() != v)
						list.add(e);
				}
			return list;
		}
	}

	class FlowEdge {
		// to deal with floating-point roundoff errors
		private static final double FLOATING_POINT_EPSILON = 1E-10;

		private final int v; // from
		private final int w; // to
		private final double capacity; // capacity
		private double flow; // flow

		public FlowEdge(int v, int w, double capacity) {
			if (v < 0)
				throw new IllegalArgumentException("vertex index must be a non-negative integer");
			if (w < 0)
				throw new IllegalArgumentException("vertex index must be a non-negative integer");
			if (!(capacity >= 0.0))
				throw new IllegalArgumentException("Edge capacity must be non-negative");
			this.v = v;
			this.w = w;
			this.capacity = capacity;
			this.flow = 0.0;
		}

		public FlowEdge(FlowEdge e) {
			this.v = e.v;
			this.w = e.w;
			this.capacity = e.capacity;
			this.flow = e.flow;
		}

		public int from() {
			return v;
		}

		public int to() {
			return w;
		}

		public double capacity() {
			return capacity;
		}

		public double flow() {
			return flow;
		}

		/**
		 * Returns the endpoint of the edge that is different from the given vertex (unless the edge
		 * represents a self-loop in which case it returns the same vertex).
		 */
		public int other(int vertex) {
			if (vertex == v)
				return w;
			else if (vertex == w)
				return v;
			else
				throw new IllegalArgumentException("invalid endpoint");
		}

		/**
		 * Returns the residual capacity of the edge in the direction to the given {@code vertex}.
		 */
		public double residualCapacityTo(int vertex) {
			if (vertex == v)
				return flow; // backward edge
			else if (vertex == w)
				return capacity - flow; // forward edge
			else
				throw new IllegalArgumentException("invalid endpoint");
		}

		/**
		 * Increases the flow on the edge in the direction to the given vertex. If {@code vertex} is
		 * the tail vertex, this increases the flow on the edge by {@code delta}; if {@code vertex}
		 * is the head vertex, this decreases the flow on the edge by {@code delta}.
		 */
		public void addResidualFlowTo(int vertex, double delta) {
			if (!(delta >= 0.0))
				throw new IllegalArgumentException("Delta must be nonnegative");

			if (vertex == v)
				flow -= delta; // backward edge
			else if (vertex == w)
				flow += delta; // forward edge
			else
				throw new IllegalArgumentException("invalid endpoint");

			// round flow to 0 or capacity if within floating-point precision
			if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
				flow = 0;
			if (Math.abs(flow - capacity) <= FLOATING_POINT_EPSILON)
				flow = capacity;

			if (!(flow >= 0.0))
				throw new IllegalArgumentException("Flow is negative");
			if (!(flow <= capacity))
				throw new IllegalArgumentException("Flow exceeds capacity");
		}
	}
}
