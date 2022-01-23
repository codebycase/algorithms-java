package a04_stacks_queues;

import java.util.Stack;

public class LongestAbsoluteFilePath {
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
