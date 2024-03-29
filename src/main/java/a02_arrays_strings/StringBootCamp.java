package a02_arrays_strings;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringBootCamp {
  /**
   * Implement a function that converts a spreadsheet column id to the corresponding integer. For
   * example, 4 for "D", 27 for "AA", 702 for "ZZ", etc.
   * 
   */
  public int decodeColumnId(String colId) {
    int result = 0;
    for (int i = 0; i < colId.length(); i++) {
      result = result * 26 + (colId.charAt(i) - 'A' + 1);
    }
    return result;
  }

  public List<String> findRepeatedDnaSequences(String s) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() < 10)
      return res;

    Set<String> set = new HashSet<>();
    Set<String> ans = new HashSet<>();
    for (int i = 0; i < s.length() - 9; i++) {
      String temp = s.substring(i, i + 10);
      if (set.contains(temp)) {
        ans.add(temp);
      } else {
        set.add(temp);
      }
    }
    res.addAll(ans);
    return res;
  }

  /**
   * Given an array of characters, and remove each 'b' and replaces each 'a' by two 'd's. You can
   * assume the array has enough space to hold the final result.
   */
  public int replaceAndRemove(int size, char[] s) {
    int writeIdx = 0, aCount = 0;
    for (int i = 0; i < size; i++) {
      if (s[i] != 'b')
        s[writeIdx++] = s[i];
      if (s[i] == 'a')
        aCount++;
    }
    int curIdx = writeIdx - 1;
    writeIdx += aCount - 1;
    final int finalSize = writeIdx + 1;
    while (curIdx >= 0) {
      if (s[curIdx] == 'a') {
        s[writeIdx--] = 'd';
        s[writeIdx--] = 'd';
      } else {
        s[writeIdx--] = s[curIdx];
      }
      curIdx--;
    }
    return finalSize;
  }

  /**
   * Given an input string, reverse the string word by word. A word is defined as a sequence of
   * non-space characters.
   * 
   * The input string does not contain leading or trailing spaces and the words are always separated
   * by a single space.
   * 
   * For example, Given s = "the sky is blue", return "blue is sky the".
   * 
   */
  public void reverseWords(char[] str) {
    reverse(str, 0, str.length - 1);
    int start = 0, end = 0;
    while (end < str.length) {
      if (str[end] == ' ') {
        reverse(str, start, end - 1);
        start = end + 1;
      }
      end++;
    }
    reverse(str, start, end - 1);
  }

  private void reverse(char[] str, int start, int end) {
    while (start < end) {
      char tmp = str[start];
      str[start] = str[end];
      str[end] = tmp;
      start++;
      end--;
    }
  }

  /**
   * S and T are strings composed of lowercase letters. In S, no letter occurs more than once.
   * 
   * S was sorted in some custom order previously. We want to permute the characters of T so that they
   * match the order that S was sorted. More specifically, if x occurs before y in S, then x should
   * occur before y in the returned string.
   * 
   * Return any permutation of T (as a string) that satisfies this property.
   * 
   * <pre>
  Example :
  Input: 
  S = "cba"
  T = "abcd"
  Output: "cbad"
  Explanation: 
  "a", "b", "c" appear in S, so the order of "a", "b", "c" should be "c", "b", and "a". 
  Since "d" does not appear in S, it can be at any position in T. "dcba", "cdba", "cbda" are also valid outputs.
   * </pre>
   */
  public String customSortString(String S, String T) {
    int[] count = new int[26];
    for (char c : T.toCharArray())
      count[c - 'a']++;
    StringBuilder sb = new StringBuilder();
    for (char c : S.toCharArray()) {
      for (int i = 0; i < count[c - 'a']; i++)
        sb.append(c);
      count[c - 'a'] = 0;
    }
    for (char c = 'a'; c <= 'z'; c++) {
      for (int i = 0; i < count[c - 'a']; i++)
        sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Given an encoded string, return it's decoded string.
   * 
   * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is
   * being repeated exactly k times. Note that k is guaranteed to be a positive integer.
   * 
   * You may assume that the input string is always valid; No extra white spaces, square brackets are
   * well-formed, etc.
   * 
   * Furthermore, you may assume that the original data does not contain any digits and that digits
   * are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
   * 
   * Examples:
   * 
   * s = "3[a]2[bc]", return "aaabcbc". s = "3[a2[c]]", return "accaccacc". s = "2[abc]3[cd]ef",
   * return "abcabccdcdcdef".
   * 
   * @param s
   * @return
   */
  public String decodeString(String s) {
    Deque<Integer> count = new LinkedList<>();
    Deque<String> result = new LinkedList<>();
    int i = 0;
    result.push("");
    while (i < s.length()) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int start = i;
        // loop through all the neighbor digits
        while (Character.isDigit(s.charAt(i + 1)))
          i++;
        count.push(Integer.valueOf(s.substring(start, i + 1)));
      } else if (c == '[') {
        result.push("");
      } else if (c == ']') {
        String sub = result.pop();
        StringBuilder sb = new StringBuilder();
        int times = count.pop();
        for (int j = 0; j < times; j += 1) {
          sb.append(sub);
        }
        result.push(result.pop() + sb.toString());
      } else {
        result.push(result.pop() + c);
      }
      i++;
    }
    return result.pop();
  }

  /**
   * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and
   * num2. <br>
   * Given two non-negative integers num1 and num2 represented as strings, return the product of num1
   * and num2.
   * 
   * Note:
   * 
   * The length of both num1 and num2 is < 5100. Both num1 and num2 contains only digits 0-9. Both
   * num1 and num2 does not contain any leading zero. You must not use any built-in BigInteger library
   * or convert the inputs to integer directly.
   * 
   * @author lchen
   *
   */
  public String addStrings(String num1, String num2) {
    StringBuilder sb = new StringBuilder();
    int carry = 0;
    for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0; i--, j--) {
      int sum = carry;
      if (i >= 0)
        sum += num1.charAt(i) - '0';
      if (j >= 0)
        sum += num2.charAt(j) - '0';
      sb.append(sum % 10);
      carry = sum / 10;
    }
    if (carry > 0)
      sb.append(carry);
    return sb.reverse().toString();
  }

  public String multiplyStrings(String num1, String num2) {
    int m = num1.length(), n = num2.length();
    int[] pos = new int[m + n];

    for (int i = m - 1; i >= 0; i--) {
      for (int j = n - 1; j >= 0; j--) {
        int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
        int p1 = i + j, p2 = i + j + 1;
        int sum = mul + pos[p2];

        pos[p1] += sum / 10;
        pos[p2] = sum % 10;
      }
    }

    StringBuilder sb = new StringBuilder();
    for (int p : pos) {
      if (sb.length() == 0 && p == 0)
        continue;
      sb.append(p);
    }
    return sb.length() == 0 ? "0" : sb.toString();
  }

  /**
   * Given two strings s (the search string) and t (the text), find the first occurrence of s in t.
   * Return the index of the first character of the substring if found, -1 otherwise.
   * 
   */
  public int rabinKarp(String t, String s) {
    if (s.length() > t.length())
      return -1; // s is not a substring of t.

    final int BASE = 26;
    int tHash = 0, sHash = 0; // Hash codes for the substring of t and s.
    int powerS = 1; // BASE^|s-1|.
    for (int i = 0; i < s.length(); i++) {
      powerS = i > 0 ? powerS * BASE : 1;
      tHash = tHash * BASE + t.charAt(i);
      sHash = sHash * BASE + s.charAt(i);
    }

    for (int i = 0; i <= t.length() - s.length(); i++) {
      // Compare two strings to protest again hash collision!
      if (tHash == sHash && t.substring(i, i + s.length()).equals(s))
        return i;
      tHash -= t.charAt(i) * powerS;
      tHash = tHash * BASE + t.charAt(i + s.length());
    }

    return -1; // s is not a substring of t.
  }

  public List<List<String>> groupStrings(String[] strings) {
    Map<String, List<String>> patternGroups = new HashMap<>();

    for (String str : strings) {
      String pattern = generatePattern(str);
      System.out.println(pattern);
      List<String> currentGroup = patternGroups.getOrDefault(pattern, new ArrayList<>());
      patternGroups.put(pattern, currentGroup);
      currentGroup.add(str);
    }

    List<List<String>> result = new ArrayList<>();
    for (List<String> group : patternGroups.values())
      result.add(group);

    return result;
  }

  private String generatePattern(String str) {
    StringBuilder pattern = new StringBuilder();
    int n = str.length();
    for (int i = 1; i < n; i++) {
      // use s[i]-s[i-1] can also handle different shitting steps
      // e.g. the first letter shift 1 step, second 2 steps...
      int diff = str.charAt(i) - str.charAt(i - 1);
      if (diff < 0)
        diff += 26;
      pattern.append(diff);
      if (i < n - 1)
        pattern.append("-");
    }
    return pattern.toString();
  }

  public int compareVersion(String version1, String version2) {
    int l1 = version1.length(), l2 = version2.length();
    int i = 0, j = 0;
    while (i < l1 || j < l2) {
      int num1 = 0, num2 = 0;
      while (i < l1 && version1.charAt(i) != '.') {
        num1 = num1 * 10 + version1.charAt(i++) - '0';
      }
      while (j < l2 && version2.charAt(j) != '.') {
        num2 = num2 * 10 + version2.charAt(j++) - '0';
      }
      if (num1 > num2)
        return 1;
      else if (num1 < num2)
        return -1;
      else {
        i++;
        j++;
      }
    }
    return 0;
  }

  public boolean validWordAbbreviation(String word, String abbr) {
    int i = 0, j = 0;
    while (i < abbr.length() && j < word.length()) {
      if (Character.isDigit(abbr.charAt(i))) {
        if (abbr.charAt(i) == '0')
          return false;
        int sum = 0;
        while (i < abbr.length() && Character.isDigit(abbr.charAt(i))) {
          sum = sum * 10 + (abbr.charAt(i) - '0');
          i++;
        }
        j += sum;
      } else {
        if (word.charAt(j) == abbr.charAt(i)) {
          i++;
          j++;
        } else {
          return false;
        }
      }
    }
    return i == abbr.length() && j == word.length();
  }

  public static void main(String[] args) {
    StringBootCamp camp = new StringBootCamp();
    assert camp.decodeColumnId("AA") == 27;
    assert camp.decodeColumnId("ZZ") == 702;
    char[] s = { 'a', 'b', 'a', 'c', 0 };
    assert camp.replaceAndRemove(4, s) == 5;
    assert ((new String(s)).equals("ddddc"));
    assert camp.addStrings("123", "321").equals("444");
    assert camp.addStrings("123", "329").equals("452");
    assert camp.addStrings("873", "329").equals("1202");
    assert camp.multiplyStrings("123", "329").equals("40467");
    assert camp.rabinKarp("GACGCCA", "CGC") == 2;
    assert camp.rabinKarp("CGC", "CGC") == 0;
    assert camp.rabinKarp("GATACCCATCGAGTCGGATCGAGT", "GAG") == 10;

    List<List<String>> result = camp.groupStrings(new String[] { "abc", "bcd", "acef", "xyz", "az", "ba", "a", "z" });
    assert result.toString().equals("[[a], [z], [bcd], [ba], [abc], [az], [xyz], [acef]]");
  }
}
