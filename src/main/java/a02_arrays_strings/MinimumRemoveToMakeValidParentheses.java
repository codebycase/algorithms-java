package a02_arrays_strings;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
 */
public class MinimumRemoveToMakeValidParentheses {
  public String minRemoveToMakeValid(String s) {
    // Pass 1: Remove all invalid ")"
    StringBuilder sb = new StringBuilder();
    int openSeen = 0;
    int balance = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '(') {
        openSeen++;
        balance++;
      }
      if (c == ')') {
        if (balance == 0)
          continue;
        balance--;
      }
      sb.append(c);
    }
    // Pass 2: Remove the rightmost "("
    StringBuilder result = new StringBuilder();
    int openToKeep = openSeen - balance;
    for (int i = 0; i < sb.length(); i++) {
      char c = sb.charAt(i);
      if (c == '(') {
        openToKeep--;
        if (openToKeep < 0)
          continue;
      }
      result.append(c);
    }

    return result.toString();
  }

  public String minRemoveToMakeValid2(String s) {
    Set<Integer> indexesToRemove = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        stack.push(i);
      }
      if (s.charAt(i) == ')') {
        if (stack.isEmpty()) {
          indexesToRemove.add(i);
        } else {
          stack.pop();
        }
      }
    }
    // Put any indexes remaining on stack into the set.
    while (!stack.isEmpty())
      indexesToRemove.add(stack.pop());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (!indexesToRemove.contains(i)) {
        sb.append(s.charAt(i));
      }
    }
    return sb.toString();
  }

}
