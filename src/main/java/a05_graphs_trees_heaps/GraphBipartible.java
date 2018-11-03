package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class GraphBipartible {
    public static boolean isBipartible(Map<String, Set<String>> graph) {
        Map<String, Integer> color = new HashMap<>();
        for (String node : graph.keySet()) {
            color.put(node, -1);
        }
        for (String start : graph.keySet()) {
            if (color.get(start) == -1) {
                Stack<String> stack = new Stack<>();
                stack.push(start);
                color.put(start, 0);
                while (!stack.empty()) {
                    String node = stack.pop();
                    for (String nei : graph.get(node)) {
                        if (color.get(nei) == -1) {
                            stack.push(nei);
                            color.put(nei, color.get(node) ^ 1);
                        } else if (color.get(nei) == color.get(node)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int start = 0; start < n; ++start) {
            if (color[start] == -1) {
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(start);
                color[start] = 0;

                while (!queue.isEmpty()) {
                    Integer node = queue.poll();
                    for (int nei : graph[node]) {
                        if (color[nei] == -1) {
                            queue.offer(nei);
                            color[nei] = color[node] ^ 1;
                        } else if (color[nei] == color[node]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean isBipartite2(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int start = 0; start < n; ++start) {
            if (color[start] == -1) {
                Stack<Integer> stack = new Stack<>();
                stack.push(start);
                color[start] = 0;

                while (!stack.empty()) {
                    Integer node = stack.pop();
                    for (int nei : graph[node]) {
                        if (color[nei] == -1) {
                            stack.push(nei);
                            color[nei] = color[node] ^ 1;
                        } else if (color[nei] == color[node]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean possibleBipartition(int N, int[][] dislikes) {
        // construct graph
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new List[N + 1];
        for (int i = 1; i <= N; ++i)
            graph[i] = new ArrayList<>();

        for (int[] edge : dislikes) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }
        // coloring bipartites
        Integer[] coloring = new Integer[N + 1];
        for (int start = 1; start <= N; start++) {
            if (coloring[start] == null) {
                // use BFS
                /*
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(start);
                coloring[start] = 0;
                while (!queue.isEmpty()) {
                    Integer node = queue.poll();
                    for (int nei : graph[node]) {
                        if (coloring[nei] == null) {
                            queue.offer(nei);
                            coloring[nei] = coloring[node] ^ 1;
                        } else if (coloring[nei].equals(coloring[node])) {
                            return false;
                        }
                    }
                }
                */
                // use DFS
                if (!possibleBipartition(start, 0, graph, coloring))
                    return false;
            }
        }
        return true;
    }

    public static boolean possibleBipartition(int node, int color, List<Integer>[] graph, Integer[] coloring) {
        if (coloring[node] != null)
            return coloring[node] == color;

        coloring[node] = color;

        for (int nei : graph[node])
            if (!possibleBipartition(nei, color ^ 1, graph, coloring))
                return false;

        return true;
    }

    public boolean possibleBipartition2(int N, int[][] dislikes) {
        // construct graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= N; ++i)
            graph.put(i, new HashSet<>());
        for (int[] edge : dislikes) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        // coloring bipartites
        Map<Integer, Integer> coloring = new HashMap<>();
        for (int start : graph.keySet()) {
            if (!coloring.containsKey(start)) {
                // use BFS
                Queue<Integer> queue = new ArrayDeque<>();
                queue.offer(start);
                coloring.put(start, 0);
                while (!queue.isEmpty()) {
                    Integer node = queue.poll();
                    for (int nei : graph.get(node)) {
                        if (!coloring.containsKey(nei)) {
                            queue.offer(nei);
                            coloring.put(nei, coloring.get(node) ^ 1);
                        } else if (coloring.get(nei).equals(coloring.get(node))) {
                            return false;
                        }
                    }
                }
                // use DFS
                /*
                if (!possibleBipartitionDfs(start, 0, graph, coloring))
                    return false;
                */
            }
        }
        return true;
    }

    public boolean possibleBipartition2Dfs(Integer node, Integer color, Map<Integer, Set<Integer>> graph,
            Map<Integer, Integer> coloring) {
        if (coloring.containsKey(node))
            return coloring.get(node).equals(color);

        coloring.put(node, color);

        for (int nei : graph.get(node)) {
            if (!possibleBipartition2Dfs(nei, color ^ 1, graph, coloring))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Map<String, Set<String>> graph = new LinkedHashMap<>();
        graph.put("A", new LinkedHashSet<>(Arrays.asList("B", "D")));
        graph.put("B", new LinkedHashSet<>(Arrays.asList("A", "C")));
        graph.put("C", new LinkedHashSet<>(Arrays.asList("B", "D")));
        graph.put("D", new LinkedHashSet<>(Arrays.asList("A", "C")));
        System.out.println(isBipartible(graph));
        System.out.println(possibleBipartition(4, new int[][] { { 1, 2 }, { 1, 3 }, { 2, 4 } }));
    }
}
