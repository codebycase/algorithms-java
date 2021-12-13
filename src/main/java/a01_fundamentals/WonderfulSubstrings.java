package a01_fundamentals;

/**
 * 
 * A wonderful string is a string where at most one letter appears an odd number of times.
 * 
 * For example, "ccjjc" and "abab" are wonderful, but "ab" is not. Given a string word that consists
 * of the first ten lowercase English letters ('a' through 'j'), return the number of wonderful
 * non-empty substrings in word. If the same substring appears multiple times in word, then count
 * each occurrence separately.
 * 
 * A substring is a contiguous sequence of characters in a string.
 * 
 * <pre>
 * Example 1:
 * 
 * Input: word = "aba"
 * Output: 4
 * Explanation: The four wonderful substrings are underlined below:
 * - "aba" -> "a"
 * - "aba" -> "b"
 * - "aba" -> "a"
 * - "aba" -> "aba"
 * </pre>
 * 
 * https://leetcode.com/problems/number-of-wonderful-substrings/
 */
public class WonderfulSubstrings {

  public long wonderfulSubstrings(String word) {
    long result = 0;
    // Store frequency of all bitmask combinations from 0b0000000000 to 0b1111111111
    long[] freqMap = new long[1 << ('j' - 'a') + 1]; // max 1024 combinations
    // Set frequency of 0000000000 as 1 when no element was encountered
    freqMap[0] = 1;

    int bitMask = 0;
    for (char c : word.toCharArray()) {
      // Toggling bit of current character to make it from odd to even OR even to odd
      bitMask ^= 1 << (c - 'a');
      // The substring between previous and current bitmask has even characters
      // Add the frequency of previous bitMask as they can all combine with current char
      result += freqMap[bitMask];

      // The substring between previous and current bitmask has odd characters.
      for (char i = 'a'; i <= 'j'; i++) {
        result += freqMap[bitMask ^ 1 << (i - 'a')];
      }

      // Increasing frequency of the current bitmask for future
      freqMap[bitMask]++;
    }

    return result;
  }
}
