package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * 
 * Some courses may have prerequisites, for example to take course 0 you have to first take course
 * 1, which is expressed as a pair: [0,1]
 * 
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of
 * courses you should take to finish all courses.
 * 
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to
 * finish all courses, return an empty array.
 * 
 * For example:
 * 
 * 2, [[1,0]]
 * 
 * There are a total of 2 courses to take. To take course 1 you should have finished course 0. So
 * the correct course order is [0,1]
 * 
 * 4, [[1,0],[2,0],[3,1],[3,2]]
 * 
 * There are a total of 4 courses to take. To take course 3 you should have finished both courses 1
 * and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course
 * order is [0,1,2,3]. Another correct ordering is[0,2,1,3].
 * 
 * Note: The input prerequisites is a graph represented by a list of edges, not adjacency matrices.
 * Read more about how a graph is represented. You may assume that there are no duplicate edges in
 * the input prerequisites.
 * 
 */
public class CourseSchedule {
  public int[] findOrderInBFS(int numCourses, int[][] prerequisites) {
    // initialize directed graph
    int[] indegrees = new int[numCourses];
    List<List<Integer>> adjacents = new ArrayList<>(numCourses);
    for (int i = 0; i < numCourses; i++) {
      adjacents.add(new ArrayList<>());
    }
    for (int[] edge : prerequisites) {
      indegrees[edge[0]]++;
      adjacents.get(edge[1]).add(edge[0]);
    }
    // breadth first search
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
    // should visited all courses
    return visited == indegrees.length ? order : new int[0];
  }

  // track cycle with three states
  public int[] findOrderInDFS(int numCourses, int[][] prerequisites) {
    // initialize directed graph
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

  /**
   * There are n different online courses numbered from 1 to n. You are given an array courses where
   * courses[i] = [durationi, lastDayi] indicate that the ith course should be taken continuously for
   * durationi days and must be finished before or on lastDayi.
   * 
   * You will start on the 1st day and you cannot take two or more courses simultaneously.
   * 
   * Return the maximum number of courses that you can take.
   * 
   * Use priority queue can achieve time complexity: O(nlog(n)).
   */
  public int scheduleCourseIII(int[][] courses) {
    // It's always profitable to take the course with a smaller lastDay
    Arrays.sort(courses, (a, b) -> a[1] - b[1]);
    // It's also profitable to take the course with a smaller duration and replace the larger one.
    Queue<int[]> queue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    int daysUsed = 0;
    for (int[] course : courses) {
      if (daysUsed + course[0] <= course[1]) {
        queue.offer(course);
        daysUsed += course[0];
      } else if (!queue.isEmpty() && queue.peek()[0] > course[0]) {
        daysUsed += course[0] - queue.poll()[0];
        queue.offer(course);
      }
    }
    return queue.size();
  }

  public static void main(String[] args) {
    CourseSchedule solution = new CourseSchedule();
    int numCourses = 4;
    int[][] prerequisites = new int[][] { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } };
    int[] order = solution.findOrderInBFS(numCourses, prerequisites);
    assert Arrays.toString(order).equals("[0, 1, 2, 3]");
    System.out.println(Arrays.toString(order));
    order = solution.findOrderInDFS(numCourses, prerequisites);
    assert Arrays.toString(order).equals("[0, 2, 1, 3]");
    System.out.println(Arrays.toString(order));
  }

}
