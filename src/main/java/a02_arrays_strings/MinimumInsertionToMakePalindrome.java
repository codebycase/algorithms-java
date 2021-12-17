package a02_arrays_strings;

/**
 * Given a string s. In one step you can insert any character at any index of the string.
 * 
 * Return the minimum number of steps to make s palindrome.
 * 
 * A Palindrome String is one that reads the same backward as well as forward.
 * 
 * <pre>
 * Example 2:
 *
 * Input: s = "mbadm"
 * Output: 2
 * Explanation: String can be "mbdadbm" or "mdbabdm".
 * </pre>
 *
 */
public class MinimumInsertionToMakePalindrome {
  public int minInsertions(String s) {
    int n = s.length();
    if (n <= 1)
      return 0;
    return makePalindrome(s, 0, n - 1, new int[n][n]);
  }

  private int makePalindrome(String s, int i, int j, int[][] dp) {
    if (i >= j)
      return 0;

    if (dp[i][j] > 0)
      return dp[i][j];

    if (s.charAt(i) == s.charAt(j)) {
      dp[i][j] = makePalindrome(s, i + 1, j - 1, dp);
    } else {
      dp[i][j] = 1 + Math.min(makePalindrome(s, i + 1, j, dp), makePalindrome(s, i, j - 1, dp));
    }

    return dp[i][j];
  }
}
