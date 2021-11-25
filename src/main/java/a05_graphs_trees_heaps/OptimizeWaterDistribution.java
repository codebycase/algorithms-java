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
    // min heap to maintain the order of edges to be visited.
    Queue<Pair<Integer>> edgesHeap = new PriorityQueue<>(n, (a, b) -> (a.getKey() - b.getKey()));

    // representation of graph in adjacency list
    List<List<Pair<Integer>>> graph = new ArrayList<>(n + 1);
    for (int i = 0; i < n + 1; ++i) {
      graph.add(new ArrayList<Pair<Integer>>());
    }

    // add a virtual vertex indexed with 0,
    // then add an edge to each of the house weighted by the cost
    for (int i = 0; i < wells.length; ++i) {
      Pair<Integer> virtualEdge = new Pair<>(wells[i], i + 1);
      graph.get(0).add(virtualEdge);
      // initialize the heap with the edges from the virtual vertex.
      edgesHeap.add(virtualEdge);
    }

    // add the bidirectional edges to the graph
    for (int i = 0; i < pipes.length; ++i) {
      int house1 = pipes[i][0];
      int house2 = pipes[i][1];
      int cost = pipes[i][2];
      graph.get(house1).add(new Pair<Integer>(cost, house2));
      graph.get(house2).add(new Pair<Integer>(cost, house1));
    }

    // kick off the exploration from the virtual vertex 0
    Set<Integer> mstSet = new HashSet<>();
    mstSet.add(0);

    int totalCost = 0;
    while (mstSet.size() < n + 1) {
      Pair<Integer> edge = edgesHeap.poll();
      int cost = edge.getKey();
      int nextHouse = edge.getValue();
      if (mstSet.contains(nextHouse)) {
        continue;
      }

      // adding the new vertex into the set
      mstSet.add(nextHouse);
      totalCost += cost;

      // expanding the candidates of edge to choose from in the next round
      for (Pair<Integer> neighborEdge : graph.get(nextHouse)) {
        if (!mstSet.contains(neighborEdge.getValue())) {
          edgesHeap.add(neighborEdge);
        }
      }
    }

    return totalCost;
  }
  
  public class Pair<T> {
    public T left;
    public T right;

    public Pair(T left, T right) {
      this.left = left;
      this.right = right;
    }

    public T getKey() {
      return left;
    }

    public T getValue() {
      return right;
    }
  }  
}
