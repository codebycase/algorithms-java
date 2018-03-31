package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.List;

/**
 * The diameter of a tree is defined to be the length of a longest path in the tree.
 * 
 * @author lchen
 *
 */
public class TreeDiameter {
	public static class TreeNode {
		List<Edge> edges = new ArrayList<>();
	}

	private static class Edge {
		public TreeNode node;
		public Double length;

		public Edge(TreeNode node, Double length) {
			this.node = node;
			this.length = length;
		}
	}

	private static class HeightAndDiameter {
		public Double height;
		public Double diameter;

		public HeightAndDiameter(Double height, Double diameter) {
			this.height = height;
			this.diameter = diameter;
		}
	}

	public static double computeDiameter(TreeNode T) {
		return T != null ? computeHeightAndDiameter(T).diameter : 0.0;
	}

	private static HeightAndDiameter computeHeightAndDiameter(TreeNode r) {
		double diameter = Double.MIN_VALUE;
		double[] heights = { 0.0, 0.0 }; // Stores the max two heights. height[0] is hte max
											// height
		for (Edge e : r.edges) {
			HeightAndDiameter heightDiameter = computeHeightAndDiameter(e.node);
			if (heightDiameter.height + e.length > heights[0]) {
				// Shift and store the max two heights
				heights = new double[] { heightDiameter.height + e.length, heights[0] };
			} else if (heightDiameter.height + e.length > heights[1]) {
				// When only greater than the second height
				heights[1] = heightDiameter.height + e.length;
			}
			// no passing root diameter
			diameter = Math.max(diameter, heightDiameter.diameter);
		}
		// passing root diameter
		return new HeightAndDiameter(heights[0], Math.max(diameter, heights[0] + heights[1]));
	}

	public static void main(String[] args) {
		TreeNode r = null;
		assert (0.0 == computeDiameter(r));
		r = new TreeNode();
		r.edges.add(new Edge(new TreeNode(), 10.0));
		r.edges.get(0).node.edges.add(new Edge(new TreeNode(), 50.0));
		r.edges.add(new Edge(new TreeNode(), 20.0));
		assert (80 == computeDiameter(r));
		System.out.println(computeDiameter(r));
		r.edges.get(0).node.edges.add(new Edge(new TreeNode(), 100.0));
		assert (150 == computeDiameter(r));
		System.out.println(computeDiameter(r));
	}
}
