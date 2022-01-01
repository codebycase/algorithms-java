package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JumpGames {
  /**
   * You are given an integer array nums. You are initially positioned at the array's first index, and
   * each element in the array represents your maximum jump length at that position.
   * 
   * Return true if you can reach the last index, or false otherwise.
   * 
   * Solution: could use recursive backtracking + memoization array; top-down or bottom-up dynamic
   * programming; track the furthest position
   */
  // Top-down dynamic programming (Greedy)
  public boolean canJump(int[] nums) {
    int pos = 0;
    for (int i = 0; i < nums.length; i++) {
      if (pos >= nums.length - 1) {
        return true;
      } else if (i > pos) {
        return false;
      } else {
        pos = Math.max(pos, i + nums[i]);
      }
    }
    return false;
  }

  // Bottom-up dynamic programming
  public boolean canJump2(int[] nums) {
    int lastPos = nums.length - 1;
    for (int i = nums.length - 1; i >= 0; i--) {
      if (i + nums[i] >= lastPos) {
        lastPos = i;
      }
    }
    return lastPos == 0;
  }

  /**
   * Given an array of non-negative integers nums, you are initially positioned at the first index of
   * the array.
   * 
   * Each element in the array represents your maximum jump length at that position.
   * 
   * Your goal is to reach the last index in the minimum number of jumps.
   * 
   * You can assume that you can always reach the last index.
   * 
   * Solution: Greedy dynamic programming, please note that we exclude the last element from our
   * iteration because as soon as we reach the last element, we do not need to jump anymore.
   */
  public int jump(int[] nums) {
    int jumps = 0, currentJumpEnd = 0, farthest = 0;
    for (int i = 0; i < nums.length - 1; i++) {
      // we continuously find the how far we can reach in the current jump
      farthest = Math.max(farthest, i + nums[i]);
      // if we have come to the end of the current jump,
      // we need to make another jump
      if (i == currentJumpEnd) {
        jumps++;
        currentJumpEnd = farthest;
      }
    }
    return jumps;
  }

  /**
   * Given an array of non-negative integers arr, you are initially positioned at start index of the
   * array. When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach
   * to any index with value 0.
   * 
   * Notice that you can not jump outside of the array at any time.
   * 
   * Solution: Use either BFS or DFS
   */
  public boolean canReach(int[] arr, int start) {
    if (start >= 0 && start < arr.length) {
      if (arr[start] < 0) {
        return false; // visited
      }
      if (arr[start] == 0) {
        return true; // reached
      }
      arr[start] *= -1; // mark as visited
      return canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);
    }
    return false;
  }

  /**
   * Given an array of integers arr, you are initially positioned at the first index of the array.
   * 
   * In one step you can jump from index i to index: Notice that you can not jump outside of the array
   * at any time.
   * 
   * In one step you can jump from index i to index: <br>
   * 
   * i + 1 where: i + 1 < arr.length. <br>
   * i - 1 where: i - 1 >= 0. <br>
   * j where: arr[i] == arr[j] and i != j. <br>
   * Return the minimum number of steps to reach the last index of the array.
   * 
   * 
   * Solution: Breadth-First Search or Bidirectional BFS
   */
  public int minJumps(int[] arr) {
    if (arr == null || arr.length < 2) {
      return 0;
    }

    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      graph.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
    }

    // int[] {jumpIndex, jumpTimes}
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[] { 0, 0 });
    boolean[] visited = new boolean[arr.length];
    visited[0] = true;

    while (!queue.isEmpty()) {
      for (int i = 0; i < queue.size(); i++) {
        int[] pair = queue.poll();
        if (pair[0] == arr.length - 1) {
          return pair[1];
        }
        int value = arr[pair[0]];
        List<Integer> neighbors = graph.get(value);
        neighbors.add(pair[0] - 1);
        neighbors.add(pair[0] + 1);
        neighbors.forEach(pos ->
          {
            if (pos >= 0 && pos < arr.length && !visited[pos]) {
              queue.offer(new int[] { pos, pair[1] + 1 });
              visited[pos] = true;
            }
          });
        // Clear to prevent stepping back
        neighbors.clear();
      }
    }

    return 0;
  }

  /**
   * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
   * 
   * i + x where: i + x < arr.length and 0 < x <= d. <br>
   * i - x where: i - x >= 0 and 0 < x <= d. <br>
   * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for
   * all indices k between i and j (More formally min(i, j) < k < max(i, j)).
   * 
   * You can choose any index of the array and start jumping. Return the maximum number of indices you
   * can visit.
   * 
   * Notice that you can not jump outside of the array at any time.
   * 
   * Solution: Longest path in a DAG(Directed Acyclic Graph) O(n*d)
   * 
   * https://leetcode.com/problems/jump-game-v/
   */
  public int maxJumps(int[] arr, int d) {
    int dp[] = new int[arr.length];

    int maxJump = 0;
    for (int i = 0; i < arr.length; i++) {
      maxJump = Math.max(maxJump, longestJump(i, arr, dp, d));
    }
    return maxJump;

  }

  private int longestJump(int start, int[] arr, int[] dp, int d) {
    if (dp[start] != 0)
      return dp[start];
    dp[start] = 1;

    int leftBound = Math.max(start - d, 0);
    int rightBound = Math.min(start + d, arr.length - 1);

    // scan left
    for (int i = start - 1; i >= leftBound; i--) {
      if (arr[i] >= arr[start])
        break;
      dp[start] = Math.max(dp[start], longestJump(i, arr, dp, d) + 1);
    }

    // scan right
    for (int i = start + 1; i <= rightBound; i++) {
      if (arr[i] >= arr[start])
        break;
      dp[start] = Math.max(dp[start], longestJump(i, arr, dp, d) + 1);
    }

    return dp[start];
  }

  /**
   * You are given a 0-indexed integer array nums and an integer k.
   * 
   * You are initially standing at index 0. In one move, you can jump at most k steps forward without
   * going outside the boundaries of the array. That is, you can jump from index i to any index in the
   * range [i + 1, min(n - 1, i + k)] inclusive.
   * 
   * You want to reach the last index of the array (index n - 1). Your score is the sum of all nums[j]
   * for each index j you visited in the array.
   * 
   * Return the maximum score you can get.
   * 
   * Solution: Dynamic Programming + Deque (Compressed) + Sliding Window Maximum; <br>
   * Time/Space Complexity: O(N)/O(k)
   * 
   * https://leetcode.com/problems/jump-game-vi/ <br>
   * https://codebycase.github.io/algorithms/a15-the-honors-question.html#sliding-window-maximum
   * 
   */
  public int maxResult(int[] nums, int k) {
    // score represents the max score we can get starting at index i
    // score[i] = max(score[i-k], ..., score[i-1]) + nums[i]
    int score = nums[0];
    Deque<int[]> deque = new LinkedList<>();
    deque.offerLast(new int[] { 0, score });
    for (int i = 1; i < nums.length; i++) {
      // Pop all the indexes smaller than i-k out of deque from top
      while (deque.peekFirst() != null && deque.peekFirst()[0] < i - k) {
        deque.pollFirst();
      }
      score = deque.peekFirst()[1] + nums[i];
      // Keep the maximum value always at the top of the queue.
      while (deque.peekLast() != null && score >= deque.peekLast()[1]) {
        deque.pollLast();
      }
      deque.offerLast(new int[] { i, score });
    }
    return score;
  }

  /**
   * You are given a 0-indexed binary string s and two integers minJump and maxJump. In the beginning,
   * you are standing at index 0, which is equal to '0'. You can move from index i to index j if the
   * following conditions are fulfilled:
   * 
   * i + minJump <= j <= min(i + maxJump, s.length - 1), <br>
   * and s[j] == '0'.
   * 
   * Return true if you can reach index s.length - 1 in s, or false otherwise.
   * 
   * https://leetcode.com/problems/jump-game-vii/
   */
  public boolean canReach(String s, int minJump, int maxJump) {
    int farthest = 0;
    Queue<Integer> queue = new LinkedList<>();
    queue.add(0);

    while (!queue.isEmpty()) {
      int curr = queue.poll();
      int start = Math.max(curr + minJump, farthest + 1);
      int end = Math.min(s.length(), curr + maxJump + 1);
      for (int j = start; j < end; j++) {
        if (s.charAt(j) == '0') {
          if (j == s.length() - 1) {
            return true;
          }
          queue.offer(j);
        }
      }
      farthest = Math.max(farthest, curr + maxJump);
    }

    return false;
  }
}
