package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Deque;
import java.util.LinkedList;
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

  /**
   * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You
   * are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take
   * course ai first if you want to take course bi.
   * 
   * For example, the pair [0, 1] indicates that you have to take course 0 before you can take course
   * 1. Prerequisites can also be indirect. If course a is a prerequisite of course b, and course b is
   * a prerequisite of course c, then course a is a prerequisite of course c.
   * 
   * You are also given an array queries where queries[j] = [uj, vj]. For the jth query, you should
   * answer whether course uj is a prerequisite of course vj or not.
   * 
   * Return a boolean array answer, where answer[j] is the answer to the jth query.
   */
  public List<Boolean> scheduleCourseIV(int numCourses, int[][] prerequisites, int[][] queries) {
    int[] indegrees = new int[numCourses];
    List<List<Integer>> adjacents = new ArrayList<>(numCourses);
    // Use BitFunctions to track the previous courses been taken
    List<BitSet> previousCourses = new ArrayList<>(numCourses);
    for (int i = 0; i < numCourses; i++) {
      adjacents.add(new ArrayList<>());
      previousCourses.add(new BitSet(numCourses));
    }
    for (int[] edge : prerequisites) {
      indegrees[edge[1]]++;
      adjacents.get(edge[0]).add(edge[1]);
    }
    Deque<Integer> toVisit = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (indegrees[i] == 0) {
        toVisit.push(i);
      }
    }
    while (!toVisit.isEmpty()) {
      int index = toVisit.pop();
      for (int v : adjacents.get(index)) {
        previousCourses.get(v).set(index);
        previousCourses.get(v).or(previousCourses.get(index));
        indegrees[v]--;
        if (indegrees[v] == 0) {
          toVisit.push(v);
        }
      }
    }
    List<Boolean> result = new ArrayList<>(queries.length);
    for (int[] query : queries) {
      if (previousCourses.get(query[1]).get(query[0])) {
        result.add(true);
      } else {
        result.add(false);
      }
    }
    return result;
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
