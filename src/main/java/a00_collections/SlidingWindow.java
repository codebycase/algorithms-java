package a00_collections;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindow {
  /**
   * Given a string, find the length of the longest substring without repeating characters.
   * 
   * Examples:
   * 
   * Given "abcabcbb", the answer is "abc", which the length is 3.
   * 
   */
  // Use a map to track position easily
  public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    // Integer[] map = new Integer[256]; // Or use an array!
    int max = 0, left = 0, right = 0;
    while (right < s.length()) {
      char c = s.charAt(right);
      if (map.containsKey(c)) {
        left = Math.max(left, map.get(c) + 1);
      }
      map.put(c, right);
      max = Math.max(max, right - left + 1);
      right++;
    }
    return max;
  }

  // Use an array to achieve efficiency
  public int lengthOfLongestSubstring2(String s) {
    boolean[] seen = new boolean[256];
    char[] arr = s.toCharArray();
    int max = 0, left = 0, right = 0;
    // outer loop to increase window
    while (right < arr.length) {
      char c = arr[right];
      if (!seen[c]) {
        seen[c] = true;
      } else {
        max = Math.max(right - left, max);
        // inner loop to decrease window
        while (arr[right] != arr[left]) {
          char c2 = arr[left];
          seen[c2] = false;
          left++;
        }
        left++;
      }
      right++;
    }
    max = Math.max(right - left, max);
    return max;
  }

  public static void main(String[] args) {
    SlidingWindow solution = new SlidingWindow();
    assertEquals(1, solution.lengthOfLongestSubstring("bbbbb"));
    assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
    assertEquals(1, solution.lengthOfLongestSubstring2("bbbbb"));
    assertEquals(3, solution.lengthOfLongestSubstring2("abcabcbb"));

  }
}
