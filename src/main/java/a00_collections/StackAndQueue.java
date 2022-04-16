package a00_collections;

import java.util.Deque;
import java.util.LinkedList;

public class StackAndQueue {
  /**
   * https://leetcode.com/problems/decode-string/
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
}
