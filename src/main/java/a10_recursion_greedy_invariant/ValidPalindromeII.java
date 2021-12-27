package a10_recursion_greedy_invariant;

/**
 * Given a string s, return true if the s can be palindrome after deleting at most one character
 * from it. e.g. for "abca", you could delete the character 'c'.
 */
public class ValidPalindromeII {
  public boolean validPalindrome(String s) {
    return dfs(s, 0, s.length() - 1, 0);
  }

  private boolean dfs(String s, int i, int j, int count) {
    if (count > 1)
      return false;
    while (i < j) {
      if (s.charAt(i) != s.charAt(j))
        return dfs(s, i + 1, j, count + 1) || dfs(s, i, j - 1, count + 1);
      i++;
      j--;
    }
    return true;
  }
}
