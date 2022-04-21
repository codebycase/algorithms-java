package a00_collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import util.State;
import util.Vertex;

public class GraphSearch {

  public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
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

  @SuppressWarnings("unused")
  private void killProcessDfs(Map<Integer, List<Integer>> graph, int kill, List<Integer> list) {
    list.add(kill);
    if (graph.containsKey(kill)) {
      for (int next : graph.get(kill)) {
        killProcessDfs(graph, next, list);
      }
    }
  }

  private void killProcessBfs(Map<Integer, List<Integer>> graph, int kill, List<Integer> list) {
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
        // edgeTo[next] = current; // if we need to track path
        return true;
      }
    }
    current.state = State.VISITED;

    return false;
  }

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

  public int cheapestFlightsWithinKStops(int n, int[][] flights, int src, int dst, int k) {
    int[][] graph = new int[n][n];
    for (int[] flight : flights) {
      graph[flight[0]][flight[1]] = flight[2];
    }

    // minimum costs array
    int[] costs = new int[n];
    // shortest steps array
    int[] stops = new int[n];
    Arrays.fill(costs, Integer.MAX_VALUE);
    Arrays.fill(stops, Integer.MAX_VALUE);
    costs[src] = 0;
    stops[src] = 0;

    // priority queue would contain (node, cost, stop)
    Queue<int[]> minHeap = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
    minHeap.offer(new int[] { src, 0, 0 });

    while (!minHeap.isEmpty()) {
      int[] top = minHeap.poll();
      int city = top[0];
      int cost = top[1];
      int stop = top[2];

      if (city == dst) {
        return cost;
      }

      // if there are no more stops left, continue
      if (stop == k + 1) {
        continue;
      }

      // relax all neighboring edges if possible
      for (int neighbor = 0; neighbor < n; neighbor++) {
        if (graph[city][neighbor] > 0) {
          int nextCost = cost + graph[city][neighbor];
          if (nextCost < costs[neighbor]) { // better cost?
            costs[neighbor] = nextCost;
            minHeap.offer(new int[] { neighbor, nextCost, stop + 1 });
            stops[neighbor] = stop; // does not have to be the shortest path
          } else if (stop < stops[neighbor]) { // better steps?
            minHeap.offer(new int[] { neighbor, nextCost, stop + 1 });
            stops[neighbor] = stop;
          }
        }
      }
    }

    return costs[dst] == Integer.MAX_VALUE ? -1 : costs[dst];
  }

}
