package a02_arrays_strings;

/**
 * Let's say a positive integer is a super-palindrome if it is a palindrome, and it is also the
 * square of a palindrome.
 * 
 * Given two positive integers left and right represented as strings, return the number of
 * super-palindromes integers in the inclusive range [left, right].
 * 
 * 
 * <pre>
 * Example 1:

 * Input: left = "4", right = "1000"
 * Output: 4
 * Explanation: 4, 9, 121, and 484 are superpalindromes.
 * Note that 676 is not a superpalindrome: 26 * 26 = 676, but 26 is not a palindrome.
 * </pre>
 *
 * Solution: Say P = R^2 is a superpalindrome. R is a palindrome too. The first half of the digits
 * in R determine R up to two possibilites. e.g. if k = 123, then R = 12321 or R = 123321.
 * 
 */
public class SuperPalindromes {
  public int superpalindromesInRange(String sL, String sR) {
    long left = Long.valueOf(sL);
    long right = Long.valueOf(sR);
    int max = 100000;
    int ans = 0;

    // count odd length;
    for (int k = 1; k < max; k++) {
      StringBuilder sb = new StringBuilder(Integer.toString(k));
      for (int i = sb.length() - 2; i >= 0; i--)
        sb.append(sb.charAt(i));
      long v = Long.valueOf(sb.toString());
      v *= v;
      if (v > right)
        break;
      if (v >= left && isPalindrome(v))
        ans++;
    }

    // count even length;
    for (int k = 1; k < max; k++) {
      StringBuilder sb = new StringBuilder(Integer.toString(k));
      for (int i = sb.length() - 1; i >= 0; i--)
        sb.append(sb.charAt(i));
      long v = Long.valueOf(sb.toString());
      v *= v;
      if (v > right)
        break;
      if (v >= left && isPalindrome(v))
        ans++;
    }

    return ans;
  }

  public boolean isPalindrome(long x) {
    long m = x, n = 0;
    while (m > 0) {
      n = 10 * n + m % 10;
      m /= 10;
    }
    return x == n;
  }

}
