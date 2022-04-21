package a99_miscellaneous;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import util.TreeNode;

public class Collections {

  /**
   * How to determine a squares based on other 3 corners
   * https://leetcode.com/problems/count-square-submatrices-with-all-ones/
   */
  public int countSquaresSubmatricesWithAllOnes(int[][] matrix) {
    int result = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] > 0 && i > 0 && j > 0) {
          matrix[i][j] = Math.min(matrix[i - 1][j - 1], Math.min(matrix[i - 1][j], matrix[i][j - 1])) + 1;
        }
        result += matrix[i][j];
      }
    }
    return result;
  }

  /**
   * https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/
   */
  public int longestSubsequenceWithGivenDifference(int[] nums, int difference) {
    Map<Integer, Integer> map = new HashMap<>();
    int longest = 0;
    for (int n : nums) {
      map.put(n, map.getOrDefault(n - difference, 0) + 1);
      longest = Math.max(longest, map.get(n));
    }
    return longest;
  }

  /**
   * Use bucket sort https://leetcode.com/problems/minimum-time-difference/
   */
  public int findMinDifference(List<String> timePoints) {
    int length = 24 * 60;
    boolean[] mark = new boolean[length];
    for (String time : timePoints) {
      String[] t = time.split(":");
      int m = Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]);
      if (mark[m]) {
        return 0;
      }
      mark[m] = true;
    }

    int min = Integer.MAX_VALUE;
    int prev = -1, first = -1, last = -1;
    for (int i = 0; i < mark.length; i++) {
      if (mark[i]) {
        if (prev != -1) {
          min = Math.min(min, i - prev);
        }
        if (first == -1) {
          first = i;
        }
        prev = last = i;
      }
    }

    // diff between begin and end time
    min = Math.min(min, (length - last + first));

    return min;
  }

  /**
   * https://leetcode.com/problems/longest-string-chain/ <br>
   * Time: O(N*log(N)+N*L^2))
   */
  public int longestStrChain(String[] words) {
    Map<String, Integer> map = new HashMap<>();
    Arrays.sort(words, (a, b) -> (a.length() - b.length()));
    int longestLength = 0;
    for (String word : words) {
      int presentLength = 0;
      // L^2 for this loop and creating each predecessor
      for (int i = 0; i < word.length(); i++) {
        String predecessor = new StringBuilder(word).deleteCharAt(i).toString();
        presentLength = Math.max(presentLength, map.getOrDefault(predecessor, 0) + 1);
      }
      map.put(word, presentLength);
      longestLength = Math.max(longestLength, presentLength);
    }
    return longestLength;
  }

  // Use hash finger print to encode a word
  // https://leetcode.com/problems/strings-differ-by-one-character/
  public boolean differByOne(String[] dict) {
    int n = dict.length;
    if (n <= 1) {
      return false;
    }
    long[] nums = new long[n];
    for (int i = 0; i < n; i++) {
      long temp = 0;
      for (char ch : dict[i].toCharArray()) {
        temp = temp * 26 + ch - 'a';
      }
      nums[i] = temp;
    }
    int m = dict[0].length();
    long base = 1;
    for (int j = m - 1; j >= 0; j--) {
      Set<Long> set = new HashSet<>();
      for (int i = 0; i < n; i++) {
        if (!set.add(nums[i] - (long) (dict[i].charAt(j) - 'a') * base)) {
          return true;
        }
      }
      base *= 26;
    }
    return false;
  }

  /**
   * Use XOR to compare each row with top row
   * https://leetcode.com/problems/remove-all-ones-with-row-and-column-flips/
   */
  public boolean removeOnes(int[][] grid) {
    if (grid.length == 1 || grid[0].length == 1) {
      return true;
    }

    for (int r = 1; r < grid.length; r++) {
      int xor = grid[0][0] ^ grid[r][0];
      for (int c = 1; c < grid[0].length; c++) {
        if ((grid[0][c] ^ grid[r][c]) != xor) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Binary tree to find track path with DFS
   * https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
   */
  public String getDirections(TreeNode root, int startValue, int destValue) {
    StringBuilder startPath = new StringBuilder();
    StringBuilder destPath = new StringBuilder();
    if (findPath(root, startValue, startPath) && findPath(root, destValue, destPath)) {
      startPath.reverse();
      destPath.reverse();
      int i = 0, len = Math.min(startPath.length(), destPath.length());
      while (i < len && startPath.charAt(i) == destPath.charAt(i)) {
        i++;
      }
      return "U".repeat(startPath.length() - i) + destPath.substring(i);
    }
    return "";
  }

  private boolean findPath(TreeNode node, int value, StringBuilder sb) {
    if (node == null) {
      return false;
    }
    if (node.val == value) {
      return true;
    }

    if (findPath(node.left, value, sb)) {
      sb.append("L");
    } else if (findPath(node.right, value, sb)) {
      sb.append("R");
    }

    return sb.length() > 0;
  }

  // Deep-First Search (Post-Order Traversal)
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // if (root == null || root == p || root == q) // two given nodes in the tree
    if (root == null || root.val == p.val || root.val == q.val)
      return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null)
      return root;
    else
      return left == null ? right : left;
  }

  // Simply move upwards with null checking and switch over
  public TreeNode lowestCommonAncestor(TreeNode p, TreeNode q) {
    TreeNode a = p, b = q;
    while (a != b) {
      a = a == null ? q : a.parent;
      b = b == null ? p : b.parent;
    }
    return a;
  }
  /**
   * Use dp and track both ways <br>
   * https://leetcode.com/problems/maximum-number-of-points-with-cost/
   */
  public long maxNumberOfPointsWithCost(int[][] points) {
    long ans = 0;
    int m = points.length, n = points[0].length;
    long[] dp = new long[n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dp[j] += points[i][j];
      }
      for (int j = 1; j < n; j++) {
        dp[j] = Math.max(dp[j], dp[j - 1] - 1);
      }
      for (int j = n - 2; j >= 0; j--) {
        dp[j] = Math.max(dp[j], dp[j + 1] - 1);
      }
    }
    for (int i = 0; i < n; i++) {
      ans = Math.max(ans, dp[i]);
    }
    return ans;
  }

  /**
   * https://leetcode.com/problems/maximum-performance-of-a-team/ <br>
   * Time: Nlog(N) + Nlog(K)
   */
  public int maxPerformanceInATeam(int n, int[] speed, int[] efficiency, int k) {
    int[][] members = new int[n][2];
    for (int i = 0; i < n; i++) {
      members[i] = new int[] { speed[i], efficiency[i] };
    }
    Arrays.sort(members, (a, b) -> b[1] - a[1]);
    Queue<int[]> queue = new PriorityQueue<>(k, (a, b) -> a[0] - b[0]);
    long speedSum = 0, maxPerf = 0;
    for (int[] member : members) {
      queue.offer(member);
      speedSum += member[0];
      if (queue.size() > k) {
        speedSum -= queue.poll()[0];
      }
      maxPerf = Math.max(maxPerf, speedSum * member[1]);
    }

    return (int) (maxPerf % (long) (1e9 + 7));
  }

  /**
   * https://leetcode.com/problems/maximum-path-quality-of-a-graph/
   */
  public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int[] edge : edges) {
      graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(new int[] { edge[1], edge[2] });
      graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(new int[] { edge[0], edge[2] });
    }

    int[][] memo = new int[values.length][maxTime + 1];
    Set<Integer> seen = new HashSet<>();
    seen.add(0);

    return maximalPathQualityDfs(graph, values, maxTime, memo, seen, 0, values[0], 0, values[0]);
  }

  private int maximalPathQualityDfs(Map<Integer, List<int[]>> graph, int[] values, int maxTime, int[][] memo, Set<Integer> seen, int curNode,
      int curValue, int curTime, int curMax) {
    if (curTime > maxTime)
      return curMax;

    if (memo[curNode][curTime] > curValue)
      return curMax;

    memo[curNode][curTime] = curValue;

    if (curNode == 0) {
      curMax = Math.max(curMax, curValue);
    }

    if (!graph.containsKey(curNode))
      return curMax;

    for (int[] neighbor : graph.get(curNode)) {
      int node = neighbor[0];
      int time = neighbor[1];

      if (seen.contains(node)) {
        curMax = Math.max(curMax, maximalPathQualityDfs(graph, values, maxTime, memo, seen, node, curValue, curTime + time, curMax));
      } else {
        seen.add(node);
        curMax = Math.max(curMax, maximalPathQualityDfs(graph, values, maxTime, memo, seen, node, curValue + values[node], curTime + time, curMax));
        seen.remove(node);
      }
    }

    return curMax;
  }

  /**
   * Use Uinon Find also see FindAllPeopleWithSecret<br>
   * https://leetcode.com/problems/satisfiability-of-equality-equations/
   */
  public boolean equationsPossible(String[] equations) {
    int[] parent = new int[26];
    for (int i = 0; i < 26; i++) {
      parent[i] = i;
    }

    for (String e : equations) {
      if (e.charAt(1) == '=') {
        parent[find(parent, e.charAt(0) - 'a')] = find(parent, e.charAt(3) - 'a');
      }
    }
    for (String e : equations) {
      if (e.charAt(1) == '!') {
        if (find(parent, e.charAt(0) - 'a') == find(parent, e.charAt(3) - 'a')) {
          return false;
        }
      }
    }

    return true;
  }

  private int find(int[] parent, int x) {
    while (x != parent[x]) {
      x = parent[parent[x]];
    }
    return x;
  }

  /**
   * Use sliding window to shift from right to left <br>
   * https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
   */
  public int maxScore(int[] cardPoints, int k) {
    if (cardPoints.length < k)
      return 0;

    int leftScore = 0;
    for (int i = 0; i < k; i++) {
      leftScore += cardPoints[i];
    }

    int totalScore = leftScore;
    int rightScore = 0;
    for (int i = k - 1; i >= 0; i--) {
      leftScore -= cardPoints[i];
      rightScore += cardPoints[cardPoints.length - (k - i)];
      totalScore = Math.max(totalScore, leftScore + rightScore);
    }

    return totalScore;
  }

  /**
   * https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
   */
  public int minMaxPairSumInArray(int[] A) {
    Arrays.sort(A);
    int res = 0, n = A.length;
    // #1 solution
    for (int i = 0; i < n / 2; ++i)
      res = Math.max(res, A[i] + A[n - i - 1]);
    // #2 solution
    int i = 0, j = n - 1;
    while (i < j) {
      res = Math.max(res, A[i++] + A[j--]);
    }
    return res;
  }

  // https://leetcode.com/problems/minimum-number-of-refueling-stops/
  public int minRefuelStops(int target, int startFuel, int[][] stations) {
    Queue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
    int i = 0, n = stations.length, stops = 0, maxDistance = startFuel;
    while (maxDistance < target) {
      while (i < n && stations[i][0] <= maxDistance) {
        queue.offer(stations[i++][1]);
      }
      if (queue.isEmpty())
        return -1;
      maxDistance += queue.poll();
      stops++;
    }
    return stops;
  }

  /**
   * https://leetcode.com/problems/student-attendance-record-ii/
   */
  public int checkStudentAttendanceRecordII(int n) {
    int M = 1000000007;
    long[] PorL = new long[n + 1]; // ending with P or L, no A
    long[] P = new long[n + 1]; // ending with P, no A
    PorL[0] = P[0] = 1;
    PorL[1] = 2;
    P[1] = 1;

    for (int i = 2; i <= n; i++) {
      P[i] = PorL[i - 1];
      PorL[i] = (P[i] + P[i - 1] + P[i - 2]) % M;
    }

    long res = PorL[n];
    for (int i = 0; i < n; i++) { // inserting A into (n-1)-length strings
      long s = (PorL[i] * PorL[n - i - 1]) % M;
      res = (res + s) % M;
    }

    return (int) res;
  }

  /**
   * Use two pointers or DP https://leetcode.com/problems/minimum-window-subsequence/
   */
  public String minWindowSubsequence(String s, String t) {
    int right = 0, minLen = Integer.MAX_VALUE;
    String result = "";

    while (right < s.length()) {
      int ti = 0;

      // use fast pointer to find the last character of t in s
      while (right < s.length()) {
        if (s.charAt(right) == t.charAt(ti)) {
          ti++;
        }
        if (ti == t.length()) {
          break;
        }
        right++;
      }

      // if right pointer is over than boundary
      if (right == s.length()) {
        break;
      }

      // use another slow pointer to traverse from right to left until find first character of t in s
      int left = right;
      ti = t.length() - 1;
      while (left >= 0) {
        if (s.charAt(left) == t.charAt(ti)) {
          ti--;
        }
        if (ti < 0) {
          break;
        }
        left--;
      }

      // if we found another subsequence with smaller length, update result
      if (right - left + 1 < minLen) {
        minLen = right - left + 1;
        result = s.substring(left, right + 1);
      }

      // move right pointer to the next position of left pointer
      right = left + 1;
    }

    return result;
  }

  public String minWindowSubsequence2(String s, String t) {
    int m = s.length(), n = t.length();
    int[][] dp = new int[n + 1][m + 1];
    for (int j = 0; j <= m; j++) {
      dp[0][j] = j + 1;
    }

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (t.charAt(i - 1) == s.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = dp[i][j - 1];
        }
      }
    }

    int start = 0, len = m + 1;
    for (int j = 1; j <= m; j++) {
      if (dp[n][j] != 0) {
        if (j - dp[n][j] + 1 < len) {
          start = dp[n][j] - 1;
          len = j - dp[n][j] + 1;
        }
      }
    }

    return len == m + 1 ? "" : s.substring(start, start + len);
  }

  /**
   * https://leetcode.com/problems/car-fleet/
   */
  public int carFleet(int target, int[] position, int[] speed) {
    int[][] cars = new int[position.length][2];
    for (int i = 0; i < position.length; i++) {
      cars[i] = new int[] { position[i], speed[i] };
    }

    Arrays.sort(cars, (a, b) -> (b[0] - a[0]));

    double timeLast = (target - cars[0][0]) * 1.0 / cars[0][1];

    Stack<Double> stack = new Stack<>();
    stack.push(timeLast);

    for (int i = 1; i < cars.length; i++) {
      double currTime = (target - cars[i][0]) * 1.0 / cars[i][1];
      if (stack.peek() < currTime) {
        stack.push(currTime);
      }
    }

    return stack.size();
  }

  /** Use stack to track longest file path */
  public int longestAbsoluteFilePath(String input) {
    int maxLen = 0;

    Stack<Integer> stack = new Stack<>();
    for (String path : input.split("\n")) {
      int level = path.lastIndexOf("\t") + 1;
      while (level < stack.size()) {
        stack.pop(); // find parent
      }
      int length = stack.isEmpty() ? 0 : stack.peek();
      length += path.length() - level + 1; // remove /t, add /
      stack.push(length);
      if (path.contains(".")) {
        maxLen = Math.max(maxLen, length - 1);
      }
    }

    return maxLen;
  }

  /**
   * https://leetcode.com/problems/swap-adjacent-in-lr-string/
   */
  public boolean canTransform(String start, String end) {
    int i = 0, j = 0, len = start.length();

    while (i < len && j < len) {
      while (i < len && start.charAt(i) == 'X')
        i++;
      while (j < len && end.charAt(j) == 'X')
        j++;
      if (i < len && j < len) {
        char chS = start.charAt(i);
        char chE = end.charAt(j);
        if ((chS != chE) || (chS == 'L' && i < j) || (chS == 'R' && i > j))
          return false;
        i++;
        j++;
      }
    }

    while (i < len) {
      if (start.charAt(i) != 'X')
        return false;
      i++;
    }

    while (j < len) {
      if (end.charAt(j) != 'X')
        return false;
      j++;
    }

    return true;
  }

  /**
   * https://leetcode.com/problems/find-and-replace-in-string/
   */
  public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < indices.length; i++) {
      if (s.startsWith(sources[i], indices[i])) {
        map.put(indices[i], i);
      }
    }

    StringBuilder sb = new StringBuilder();
    int i = 0;
    while (i < s.length()) {
      if (map.containsKey(i)) {
        sb.append(targets[map.get(i)]);
        i += sources[map.get(i)].length();
      } else {
        sb.append(s.charAt(i));
        i++;
      }
    }

    return sb.toString();
  }

  // If indices/sources/targets are all already sorted
  public String findReplaceString2(String s, int[] indices, String[] sources, String[] targets) {
    StringBuilder sb = new StringBuilder();
    int i = 0, j = 0;
    while (i < s.length()) {
      if (i == indices[j] && s.startsWith(sources[j], i)) {
        sb.append(targets[j]);
        i += sources[j].length();
        j++;
      } else {
        sb.append(s.charAt(i));
        i++;
      }
    }
    return sb.toString();
  }

  /**
   * https://leetcode.com/problems/sentence-screen-fitting/ <br>
   * Precalculate the extra spaces with map
   */
  public int sentenceScreenFitting(String[] sentence, int rows, int cols) {
    StringBuilder s = new StringBuilder();
    for (String word : sentence) {
      s.append(word).append(" ");
    }
    int len = s.length(), count = 0;
    int[] map = new int[len];
    for (int i = 1; i < len; ++i) {
      map[i] = s.charAt(i) == ' ' ? 1 : map[i - 1] - 1;
    }
    for (int i = 0; i < rows; ++i) {
      count += cols;
      count += map[count % len];
    }
    return count / len;
  }

  /**
   * How to fill in the gaps and extra spaces
   */
  public List<String> fullTextJustification(String[] words, int maxWidth) {
    List<String> result = new ArrayList<>();
    int start = 0, end = 0;
    while (start < words.length) {
      int count = words[start].length();
      // end is excluded!
      end = start + 1;
      while (end < words.length) {
        // count in the spaces
        if (count + 1 + words[end].length() > maxWidth)
          break;
        count += 1 + words[end].length();
        end++;
      }

      StringBuilder builder = new StringBuilder();
      int gaps = end - 1 - start;
      // left or middle justified
      if (end == words.length || gaps == 0) {
        for (int i = start; i < end; i++) {
          builder.append(words[i]);
          if (i < end - 1)
            builder.append(" ");
        }
        for (int i = builder.length(); i < maxWidth; i++) {
          builder.append(" ");
        }
      } else {
        int spaces = (maxWidth - count) / gaps;
        int rest = (maxWidth - count) % gaps;
        for (int i = start; i < end; i++) {
          builder.append(words[i]);
          if (i < end - 1) {
            builder.append(" ");
            for (int j = 0; j < spaces + (i - start < rest ? 1 : 0); j++)
              builder.append(" ");
          }
        }
      }
      result.add(builder.toString());
      start = end;
    }

    return result;
  }

  /**
   * Bucket sort + frequency + pairing
   * https://leetcode.com/problems/find-original-array-from-doubled-array/
   */
  public int[] findOriginalArray(int[] changed) {
    int length = changed.length;
    if (length == 0 || length % 2 != 0)
      return new int[0];

    int max = Integer.MIN_VALUE;
    for (int c : changed) {
      max = Math.max(max, c);
    }

    // bucket sort + frequency
    int[] freq = new int[max + 1];
    for (int i : changed) {
      freq[i]++;
    }

    int[] ans = new int[length / 2];
    int idx = 0;
    for (int i = 0; i < freq.length; i++) {
      if (freq[i] == 0)
        continue; // no num

      if (i == 0) {
        // expect even zeros to pair themselves
        if (freq[i] % 2 == 1)
          return new int[0];
        // ans[idx] is zero by default
        idx = idx + freq[i] / 2;
      } else {
        // not enough bigger nums to pair
        if (2 * i >= freq.length || freq[2 * i] < freq[i])
          return new int[0];
        // collect valid numbers
        for (int j = 0; j < freq[i]; j++) {
          ans[idx++] = i;
        }
        freq[2 * i] -= freq[i];
      }
    }
    return ans;
  }

  /**
   * https://leetcode.com/problems/array-of-doubled-pairs/
   */
  public boolean canReorderDoubledPairs(int[] arr) {
    Arrays.sort(arr);
    HashMap<Integer, Integer> cnt = new HashMap<>();
    for (int x : arr) {
      cnt.put(x, cnt.getOrDefault(x, 0) + 1);
    }

    for (int x : arr) {
      if (cnt.get(x) == 0)
        continue;
      if (x < 0 && x % 2 != 0)
        return false; // For example: arr=[-5, -2, 1, 2], x = -5, there is no x/2 pair to match
      int y = x > 0 ? x * 2 : x / 2;
      if (cnt.getOrDefault(y, 0) == 0)
        return false; // Don't have the corresponding `y` to match with `x` -> Return IMPOSSIBLE!
      cnt.put(x, cnt.get(x) - 1);
      cnt.put(y, cnt.get(y) - 1);
    }

    return true;
  }

  /**
   * Mark the task id and sort by priority queue. <br>
   * https://leetcode.com/problems/single-threaded-cpu/
   */
  public int[] singleCPUThreadTasksOrder(int[][] tasks) {
    int n = tasks.length;
    int[] ans = new int[n];
    int[][] extTasks = new int[n][3];
    for (int i = 0; i < n; i++) {
      extTasks[i] = new int[] { i, tasks[i][0], tasks[i][1] };
    }
    Arrays.sort(extTasks, (a, b) -> a[1] - b[1]);

    Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] == b[2] ? a[0] - b[0] : a[2] - b[2]);
    int time = 0, ansIdx = 0, taskIdx = 0;

    while (ansIdx < n) {
      // add all tasks in the time
      while (taskIdx < n && extTasks[taskIdx][1] <= time) {
        queue.offer(extTasks[taskIdx++]);
      }
      if (queue.isEmpty()) {
        time = extTasks[taskIdx][1];
        continue;
      }
      int[] bestFit = queue.poll();
      ans[ansIdx++] = bestFit[0];
      // add on processing time
      time += bestFit[2];
    }
    return ans;
  }

  // https://leetcode.com/problems/smallest-rectangle-enclosing-black-pixels/

  /**
   * https://leetcode.com/problems/01-matrix/
   */
  public int[][] shortestDistanceMatrix(int[][] matrix) {
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == 0) {
          // only those added to queue who has 0 value.
          queue.add(new int[] { i, j });
        } else {
          matrix[i][j] = -1; // Need to update
        }
      }
    }
    int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    int distance = 0;
    while (!queue.isEmpty()) {
      distance++;
      for (int size = queue.size(); size > 0; size--) {
        int[] cell = queue.poll();
        for (int[] dir : dirs) {
          int r = cell[0] + dir[0];
          int c = cell[1] + dir[1];
          if (r < 0 || c < 0 || r >= matrix.length || c >= matrix[0].length || matrix[r][c] != -1) {
            continue;
          }
          matrix[r][c] = distance;
          queue.add(new int[] { r, c });
        }
      }
    }
    return matrix;
  }

  /**
   * https://codebycase.github.io/algorithm/a05-graphs-trees-heaps.html#shortest-distance-from-all-buildings
   */
  public int shortestDistanceFromAllBuildings(int[][] grid) {
    int dirs[][] = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    int rows = grid.length, cols = grid[0].length;

    // Store total distance sum for each empty cell to all houses.
    int[][] totalDist = new int[rows][cols];

    int minDist = 0, emptyLandValue = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {

        // Start a BFS from each house.
        if (grid[row][col] == 1) {
          // Reset min distance!
          minDist = Integer.MAX_VALUE;

          Queue<int[]> queue = new LinkedList<>();
          queue.offer(new int[] { row, col });

          int steps = 0; // levels
          while (!queue.isEmpty()) {
            steps++;
            // Iterator on in the same level
            for (int i = queue.size(); i > 0; i--) {
              int[] curr = queue.poll();

              for (int[] dir : dirs) {
                int nextRow = curr[0] + dir[0];
                int nextCol = curr[1] + dir[1];

                // For each cell with the value equal to empty land value,
                // add distance and decrement the cell value by 1 in favor of tracking visited
                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols && grid[nextRow][nextCol] == emptyLandValue) {
                  grid[nextRow][nextCol]--;
                  totalDist[nextRow][nextCol] += steps;

                  queue.offer(new int[] { nextRow, nextCol });
                  minDist = Math.min(minDist, totalDist[nextRow][nextCol]);
                }
              }
            }
          }

          // Abort if not found any empty land
          if (minDist == Integer.MAX_VALUE) {
            return -1;
          }
          // Decrement empty land value to be searched in next iteration.
          emptyLandValue--;
        }
      }
    }

    return minDist;
  }

  /**
   * Optimize Water Distribution
   * https://codebycase.github.io/algorithm/a05-graphs-trees-heaps.html#optimize-water-distribution-in-a-village
   */
  public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
    List<List<int[]>> graph = new ArrayList<>(n + 1);
    for (int i = 0; i < n + 1; i++) {
      graph.add(new ArrayList<int[]>());
    }

    // int[0] is house, int[1] is cost
    Queue<int[]> queue = new PriorityQueue<>(n, (a, b) -> (a[1] - b[1]));

    // Add a virtual vertex 0 for the insided wells cost,
    for (int i = 0; i < wells.length; i++) {
      int[] virtualEdge = { i + 1, wells[i] };
      graph.get(0).add(virtualEdge);
      queue.add(virtualEdge); // starts with well!
    }

    // Add the bidirectional edges to the graph
    for (int[] pipe : pipes) {
      graph.get(pipe[0]).add(new int[] { pipe[1], pipe[2] });
      graph.get(pipe[1]).add(new int[] { pipe[0], pipe[2] });
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

  /**
   * BFS with 3 dimensions memorization
   * https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/ <br>
   * Time: O(m*n*k), Space: O(m*n*k)
   */
  public int shortestPathInGridWithObstaclesElimination(int[][] grid, int k) {
    int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    int m = grid.length, n = grid[0].length;
    boolean[][][] visited = new boolean[m][n][k + 1];
    Queue<int[]> queue = new LinkedList<>();

    queue.offer(new int[] { 0, 0, 0 });
    visited[0][0][0] = true;

    int steps = 0;
    while (!queue.isEmpty()) {
      for (int i = queue.size(); i > 0; i--) {
        int[] node = queue.poll();
        int row = node[0], col = node[1], curK = node[2];

        if (row == m - 1 && col == n - 1) {
          return steps; // reached destination
        }

        for (int[] dir : dirs) {
          int nextR = row + dir[0];
          int nextC = col + dir[1];
          int nextK = curK;
          if (nextR >= 0 && nextR < m && nextC >= 0 && nextC < n) {
            if (grid[nextR][nextC] == 1) {
              nextK++; // try to eliminate
            }
            if (nextK <= k && !visited[nextR][nextC][nextK]) {
              queue.offer(new int[] { nextR, nextC, nextK });
              visited[nextR][nextC][nextK] = true;
            }
          }
        }
      }

      steps++;
    }

    return -1;
  }

  /**
   * https://leetcode.com/problems/strange-printer/
   */
  public int strangePrinter(String s) {
    return strangePrinterDfs(s, new HashMap<String, Integer>());
  }

  private int strangePrinterDfs(String s, Map<String, Integer> map) {
    if (s.length() < 2)
      return s.length();

    if (map.containsKey(s))
      return map.get(s);

    char chr = s.charAt(0);
    int idx = 1;
    while (idx < s.length() && s.charAt(idx) == chr) {
      idx++;
    }
    int best = 1 + strangePrinterDfs(s.substring(idx), map);
    for (int i = idx; i < s.length(); i++) {
      if (s.charAt(i) == chr) {
        best = Math.min(best, strangePrinterDfs(s.substring(idx, i), map) + strangePrinterDfs(s.substring(i), map));
      }
    }

    map.put(s, best);
    return best;
  }

  /**
   * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/
   * Time: O(m * n * 2 ^ (m * n)), Space: O(2 ^ (m * n)).
   */
  public int minFlipsToConvertBinaryMatrix(int[][] mat) {
    int[] dirs = { 0, 0, 1, 0, -1, 0 };
    int start = 0, m = mat.length, n = mat[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        start |= mat[i][j] << (i * n + j);
      }
    }

    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    Set<Integer> seen = new HashSet<>();
    seen.add(start);

    int steps = 0;
    while (!queue.isEmpty()) {
      for (int size = queue.size(); size > 0; size--) {
        int curr = queue.poll();
        if (curr == 0)
          return steps;

        for (int i = 0; i < m; i++) {
          for (int j = 0; j < n; j++) {
            int next = curr;
            for (int k = 0; k < 5; k++) {
              int r = i + dirs[k], c = j + dirs[k + 1];
              if (r >= 0 && r < m && c >= 0 && c < n) {
                next ^= 1 << (r * n + c);
              }
            }
            if (seen.add(next)) {
              queue.offer(next);
            }
          }
        }
      }
      steps++;
    }

    return -1;
  }

  /**
   * https://leetcode.com/problems/evaluate-reverse-polish-notation/
   */
  public int evalRPN(String[] tokens) {
    if (tokens.length == 0)
      return 0;
    Stack<Integer> st = new Stack<>();
    int num1 = 0, num2 = 0;
    for (String s : tokens) {
      switch (s) {
      case "+":
        num1 = st.pop();
        num2 = st.pop();
        st.push(num2 + num1);
        break;
      case "-":
        num1 = st.pop();
        num2 = st.pop();
        st.push(num2 - num1);
        break;
      case "*":
        num1 = st.pop();
        num2 = st.pop();
        st.push(num2 * num1);
        break;
      case "/":
        num1 = st.pop();
        num2 = st.pop();
        st.push(num2 / num1);
        break;
      default:
        st.push(Integer.parseInt(s));
        break;
      }
    }
    return st.pop();
  }

  /** xxxx
   * https://leetcode.com/problems/random-pick-with-weight/
   */
  class RandomPickWithWeight {
    private Random random;
    private int[] prefixSums;

    public RandomPickWithWeight(int[] w) {
      random = new Random();
      prefixSums = new int[w.length];
      prefixSums[0] = w[0];
      for (int i = 1; i < w.length; i++) {
        prefixSums[i] = prefixSums[i - 1] + w[i];
      }
    }

    public int pickIndex() {
      int length = prefixSums.length;
      int target = random.nextInt(prefixSums[length - 1]) + 1;
      // run a binary search to find the target zone
      int low = 0, high = length - 1;
      while (low < high) {
        int mid = low + (high - low) / 2;
        if (target == prefixSums[mid]) {
          return mid;
        } else if (target > prefixSums[mid]) {
          low = mid + 1;
        } else {
          high = mid;
        }
      }
      return low;
    }
  }

  // https://leetcode.com/problems/number-of-matching-subsequences/
  public class MatchingSubsequences {
    class Node {
      String word;
      int index;

      public Node(String word, int index) {
        this.word = word;
        this.index = index;
      }
    }

    public int numMatchingSubseq(String s, String[] words) {
      @SuppressWarnings("unchecked")
      List<Node>[] heads = new ArrayList[26];
      // do not use fill here as it's read only
      // Arrays.fill(heads, new ArrayList<Node>());
      for (int i = 0; i < heads.length; i++) {
        heads[i] = new ArrayList<Node>();
      }
      for (String word : words)
        heads[word.charAt(0) - 'a'].add(new Node(word, 0));

      int result = 0;
      for (char c : s.toCharArray()) {
        List<Node> oldBucket = heads[c - 'a'];
        heads[c - 'a'] = new ArrayList<Node>();

        for (Node node : oldBucket) {
          node.index++;
          if (node.index == node.word.length()) {
            result++;
          } else {
            heads[node.word.charAt(node.index) - 'a'].add(node);
          }
        }
        // oldBucket.clear();
      }

      return result;
    }
  }

  /**
   * Indorder traverse to track the unique pathes
   * https://leetcode.com/problems/find-duplicate-subtrees/
   */
  class FindDuplicateSubtrees {
    List<TreeNode> res = new ArrayList<>();
    Map<String, Integer> freq = new HashMap<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
      dfs(root);
      return res;
    }

    public String dfs(TreeNode root) {
      if (root == null)
        return "#";
      StringBuilder sb = new StringBuilder();
      sb.append("L");
      sb.append(dfs(root.left));
      sb.append(root.val);
      sb.append("R");
      sb.append(dfs(root.right));
      String path = sb.toString();
      freq.put(path, freq.getOrDefault(path, 0) + 1);
      // only need to add once!
      if (freq.get(path) == 2)
        res.add(root);
      return path;
    }
  }

  // https://codebycase.github.io/algorithm/a09-recursion-greedy-invariant.html#trapping-water-in-2d-matrix
  public class TrappingRainWaterII {
    public int trapRainWater(int[][] heights) {
      int m = heights.length, n = heights[0].length;
      boolean[][] visited = new boolean[m][n];
      Queue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);

      // Initially, add all the Cells which are on borders to the queue.
      for (int i = 0; i < m; i++) {
        visited[i][0] = visited[i][n - 1] = true;
        queue.offer(new int[] { i, 0, heights[i][0] });
        queue.offer(new int[] { i, n - 1, heights[i][n - 1] });
      }

      for (int j = 0; j < n; j++) {
        visited[0][j] = visited[m - 1][j] = true;
        queue.offer(new int[] { 0, j, heights[0][j] });
        queue.offer(new int[] { m - 1, j, heights[m - 1][j] });
      }

      // From the borders, pick the shortest cell visited and check its neighbors:
      // if the neighbor is shorter, collect the water it can trap and update its height as its height
      // plus the water trapped add all its neighbors to the queue.
      int result = 0;
      int[][] dirs = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
      while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] dir : dirs) {
          int row = cell[0] + dir[0];
          int col = cell[1] + dir[1];
          if (row >= 0 && row < m && col >= 0 && col < n && !visited[row][col]) {
            visited[row][col] = true;
            result += Math.max(0, cell[2] - heights[row][col]);
            queue.offer(new int[] { row, col, Math.max(cell[2], heights[row][col]) });
          }
        }
      }
      return result;
    }
  }

  /**
   * https://leetcode.com/problems/prefix-and-suffix-search/
   */
  class WordFilter {
    class TrieNode {
      TrieNode[] children;
      int index;

      public TrieNode() {
        children = new TrieNode[27]; // add {
        index = 0;
      }
    }

    TrieNode trie;

    public WordFilter(String[] words) {
      trie = new TrieNode();
      for (int index = 0; index < words.length; index++) {
        String word = words[index] + "{";
        int length = word.length();
        for (int i = 0; i < length; i++) {
          TrieNode node = trie;
          // minus an extra {
          for (int j = i; j < 2 * length - 1; j++) {
            int k = word.charAt(j % length) - 'a';
            if (node.children[k] == null) {
              node.children[k] = new TrieNode();
            }
            node = node.children[k];
            // use the largest
            node.index = index;
          }
        }
      }
    }

    public int f(String prefix, String suffix) {
      TrieNode node = trie;
      for (char c : (suffix + '{' + prefix).toCharArray()) {
        node = node.children[c - 'a'];
        if (node == null)
          return -1;
      }
      return node.index;
    }
  }

  /**
   * https://leetcode.com/problems/rle-iterator/
   */
  class RLEIterator {
    private int index;
    private int[] encoding;

    public RLEIterator(int[] encoding) {
      this.index = 0;
      this.encoding = encoding;
    }

    public int next(int n) {
      while (index < encoding.length && n > encoding[index]) {
        n -= encoding[index];
        index += 2;
      }

      // Make sure index + 1 does not exceed
      if (index >= encoding.length - 1) {
        return -1;
      }

      encoding[index] -= n;
      return encoding[index + 1];
    }
  }

  /**
   * https://leetcode.com/problems/design-excel-sum-formula/
   */
  public class Excel {
    private Cell[][] table;

    public Excel(int height, char width) {
      table = new Cell[height + 1][width - 'A' + 1];
    }

    public void set(int row, char column, int value) {
      int i = column - 'A';
      if (table[row][i] == null)
        table[row][i] = new Cell(value);
      else
        table[row][i].setValue(value);
    }

    public int get(int r, char c) {
      int i = c - 'A';
      return table[r][i] == null ? 0 : table[r][i].getValue();
    }

    public int sum(int row, char column, String[] numbers) {
      int i = column - 'A';
      if (table[row][i] == null) {
        table[row][i] = new Cell(numbers);
      } else {
        table[row][i].setFormula(numbers);
      }
      return table[row][i].getValue();
    }

    private class Cell {
      private int value = 0;
      // Lazy initialization
      private Map<Cell, Integer> formula; // one cell might appear multiple times

      public Cell(int value) {
        setValue(value);
      }

      public Cell(String[] formulaStr) {
        setFormula(formulaStr);
      }

      public void setValue(int value) {
        formula = null;
        this.value = value;
      }

      public void setFormula(String[] numbers) {
        formula = new HashMap<>();
        for (String number : numbers) {
          if (number.indexOf(":") < 0) {
            int[] pos = getPosition(number);
            addFormulaCell(pos[0], pos[1]);
          } else {
            String[] parts = number.split(":");
            int[] startPos = getPosition(parts[0]);
            int[] endPos = getPosition(parts[1]);
            for (int r = startPos[0]; r <= endPos[0]; r++) {
              for (int c = startPos[1]; c <= endPos[1]; c++) {
                addFormulaCell(r, c);
              }
            }
          }
        }
      }

      private int[] getPosition(String number) {
        int[] position = new int[2];
        position[1] = number.charAt(0) - 'A';
        position[0] = Integer.parseInt(number.substring(1));
        return position;
      }

      private void addFormulaCell(int row, int col) {
        if (table[row][col] == null)
          table[row][col] = new Cell(0);
        Cell rangeCell = table[row][col];
        formula.put(rangeCell, (formula.getOrDefault(rangeCell, 0) + 1));
      }

      // recursive method
      private int getValue() {
        if (formula == null)
          return value;
        int sum = 0;
        for (Map.Entry<Cell, Integer> entry : formula.entrySet()) {
          sum += entry.getKey().getValue() * entry.getValue();
        }
        return sum;
      }
    }
  }

  /**
   * Other questions:
   * 
   * https://codebycase.github.io/algorithm/a15-the-honors-question.html#find-kth-largest-element
   * https://leetcode.com/problems/employee-importance/
   * 
   */
  public static void main(String[] args) {
    Collections solution = new Collections();

    String pathText = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext";
    Assert.assertEquals(32, solution.longestAbsoluteFilePath(pathText));

    System.out.println(16 & (16 - 1));
  }

}
