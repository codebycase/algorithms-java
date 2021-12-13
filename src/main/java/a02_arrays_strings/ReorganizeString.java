package a02_arrays_strings;

import org.junit.Assert;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class ReorganizeString {
  /**
   * Given a string s, rearrange the characters of s so that any two adjacent characters are not the
   * same. Return any possible rearrangement of s or return "" if not possible.
   * 
   * Example 1: Input: s = "aab" Output: "aba"
   *
   */
  public String reorganizeString(String s) {
    // count characters
    int[] counts = new int[26];
    for (int i = 0; i < s.length(); i++) {
      counts[s.charAt(i) - 'a']++;
    }
    int maxChar = 0;
    for (int i = 0; i < counts.length; i++) {
      if (counts[i] > counts[maxChar]) {
        maxChar = i;
      }
    }
    if (counts[maxChar] > (s.length() + 1) / 2) {
      return ""; // not possible
    }

    // reorganize string
    int idx = 0;
    char[] chars = new char[s.length()];
    for (int i = 0; i < counts[maxChar]; i++) {
      chars[idx] = (char) (maxChar + 'a');
      idx += 2;
    }
    counts[maxChar] = 0; // update to 0
    for (int i = 0; i < counts.length; i++) {
      for (int j = 0; j < counts[i]; j++) {
        if (idx >= chars.length) {
          idx = 1;
        }
        chars[idx] = (char) (i + 'a');
        idx += 2;
      }
      counts[i] = 0;
    }

    return new String(chars);
  }

  /**
   * Given a string s and an integer k, rearrange s such that the same characters are at least
   * distance k from each other. If it is not possible to rearrange the string, return an empty string
   * "".
   * 
   * Example 1: Input: s = "aabbcc", k = 3 Output: "abcabc" Explanation: The same letters are at least
   * a distance of 3 from each other.
   * 
   * Example 2: Input: s = "aaadbbcc", k = 2 Output: "abacabcd" Explanation: The same letters are at
   * least a distance of 2 from each other.
   */
  public String rearrangeString(String s, int k) {
    if (k == 0) {
      return s;
    }

    int[][] counts = new int[26][2];
    for (int i = 0; i < 26; i++) {
      counts[i] = new int[] { i, 0 };
    }
    for (char c : s.toCharArray()) {
      counts[c - 'a'][1]++;
    }
    Arrays.sort(counts, (a, b) -> (b[1] - a[1]));

    int largestDuplicate = 1;
    for (int i = 1; i < counts.length; i++) {
      if (counts[i][1] == counts[i - 1][1]) {
        largestDuplicate++;
      } else {
        break;
      }
    }

    if (s.length() < (counts[0][1] - 1) * k + largestDuplicate) {
      return "";
    }

    char[] chars = new char[s.length()];
    int pairIndex = 0, stringOffset = s.length() % k - 1;
    stringOffset = stringOffset < 0 ? stringOffset + k : stringOffset;
    int stringIndex = stringOffset;
    for (int i = 0; i < s.length(); i++) {
      if (counts[pairIndex][1] == 0) {
        pairIndex++;
      }
      if (stringIndex >= s.length()) {
        stringOffset--;
        if (stringOffset < 0) {
          stringOffset += k;
        }
        stringIndex = stringOffset;
      }

      chars[stringIndex] = (char) ('a' + counts[pairIndex][0]);
      counts[pairIndex][1]--;
      stringIndex += k;
    }
    return new String(chars);
  }

  // Use priority queue
  public String rearrangeString2(String s, int k) {
    if (k <= 1) {
      return s;
    }

    int[] counts = new int[26];
    for (int i = 0; i < s.length(); i++) {
      counts[s.charAt(i) - 'a']++;
    }

    Queue<Character> queue = new PriorityQueue<>((a, b) -> (counts[b - 'a'] - counts[a - 'a']));
    for (int i = 0; i < counts.length; i++) {
      if (counts[i] != 0) {
        queue.add((char) (i + 'a'));
      }
    }

    StringBuilder builder = new StringBuilder();
    while (!queue.isEmpty()) {
      char c = queue.poll();
      builder.append(c);
      counts[c - 'a']--;
      // look back k distance and put back if applicable
      if (builder.length() >= k) {
        c = builder.charAt(builder.length() - k);
        if (counts[c - 'a'] > 0) {
          queue.add(c);
        }
      }
    }

    return builder.length() != s.length() ? "" : builder.toString();
  }

  public static void main(String[] args) {
    ReorganizeString solution = new ReorganizeString();
    Assert.assertEquals("aba", solution.reorganizeString("aab"));
    Assert.assertEquals("abacbc", solution.reorganizeString("aabbcc"));
    Assert.assertEquals("abacacbd", solution.reorganizeString("aaadbbcc"));

    Assert.assertEquals("a", solution.rearrangeString("a", 0));
    Assert.assertEquals("cbacba", solution.rearrangeString("aabbcc", 3));
    Assert.assertEquals("bacacadb", solution.rearrangeString("aaadbbcc", 2));
    Assert.assertEquals("abcabcad", solution.rearrangeString2("aaadbbcc", 2));
  }
}
