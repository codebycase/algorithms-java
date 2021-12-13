package a07_hash_cache_memory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashTableBootCamp {
  /**
   * <pre>
  Given an array of strings, group anagrams together.
  
  For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"], 
  Return:
  
  [
    ["ate", "eat","tea"],
    ["nat","tan"],
    ["bat"]
  ]
  Note: All inputs will be in lower-case.
   * </pre>
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    int[] prime = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103 };

    List<List<String>> result = new ArrayList<>();
    if (strs == null || strs.length == 0)
      return result;

    Map<Integer, List<String>> map = new HashMap<>();
    for (String s : strs) {
      int key = 1;
      for (char c : s.toCharArray()) {
        key *= prime[c - 'a'];
      }
      if (!map.containsKey(key))
        map.put(key, new ArrayList<>());
      map.get(key).add(s);
    }
    result.addAll(map.values());

    return result;
  }

  public List<List<String>> groupAnagrams2(String[] strs) {
    if (strs.length == 0 || strs.length == 0)
      return new ArrayList<>();
    Map<String, List<String>> ans = new HashMap<>();
    int[] count = new int[26];
    for (String s : strs) {
      Arrays.fill(count, 0);
      for (char c : s.toCharArray())
        count[c - 'a']++;

      StringBuilder sb = new StringBuilder("");
      for (int i = 0; i < 26; i++) {
        sb.append('#');
        sb.append(count[i]);
      }
      
      String key = sb.toString();
      // String key = Arrays.toString(count);
      if (!ans.containsKey(key))
        ans.put(key, new ArrayList<>());
      ans.get(key).add(s);
    }
    return new ArrayList<>(ans.values());
  }

  /**
   * Given a string, find the length of the longest substring without repeating characters.
   * 
   * Examples:
   * 
   * Given "abcabcbb", the answer is "abc", which the length is 3.
   * 
   */
  public int longestDistinctSubset(String s) {
    if (s.length() == 0)
      return 0;
    Map<Character, Integer> map = new HashMap<Character, Integer>();
    int max = 0, l = 0;
    for (int r = 0; r < s.length(); r++) {
      char c = s.charAt(r);
      if (map.containsKey(c)) {
        l = Math.max(l, map.get(c) + 1);
      }
      map.put(c, r);
      max = Math.max(max, r - l + 1);
    }
    return max;
  }

  /**
   * Given a string, find the length of the longest substring T that contains at most k distinct
   * characters.
   * 
   * For example, Given s = “eceba” and k = 2,
   * 
   * T is "ece" which its length is 3.
   */
  // Use a hash map to count the distinct characters and their last occurance.
  public int longestKDistinctSubset(String s, int k) {
    if (s == null || s.length() == 0 || k <= 0)
      return 0;
    int lo = 0, hi = 0, maxLength = 0;
    Map<Character, Integer> map = new HashMap<>();
    while (hi < s.length()) {
      if (map.size() <= k) {
        map.put(s.charAt(hi), hi);
        hi++;
      }
      if (map.size() > k) {
        int leftMost = s.length();
        /*
         * To track the left most last occurance, a better idea is:
         * Use a PriorityQueue which takes O(1) to getMin, but O(n) to update.
         * Or use a TreeMap which takes O(logn) for both getMin and update.
         */
        for (int i : map.values()) {
          leftMost = Math.min(leftMost, i);
        }
        map.remove(s.charAt(leftMost));
        lo = leftMost + 1;
      }

      maxLength = Math.max(maxLength, hi - lo);

    }
    return maxLength;
  }

  /**
   * <pre>
  Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
  
  For example,
  Given [100, 4, 200, 1, 3, 2],
  The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
  
  Your algorithm should run in O(n) complexity.
   * 
   * </pre>
   */
  public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      set.add(num);
    }

    int maxLength = 0;

    for (int num : set) {
      if (!set.contains(num - 1)) {
        int currentNum = num;
        int currentLength = 1;

        while (set.contains(currentNum + 1)) {
          currentNum += 1;
          currentLength += 1;
        }

        maxLength = Math.max(maxLength, currentLength);
      }
    }

    return maxLength;
  }

  public int longestConsecutive2(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    int maxLength = 0;

    for (int num : nums) {
      if (!map.containsKey(num)) {
        int left = map.getOrDefault(num - 1, 0);
        int right = map.getOrDefault(num + 1, 0);
        int total = left + right + 1;

        maxLength = Math.max(maxLength, total);
        map.put(num, total);

        // Only need to update head and tail
        map.put(num - left, total);
        map.put(num + right, total);
      }
    }

    return maxLength;
  }

  /**
   * <pre>
  Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
  
  For example,
  S = "ADOBECODEBANC"
  T = "ABC"
  Minimum window is "BANC".
  
  Note:
  If there is no such window in S that covers all characters in T, return the empty string "".
  
  If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
   * </pre>
   *
   */
  public String minWindowSubset(String s, String t) {
    if (s == null || s.length() == 0 || t == null || t.length() == 0 || s.length() < t.length())
      return "";

    int counter = t.length(), start = -1, end = s.length();
    Deque<Integer> queue = new LinkedList<>();
    Map<Character, Integer> map = new HashMap<>();

    for (char c : t.toCharArray()) {
      map.put(c, map.getOrDefault(c, 0) + 1);
    }

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!map.containsKey(c))
        continue;

      int n = map.get(c);
      queue.add(i);
      map.put(c, n - 1);
      if (n > 0)
        counter--;

      // remove the old/duplicate char index
      char head = s.charAt(queue.peek());
      while (map.get(head) < 0) {
        queue.poll();
        map.put(head, map.get(head) + 1);
        head = s.charAt(queue.peek());
      }

      if (counter == 0) {
        int newLen = queue.peekLast() - queue.peek() + 1;
        if (newLen < end - start) {
          start = queue.peek();
          end = queue.peekLast() + 1;
        }
      }

    }

    if (counter == 0)
      return s.substring(start, end);
    else
      return "";
  }

  /**
   * <pre>
  Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.
  
  If there is no such window in S that covers all characters in T, return the empty string "". If there are multiple such minimum-length windows, return the one with the left-most starting index.
  
  Example 1:
  Input: 
  S = "abcdebdde", T = "bde"
  Output: "bcde"
  Explanation: 
  "bcde" is the answer because it occurs before "bdde" which has the same length.
  "deb" is not a smaller window because the elements of T in the window must occur in order.
   * </pre>
   * 
   */
  public String minWindowSubsequence(String S, String T) {
    String output = "";
    int minLen = 20001;
    for (int i = 0; i <= S.length() - T.length(); i++) {
      while (i < S.length() && S.charAt(i) != T.charAt(0)) {
        i++;
      }
      int l = find(S.substring(i, Math.min(i + minLen, S.length())), T);
      if (l != -1 && l < minLen) {
        minLen = l;
        output = S.substring(i, i + l);
      }
    }
    return output;
  }

  private int find(String S, String T) {
    for (int i = 0, j = 0; i < S.length() && j < T.length();) {
      if (S.charAt(i) == T.charAt(j)) {
        i++;
        j++;
        if (j == T.length()) {
          return i;
        }
      } else {
        i++;
      }
    }
    return -1;
  }

  // Dynamic Programming
  public String minWindowSubsequence2(String S, String T) {
    int m = S.length(), n = T.length();
    int[] dp = new int[m];
    Arrays.fill(dp, -1);
    for (int i = 0; i < m; i++) {
      if (S.charAt(i) == T.charAt(0))
        dp[i] = i;
    }
    for (int j = 1; j < n; j++) {
      int k = -1;
      int[] tmp = new int[m];
      Arrays.fill(tmp, -1);
      for (int i = 0; i < m; i++) {
        if (k != -1 && S.charAt(i) == T.charAt(j))
          tmp[i] = k;
        if (dp[i] != -1)
          k = dp[i];
      }
      dp = tmp; // swap it
    }
    int start = -1, length = Integer.MAX_VALUE;
    // check the last row
    for (int i = 0; i < m; i++) {
      if (dp[i] != -1 && i - dp[i] + 1 < length) {
        start = dp[i];
        length = i - dp[i] + 1;
      }
    }
    return start == -1 ? "" : S.substring(start, start + length);
  }

  public static void main(String[] args) {
    HashTableBootCamp bootCamp = new HashTableBootCamp();
    String[] strs = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };
    assert bootCamp.groupAnagrams2(strs).toString().equals("[[eat, tea, ate], [bat], [tan, nat]]");
    int[] array = new int[] { 3, -2, 7, 9, 8, 1, 2, 0, -1, 5, 8 };
    assert bootCamp.longestConsecutive(array) == 6;
    assert bootCamp.minWindowSubsequence("abcdebdde", "bde").equals("bcde");
    assert bootCamp.minWindowSubsequence2("abcdebdde", "bde").equals("bcde");

    System.out.println(bootCamp.longestDistinctSubset("abcabcdbb"));

  }
}
