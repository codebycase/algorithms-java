package a99_miscellaneous;

import static org.junit.Assert.assertEquals;

import util.ListNode;

public class InterviewQuestions {
  /**
   * Given a contiguous sequence of numbers in which each number repeats thrice, there is exactly one
   * missing number. <br>
   * Find the missing number. <br>
   * eg: 11122333 : Missing number 2 <br>
   * 11122233344455666 Missing number 5
   */
  public int tripleBinarySearch(int[] nums) {
    int i = 0, j = nums.length - 1;
    while (i < j - 1) { // skip loop if less than 3 nums
      int mid = i + (j - i) / 2;
      int midI = mid, midJ = mid;
      while (midI >= 0 && nums[midI] == nums[mid])
        midI--;
      while (midJ < nums.length && nums[midJ] == nums[mid])
        midJ++;
      if (midJ - midI < 4) // 2 nums between
        return nums[mid];
      if (midI > 0 && (midI + 1) % 3 != 0)
        j = midI;
      else
        i = midJ;
    }
    return nums[i];
  }

  /**
   * The power of the string is the maximum length of a non-empty substring that contains only one
   * unique character.
   * 
   * Given a string s, return the power of s.
   * 
   * 
   * <pre>
  Example 1:
  
  Input: s = "leetcode"
  Output: 2
  Explanation: The substring "ee" is of length 2 with the character 'e' only.
  Example 2:
  
  Input: s = "abbcccddddeeeeedcba"
  Output: 5
  Explanation: The substring "eeeee" is of length 5 with the character 'e' only.
   * </pre>
   */
  public int maxPower(String s) {
    int count = 0;
    int maxCount = 0;
    char previous = ' ';
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == previous) {
        // if same as previous one, increase the count
        count++;
      } else {
        // else, reset the count
        count = 1;
        previous = c;
      }
      maxCount = Math.max(maxCount, count);
    }
    return maxCount;
  }

  /**
   * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
   * 
   * <pre>
  Symbol       Value
  I             1
  V             5
  X             10
  L             50
  C             100
  D             500
  M             1000
   * </pre>
   * 
   * For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as
   * XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
   * 
   * Roman numerals are usually written largest to smallest from left to right. However, the numeral
   * for four is not IIII. Instead, the number four is written as IV. Because the one is before the
   * five we subtract it making four. The same principle applies to the number nine, which is written
   * as IX. There are six instances where subtraction is used:
   * 
   * I can be placed before V (5) and X (10) to make 4 and 9. X can be placed before L (50) and C
   * (100) to make 40 and 90. C can be placed before D (500) and M (1000) to make 400 and 900. Given
   * an integer, convert it to a roman numeral.
   * 
   * <br>
   * 
   * Example 3:
   * 
   * Input: num = 1994 Output: "MCMXCIV" Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
   * 
   * https://leetcode.com/problems/integer-to-roman/
   * 
   * Solution:
   * 
   * Getting each digit of the number can be done using the modulus and division operators.
   * 
   * <pre>
  thousands_digit = integer / 1000
  hundreds_digit = (integer % 1000) / 100
  tens_digit = (integer % 100) / 10
  ones_digit = integer % 10
   * </pre>
   * 
   */
  public String intToRoman(int num) {
    int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      while (num >= values[i]) {
        num -= values[i];
        sb.append(symbols[i]);
      }
    }
    return sb.toString();
  }

  /**
   * Given a linked list, swap every two adjacent nodes and return its head. You must solve the
   * problem without modifying the values in the list's nodes (i.e., only nodes themselves may be
   * changed.)
   * 
   * https://leetcode.com/problems/swap-nodes-in-pairs/
   * 
   */
  public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode();

    dummy.next = head;
    ListNode prevNode = dummy;

    while (head != null && head.next != null) {
      ListNode firstNode = head;
      ListNode secondNode = head.next;

      prevNode.next = secondNode;
      firstNode.next = secondNode.next;
      secondNode.next = firstNode;

      prevNode = firstNode;
      head = firstNode.next;
    }

    return dummy.next;
  }

  /**
   * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according
   * to the following rules:
   * 
   * Each row must contain the digits 1-9 without repetition. Each column must contain the digits 1-9
   * without repetition. Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9
   * without repetition.
   * 
   */
  public boolean isValidSudoku(char[][] board) {
    int N = 9;

    // Use a binary number to record previous occurrence
    int[] rows = new int[N];
    int[] cols = new int[N];
    int[] boxes = new int[N];

    for (int r = 0; r < N; r++) {
      for (int c = 0; c < N; c++) {
        // Check if the position is filled with number
        if (board[r][c] == '.') {
          continue;
        }
        int val = board[r][c] - '0';
        int pos = 1 << (val - 1);

        // Check the row
        if ((rows[r] & pos) > 0) {
          return false;
        }
        rows[r] |= pos;

        // Check the column
        if ((cols[c] & pos) > 0) {
          return false;
        }
        cols[c] |= pos;

        // Check the box
        int idx = (r / 3) * 3 + c / 3;
        if ((boxes[idx] & pos) > 0) {
          return false;
        }
        boxes[idx] |= pos;
      }
    }

    return true;
  }

  /**
   * Example 2:
   * 
   * <pre>
  Input: customers = [[5,2],[5,4],[10,3],[20,1]]
  Output: 3.25000
  Explanation:
  1) The first customer arrives at time 5, the chef takes his order and starts preparing it immediately at time 5, and finishes at time 7, so the waiting time of the first customer is 7 - 5 = 2.
  2) The second customer arrives at time 5, the chef takes his order and starts preparing it at time 7, and finishes at time 11, so the waiting time of the second customer is 11 - 5 = 6.
  3) The third customer arrives at time 10, the chef takes his order and starts preparing it at time 11, and finishes at time 14, so the waiting time of the third customer is 14 - 10 = 4.
  4) The fourth customer arrives at time 20, the chef takes his order and starts preparing it immediately at time 20, and finishes at time 21, so the waiting time of the fourth customer is 21 - 20 = 1.
  So the average waiting time = (2 + 6 + 4 + 1) / 4 = 3.25.
   * </pre>
   */
  public double averageWaitingTime(int[][] customers) {
    int time = 0;
    long sum = 0;
    for (int[] customer : customers) {
      time = Math.max(time, customer[0]);
      time += customer[1];
      sum += time - customer[0]; // add waiting time
    }
    return (double) sum / customers.length;
  }

  /**
   * Write an SQL query to report the second highest salary from the Employee table. If there is no
   * second highest salary, the query should report null.
   */
  public void secondHighestSalary() {
    /*
    SELECT (SELECT DISTINCT salary 
      FROM   employee 
      ORDER  BY salary DESC 
      LIMIT  1 offset 1) AS SecondHighestSalary; 
    */
  }

  public static void main(String[] args) {
    InterviewQuestions solution = new InterviewQuestions();
    int[] nums = new int[] { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6 };
    assertEquals(5, solution.tripleBinarySearch(nums));
    assertEquals("MCMXCIV", solution.intToRoman(1994));
  }
}
