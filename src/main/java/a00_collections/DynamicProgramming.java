package a00_collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class DynamicProgramming {
  /**
   * To illustrate the idea underlying DP, consider the problem of computing Fibonacci numbers. <br>
   * Mathematically, the nth Fibonacci number is given by the equation: F(n) = F(n-1) + F(n-2) with
   * F(0) = 0 and F(1) = 1. <br>
   * The first few Fibonacci numbers are 0, 1, 1, 2, 3, 5, 8, 13, 21,... <br>
   * 
   * Let's walk through different approaches to compute the nth Fibonacci number.
   * 
   * @author lchen
   *
   */
  public class FibonacciNumber {
    /**
     * We can start with a simple recursive implementation. <br>
     * This gives us a runtime of roughly O(2^n), an exponential runtime.
     * 
     * @param n
     * @return
     */
    public int fibonacciI(int n) {
      if (n == 0)
        return 0;
      if (n == 1)
        return 1;
      return fibonacciI(n - 1) + fibonacciI(n - 2);
    }

    /**
     * We still use top-down dynamic programming, but with memorization this time! <br>
     * The runtime is roughly O(n) since we are caching the result and use it later.
     * 
     * @param i
     * @return
     */
    public int fibonacciII(int i) {
      return fibonacciII(i, new int[i + 1]);
    }

    public int fibonacciII(int n, int[] memo) {
      if (n == 0)
        return 0;
      if (n == 1)
        return 1;
      if (memo[n] == 0) {
        memo[n] = fibonacciII(n - 1, memo) + fibonacciII(n - 2, memo);
      }
      return memo[n];
    }

    /**
     * Let's change it to bottom-up dynamic programming, with memorization too! <br>
     * This give us the same O(n) runtime.
     * 
     * @param n
     * @return
     */
    public int fibonacciIII(int n) {
      if (n == 0)
        return 0;
      int[] memo = new int[n + 1];
      memo[0] = 0;
      memo[1] = 1;
      for (int i = 2; i <= n; i++) {
        memo[i] = memo[i - 1] + memo[i - 2];
      }
      return memo[n];
    }

    /**
     * We can even get rid of the memo table, to achieve O(n) time and O(1) space. <br>
     * 
     * @param n
     * @return
     */
    public int fibonacciVI(int n) {
      if (n == 0)
        return 0;
      int a = 0;
      int b = 1;
      for (int i = 2; i <= n; i++) {
        int c = a + b;
        a = b;
        b = c;
      }
      return b;
    }
  }

  public int findHowManyUniquePathsInGridWithObstacles(int[][] obstacleGrid) {
    int width = obstacleGrid[0].length;
    int[] dp = new int[width];
    dp[0] = 1;
    for (int[] row : obstacleGrid) {
      for (int j = 0; j < width; j++) {
        if (row[j] == 1)
          dp[j] = 0;
        else if (j > 0)
          dp[j] += dp[j - 1];
      }
    }
    return dp[width - 1];
  }

  public int longestLineOfConsecutiveOneInMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length, max = 0;
    int[][][] dp = new int[m][n][4]; // 4 directions
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (mat[i][j] == 0)
          continue;
        Arrays.fill(dp[i][j], 1); // init as 1
        if (j > 0)
          dp[i][j][0] += dp[i][j - 1][0]; // left vertical
        if (i > 0 && j > 0)
          dp[i][j][1] += dp[i - 1][j - 1][1]; // up-left diagonal
        if (i > 0)
          dp[i][j][2] += dp[i - 1][j][2]; // up horizontal
        if (i > 0 && j < n - 1)
          dp[i][j][3] += dp[i - 1][j + 1][3]; // up-right anti-diagonal
        for (int k = 0; k < 4; k++) {
          max = Math.max(max, dp[i][j][k]);
        }
      }
    }
    return max;
  }

  // Recursion with memoization
  public boolean wordBreak1(String s, List<String> wordDict) {
    return wordBreak1(s, new HashSet<String>(wordDict), 0, new Boolean[s.length()]);
  }

  public boolean wordBreak1(String s, Set<String> wordDict, int start, Boolean[] memo) {
    if (start == s.length())
      return true;
    if (memo[start] != null)
      return memo[start];
    for (int end = start + 1; end <= s.length(); end++) {
      if (wordDict.contains(s.substring(start, end)) && wordBreak1(s, wordDict, end, memo))
        return memo[start] = true;
    }
    return memo[start] = false;
  }

  // Use Breadth-First-Search
  public boolean wordBreak2(String s, List<String> wordDict) {
    Set<String> wordDictSet = new HashSet<>(wordDict);
    Queue<Integer> queue = new LinkedList<>();
    int[] visited = new int[s.length()];
    queue.add(0);

    while (!queue.isEmpty()) {
      int start = queue.remove();
      if (visited[start] == 0) {
        for (int end = start + 1; end <= s.length(); end++) {
          if (wordDictSet.contains(s.substring(start, end))) {
            queue.add(end);
            if (end == s.length())
              return true;
          }
        }
        visited[start] = 1;
      }
    }

    return false;
  }

  // Dynamic programming
  public boolean wordBreak3(String s, List<String> wordDict) {
    Set<String> wordDictSet = new HashSet<>(wordDict);
    boolean[] found = new boolean[s.length() + 1];
    found[0] = true;
    for (int i = 1; i <= s.length(); i++) {
      for (int j = 0; j < i; j++) {
        if (found[j] && wordDictSet.contains(s.substring(j, i))) {
          found[i] = true;
          break;
        }
      }
    }
    return found[s.length()];
  }

  // Also can apply Trie Tree (or TST) to break earlier (if there were many words to check)

  // Can also resolve the question: Concatenated Words
  // A word can only be formed by words shorter than it. So we can first sort the input
  // by length of each word, and only try to form one word by using words in front of it.
  public List<String> findAllConcatenateWords(String[] words) {
    List<String> result = new ArrayList<>();
    Set<String> preWords = new HashSet<>();
    Arrays.sort(words, (a, b) -> (a.length() - b.length()));
    for (String word : words) {
      if (canForm(word, preWords))
        result.add(word);
      preWords.add(word);
    }
    return result;
  }

  private static boolean canForm(String word, Set<String> dict) {
    if (dict.isEmpty())
      return false;
    boolean[] dp = new boolean[word.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= word.length(); i++) {
      for (int j = 0; j < i; j++) {
        if (dp[i] && dict.contains(word.substring(j, i))) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[word.length()];
  }

}
