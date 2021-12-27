package a10_recursion_greedy_invariant;

import java.util.LinkedList;
import java.util.List;

/**
 * Different Ways to Add Parentheses
 * 
 * Given a string expression of numbers and operators, return all possible results from computing
 * all the different possible ways to group numbers and operators. You may return the answer in any
 * order.
 * 
 * <pre>
 * Example 1:
 * 
 * Input: expression = "2-1-1"
 * Output: [0,2]
 * Explanation:
 * ((2-1)-1) = 0 
 * (2-(1-1)) = 2
 * 
 * Example 2:
 * 
 * Input: expression = "2*3-4*5"
 * Output: [-34,-14,-10,-10,10]
 * Explanation:
 * (2*(3-(4*5))) = -34 
 * ((2*3)-(4*5)) = -14 
 * ((2*(3-4))*5) = -10 
 * (2*((3-4)*5)) = -10 
 * (((2*3)-4)*5) = 10
 * </pre>
 *
 */
public class AddParentheses {

  // Standard divide and conquer
  public List<Integer> diffWaysToCompute(String input) {
    List<Integer> result = new LinkedList<Integer>();
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (c == '-' || c == '*' || c == '+') {
        String left = input.substring(0, i);
        String right = input.substring(i + 1);
        List<Integer> leftResult = diffWaysToCompute(left);
        List<Integer> rightResult = diffWaysToCompute(right);
        for (Integer l : leftResult) {
          for (Integer r : rightResult) {
            int x = 0;
            switch (c) {
            case '+':
              x = l + r;
              break;
            case '-':
              x = l - r;
              break;
            case '*':
              x = l * r;
              break;
            }
            result.add(x);
          }
        }
      }
    }
    // no operator
    if (result.size() == 0) {
      result.add(Integer.valueOf(input));
    }
    return result;
  }
}
