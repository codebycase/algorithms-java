package a00_collections;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import a04_stacks_queues.LongestAbsoluteFilePath;

public class StackAndQueue {
  public String decodeString(String s) {
    Deque<Integer> count = new LinkedList<>();
    Deque<String> result = new LinkedList<>();
    int i = 0;
    result.push("");
    while (i < s.length()) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        int start = i;
        while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
          i++;
        }
        count.push(Integer.valueOf(s.substring(start, i + 1)));
      } else if (c == '[') {
        result.push("");
      } else if (c == ']') {
        String sub = result.pop();
        StringBuilder sb = new StringBuilder();
        for (int times = count.pop(); times > 0; times--) {
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

  public int lengthLongestPath(String input) {
    int maxLen = 0;

    Stack<Integer> stack = new Stack<>();
    for (String path : input.split("\n")) {
      int level = path.lastIndexOf("\t") + 1;
      while (level < stack.size()) {
        stack.pop(); // find parent
      }
      int length = stack.isEmpty() ? 0 : stack.peek();
      length += path.length() - level + 1; // remove /t, add /
      stack.push(length);
      if (path.contains(".")) {
        maxLen = Math.max(maxLen, length - 1);
      }
    }

    return maxLen;
  }

  public static void main(String[] args) {
    LongestAbsoluteFilePath solution = new LongestAbsoluteFilePath();
    int result = solution.lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext");
    System.out.println(result);
  }

}
