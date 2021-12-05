package a02_arrays_strings;

import java.util.Arrays;

/**
 * You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width
 * and the height of an envelope.
 * 
 * One envelope can fit into another if and only if both the width and height of one envelope are
 * greater than the other envelope's width and height.
 * 
 * Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).
 * 
 * Note: You cannot rotate an envelope.
 *
 * Example 1:
 * 
 * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]] <br>
 * Output: 3 <br>
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 * 
 * https://leetcode.com/problems/russian-doll-envelopes/
 * 
 */
public class RussianDollEnvelopes {
  public int maxEnvelopes(int[][] envelopes) {
    // Check envelopes emptiness if neccessary

    // Sort increasing on the first dimension, and decreasing on the second dimension, so that two
    // envelopes that are equal in the first dimension can never be in the same increasing subsequence.
    Arrays.sort(envelopes, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));

    // dp is an array such that dp[i] is the smallest element that ends an increasing subsequence of
    // length i + 1. Whenever we encounter a new element e, we binary search inside dp to find the
    // largest index i such that e can end that subsequence. We then update dp[i] with e.
    int length = 0;
    int dp[] = new int[envelopes.length];
    for (int[] envelope : envelopes) {
      int height = envelope[1];
      if (length == 0 || height > dp[length - 1]) {
        dp[length++] = height;
      } else {
        int index = Arrays.binarySearch(dp, 0, length, height);
        dp[index < 0 ? -(index + 1) : index] = height;
      }
    }
    return length;
  }
}
