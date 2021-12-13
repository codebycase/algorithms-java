package a05_graphs_trees_heaps;

import java.util.PriorityQueue;
import java.util.Queue;

public class ConnectCitiesWithMinCost {

  public int minimumCost(int n, int[][] edges) {
    if (edges.length < n - 1)
      return -1;

    int[] parent = new int[n + 1];
    for (int i = 0; i < n + 1; i++)
      parent[i] = i;

    Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
    for (int[] edge : edges) {
      queue.add(edge);
    }

    int minCost = 0;
    int edgeCount = 0;
    while (edgeCount < n - 1) {
      int[] edge = queue.remove();
      int parentA = findParent(parent, edge[0]);
      int parentB = findParent(parent, edge[1]);

      if (parentA != parentB) {
        parent[parentA] = parentB;
        edgeCount++;
        minCost += edge[2];
      }
    }

    return minCost;
  }

  private int findParent(int[] parent, int i) {
    if (parent[i] == i)
      return i;
    return parent[i] = findParent(parent, parent[i]);
  }
}
