package a04_stacks_queues;

import java.util.Stack;

/**
 * You are given a string s and an integer k, a k duplicate removal consists of choosing k adjacent
 * and equal letters from s and removing them, causing the left and the right side of the deleted
 * substring to concatenate together.
 * 
 * We repeatedly make k duplicate removals on s until we no longer can.
 * 
 * Return the final string after all such duplicate removals have been made. It is guaranteed that
 * the answer is unique.
 * 
 * <pre>
Example:
Input: s = "deeedbbcccbdaa", k = 3
Output: "aa"
Explanation: 
First delete "eee" and "ccc", get "ddbbbdaa"
Then delete "bbb", get "dddaa"
Finally delete "ddd", get "aa"
 * </pre>
 * 
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
 * https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
 */
public class RemoveDuplicates {
  public String removeDuplicates(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (sb.length() > 0 && s.charAt(i) == sb.charAt(sb.length() - 1)) {
        sb.deleteCharAt(sb.length() - 1);
      } else {
        sb.append(s.charAt(i));
      }
    }
    return sb.toString();
  }

  /**
   * Copy characters within the same string using the fast and slow pointers. Each time we need to
   * erase k characters, we just move the slow pointer k positions back.
   */
  public String removeDuplicates(String s, int k) {
    Stack<Integer> counts = new Stack<>();
    char[] sa = s.toCharArray();
    int j = 0; // a slow pointer
    for (int i = 0; i < s.length(); ++i, ++j) {
      sa[j] = sa[i]; // copy current
      if (j == 0 || sa[j] != sa[j - 1]) {
        counts.push(1);
      } else {
        int count = counts.pop() + 1;
        if (count == k) {
          j = j - k; // move back by k
        } else {
          counts.push(count);
        }
      }
    }
    return new String(sa, 0, j);
  }

  class Pair {
    int cnt;
    char ch;

    public Pair(int cnt, char ch) {
      this.ch = ch;
      this.cnt = cnt;
    }
  }

  public String removeDuplicates2(String s, int k) {
    Stack<Pair> counts = new Stack<>();
    for (int i = 0; i < s.length(); ++i) {
      if (counts.empty() || s.charAt(i) != counts.peek().ch) {
        counts.push(new Pair(1, s.charAt(i)));
      } else {
        if (++counts.peek().cnt == k) {
          counts.pop();
        }
      }
    }
    StringBuilder b = new StringBuilder();
    while (!counts.empty()) {
      Pair p = counts.pop();
      for (int i = 0; i < p.cnt; i++) {
        b.append(p.ch);
      }
    }
    return b.reverse().toString();
  }
}
