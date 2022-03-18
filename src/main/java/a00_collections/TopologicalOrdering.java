package a00_collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class TopologicalOrdering {

  // Track cycle with indegrees
  public int[] findOrderInBFS(int numCourses, int[][] prerequisites) {
    // Initialize directed graph
    int[] indegrees = new int[numCourses];
    List<List<Integer>> adjacents = new ArrayList<>(numCourses);
    for (int i = 0; i < numCourses; i++) {
      adjacents.add(new ArrayList<>());
    }
    for (int[] edge : prerequisites) {
      indegrees[edge[0]]++;
      adjacents.get(edge[1]).add(edge[0]);
    }
    // Breadth first search
    int[] order = new int[numCourses];
    Queue<Integer> toVisit = new ArrayDeque<>();
    for (int i = 0; i < indegrees.length; i++) {
      if (indegrees[i] == 0)
        toVisit.offer(i);
    }
    int visited = 0;
    while (!toVisit.isEmpty()) {
      int from = toVisit.poll();
      order[visited++] = from;
      for (int to : adjacents.get(from)) {
        indegrees[to]--;
        if (indegrees[to] == 0)
          toVisit.offer(to);
      }
    }
    // Should visited all courses
    return visited == indegrees.length ? order : new int[0];
  }

  // Track cycle with three states
  public int[] findOrderInDFS(int numCourses, int[][] prerequisites) {
    // Initialize directed graph
    List<List<Integer>> adjacents = new ArrayList<>(numCourses);
    for (int i = 0; i < numCourses; i++) {
      adjacents.add(new ArrayList<>());
    }
    for (int[] edge : prerequisites) {
      adjacents.get(edge[1]).add(edge[0]);
    }
    int[] states = new int[numCourses]; // 0=unvisited, 1=visiting, 2=visited
    Stack<Integer> stack = new Stack<>();
    for (int from = 0; from < numCourses; from++) {
      if (!topologicalSort(adjacents, from, stack, states))
        return new int[0];
    }
    int i = 0;
    int[] order = new int[numCourses];
    while (!stack.isEmpty()) {
      order[i++] = stack.pop();
    }
    return order;
  }

  private boolean topologicalSort(List<List<Integer>> adjacents, int from, Stack<Integer> stack, int[] states) {
    if (states[from] == 1)
      return false;
    if (states[from] == 2)
      return true;
    states[from] = 1; // visiting
    for (Integer to : adjacents.get(from)) {
      if (!topologicalSort(adjacents, to, stack, states))
        return false;
    }
    states[from] = 2; // visited
    stack.push(from);
    return true;
  }

  public String alienOrder(String[] words) {
    Map<Character, Set<Character>> map = new HashMap<>();
    Map<Character, Integer> degree = new HashMap<>();
    StringBuilder result = new StringBuilder();

    for (String word : words) {
      for (char c : word.toCharArray()) {
        degree.put(c, 0);
      }
    }

    for (int i = 0; i < words.length - 1; i++) {
      String curr = words[i];
      String next = words[i + 1];
      for (int j = 0; j < Math.min(curr.length(), next.length()); j++) {
        char c1 = curr.charAt(j);
        char c2 = next.charAt(j);
        if (c1 != c2) {
          if (!map.containsKey(c1))
            map.put(c1, new HashSet<>());
          Set<Character> set = map.get(c1);
          if (!set.contains(c2)) {
            set.add(c2);
            degree.put(c2, degree.getOrDefault(c2, 0) + 1);
          }
          break;
        }
      }
    }

    Queue<Character> queue = new LinkedList<>();
    for (Map.Entry<Character, Integer> entry : degree.entrySet()) {
      if (entry.getValue() == 0)
        queue.add(entry.getKey());
    }
    while (!queue.isEmpty()) {
      char c = queue.remove();
      result.append(c);
      if (map.containsKey(c)) {
        for (char c2 : map.get(c)) {
          degree.put(c2, degree.get(c2) - 1);
          if (degree.get(c2) == 0)
            queue.add(c2);
        }
      }
    }
    if (result.length() != degree.size())
      result.setLength(0);

    return result.toString();
  }

  public static void main(String[] args) {
    TopologicalOrdering solution = new TopologicalOrdering();
    int[][] prerequisites = { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
    assertArrayEquals(new int[] { 0, 1, 2, 3 }, solution.findOrderInBFS(4, prerequisites));
    assertArrayEquals(new int[] { 0, 2, 1, 3 }, solution.findOrderInDFS(4, prerequisites));
    String[] words = { "wrt", "wrf", "er", "ett", "rftt" };
    assertEquals("wertf", solution.alienOrder(words));
  }

}
