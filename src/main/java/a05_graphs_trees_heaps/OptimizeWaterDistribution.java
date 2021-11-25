package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * There are n houses in a village. We want to supply water for all the houses by building wells and
 * laying pipes.
 * 
 * For each house i, we can either build a well inside it directly with cost wells[i - 1] (note the
 * -1 due to 0-indexing), or pipe in water from another well to it. The costs to lay pipes between
 * houses are given by the array pipes, where each pipes[j] = [house1, house2, cost] represents the
 * cost to connect house1 and house2 together using a pipe. Connections are bidirectional.
 * 
 * Return the minimum total cost to supply water to all houses.
 *
 * Example 1:
 * 
 * 
 * Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]] <br>
 * Output: 3 <br>
 * Explanation: The image shows the costs of connecting houses using pipes. The best strategy is to
 * build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the
 * total cost is 3.
 * 
 * https://leetcode.com/problems/optimize-water-distribution-in-a-village/
 * 
 * Solution: Convert it into a standard minimum spanning tree (MST) problem and use Prim's
 * algorithm.
 * 
 * Time Complexity: O((N+M)⋅log(N+M)); Space Complexity: O(N+M)
 */
public class OptimizeWaterDistribution {
  public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
    // int[0] is house, int[1] is cost
    Queue<int[]> queue = new PriorityQueue<>(n, (a, b) -> (a[1] - b[1]));

    List<List<int[]>> graph = new ArrayList<>(n + 1);
    for (int i = 0; i < n + 1; ++i) {
      graph.add(new ArrayList<int[]>());
    }

    // Add a virtual vertex 0 for the insided wells cost,
    for (int i = 0; i < wells.length; i++) {
      int[] virtualEdge = { i + 1, wells[i] };
      graph.get(0).add(virtualEdge);
      queue.add(virtualEdge); // starts with well!
    }

    // Add the bidirectional edges to the graph
    for (int i = 0; i < pipes.length; ++i) {
      graph.get(pipes[i][0]).add(new int[] { pipes[i][1], pipes[i][2] });
      graph.get(pipes[i][1]).add(new int[] { pipes[i][0], pipes[i][2] });
    }

    Set<Integer> visited = new HashSet<>();
    visited.add(0); // Already in queue

    int totalCost = 0;
    // n + 1 to cover the virtual vertex 0
    while (visited.size() < n + 1) {
      int[] edge = queue.poll();
      if (visited.contains(edge[0])) {
        continue;
      }

      visited.add(edge[0]);
      totalCost += edge[1];

      for (int[] neighbor : graph.get(edge[0])) {
        if (!visited.contains(neighbor[0])) {
          queue.add(neighbor);
        }
      }
    }

    return totalCost;
  }

}
