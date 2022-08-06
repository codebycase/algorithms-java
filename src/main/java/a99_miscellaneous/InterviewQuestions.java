package a99_miscellaneous;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ListNode;
import util.TreeNode;

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
   * Single Element in a Sorted Array
   * 
   * You are given a sorted array consisting of only integers where every element appears exactly
   * twice, except for one element which appears exactly once.
   * 
   * Return the single element that appears only once.
   * 
   * Your solution must run in O(log n) time and O(1) space.
   * 
   * <pre>
  Example 1:
  Input: nums = [1,1,2,3,3,4,4,8,8]
  Output: 2
  
  Example 2:
  Input: nums = [3,3,7,7,10,11,11]
  Output: 10
   * </pre>
   * 
   * Algorithm
   * 
   * We need to set up the binary search variables and loop so that we are only considering even
   * indexes. The last index of an odd-lengthed array is always even, so we can set lo and hi to be
   * the start and end of the array.
   * 
   * We need to make sure our mid index is even. We can do this by dividing lo and hi in the usual
   * way, but then decrementing it by 1 if it is odd. This also ensures that if we have an even number
   * of even indexes to search, that we are getting the lower middle (incrementing by 1 here would not
   * work, it'd lead to an infinite loop as the search space would not be reduced in some cases).
   * 
   * Then we check whether or not the mid index is the same as the one after it.
   * 
   * If it is, then we know that mid is not the single element, and that the single element must be at
   * an even index after mid. Therefore, we set lo to be mid + 2. It is +2 rather than the usual +1
   * because we want it to point at an even index. If it is not, then we know that the single element
   * is either at mid, or at some index before mid. Therefore, we set hi to be mid. Once lo == hi, the
   * search space is down to 1 element, and this must be the single element, so we return it.
   */

  public int singleNonDuplicate(int[] nums) {
    int lo = 0;
    int hi = nums.length - 1;
    while (lo < hi) {
      int mid = lo + (hi - lo) / 2;
      if (mid % 2 == 1)
        mid--;
      if (nums[mid] == nums[mid + 1]) {
        lo = mid + 2;
      } else {
        hi = mid;
      }
    }
    return nums[lo];
  }

  /**
   * The power of the string is the maximum length of a non-empty substring that contains only one
   * unique character.
   * 
   * Given a string s, return the power of s.
   * 
   * 
   * <pre>
   *   
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
   * Minimum Operations to Reduce X to Zero
   * 
   * You are given an integer array nums and an integer x. In one operation, you can either remove the
   * leftmost or the rightmost element from the array nums and subtract its value from x. Note that
   * this modifies the array for future operations.
   * 
   * Return the minimum number of operations to reduce x to exactly 0 if it is possible, otherwise,
   * return -1.
   * 
   * 
   * <pre>
  Example 1:
  
  Input: nums = [1,1,4,2,3], x = 5
  Output: 2
  Explanation: The optimal solution is to remove the last two elements to reduce x to zero.
  Example 2:
  
  Input: nums = [5,6,7,8,9], x = 4
  Output: -1
  Example 3:
  
  Input: nums = [3,2,20,1,1,3], x = 10
  Output: 5
  Explanation: The optimal solution is to remove the last three elements and the first two elements (5 operations in total) to reduce x to zero.
   * </pre>
   * 
   * https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
   * 
   */
  public int minOperations(int[] nums, int x) {
    int current = 0;
    for (int num : nums) {
      current += num;
    }

    int min = Integer.MAX_VALUE;
    int left = 0;

    for (int right = 0; right < nums.length; right++) {
      // sum([0,..,left) + (right,...,n-1]) = x
      current -= nums[right];
      // if smaller, move `left` to right
      while (current < x && left <= right) {
        current += nums[left++];
      }
      // check if equals
      if (current == x) {
        min = Math.min(min, (nums.length - 1 - right) + left);
      }
    }
    return min != Integer.MAX_VALUE ? min : -1;
  }

  /**
   * You are given an array people where people[i] is the weight of the ith person, and an infinite
   * number of boats where each boat can carry a maximum weight of limit. Each boat carries at most
   * two people at the same time, provided the sum of the weight of those people is at most limit.
   * 
   * Return the minimum number of boats to carry every given person.
   * 
   * 
   * <pre>
  Example 1:
  
  Input: people = [1,2], limit = 3
  Output: 1
  Explanation: 1 boat (1, 2)
  Example 2:
  
  Input: people = [3,2,2,1], limit = 3
  Output: 3
  Explanation: 3 boats (1, 2), (2) and (3)
  Example 3:
  
  Input: people = [3,5,3,4], limit = 5
  Output: 4
  Explanation: 4 boats (3), (3), (4), (5)
   * </pre>
   * 
   * https://leetcode.com/problems/boats-to-save-people/
   */
  public int numRescueBoats(int[] people, int limit) {
    int n = people.length;

    // Arrays.sort(people); nlog(n) method

    // Count sorting people
    int[] freq = new int[limit + 1];
    for (int p : people) {
      freq[p]++;
    }
    int i = 0;
    for (int v = 1; v < freq.length; v++) {
      while (freq[v]-- > 0) {
        people[i++] = v;
      }
    }

    int left = 0, right = n - 1, boats = 0;
    // two pointers and will check in limit
    while (left <= right) {
      if (people[left] + people[right] <= limit) {
        left++;
        right--;
      } else {
        right--;
      }
      boats++;
    }

    return boats;
  }

  /**
   * Find All Duplicates in an Array
   * 
   * Given an integer array nums of length n where all the integers of nums are in the range [1, n]
   * and each integer appears once or twice, return an array of all the integers that appears twice.
   * 
   * You must write an algorithm that runs in O(n) time and uses only constant extra space.
   * 
   * 
   * <pre>
  Example 1:
  
  Input: nums = [4,3,2,7,8,2,3,1]
  Output: [2,3]
  
  Example 2:
  
  Input: nums = [1,1,2]
  Output: [1]
  
  Example 3:
  
  Input: nums = [1]
  Output: []
   * </pre>
   */

  public List<Integer> findDuplicates(int[] nums) {
    List<Integer> ans = new ArrayList<>();

    for (int num : nums) {
      int n = Math.abs(num);
      if (nums[n - 1] < 0) {
        ans.add(n);
      } else {
        nums[n - 1] *= -1;
      }
    }

    return ans;
  }

  /**
   * Given an array of n integers nums, a 132 pattern is a subsequence of three integers nums[i],
   * nums[j] and nums[k] such that i < j < k and nums[i] < nums[k] < nums[j].
   * 
   * Return true if there is a 132 pattern in nums, otherwise, return false.
   * 
   * 
   * <pre>
  Example 1:
  
  Input: nums = [1,2,3,4]
  Output: false
  Explanation: There is no 132 pattern in the sequence.
  Example 2:
  
  Input: nums = [3,1,4,2]
  Output: true
  Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
  Example 3:
  
  Input: nums = [-1,3,2,0]
  Output: true
  Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
   * </pre>
   */
  public boolean find132pattern(int[] nums) {
    if (nums.length < 3)
      return false;
    int[] mins = new int[nums.length];
    mins[0] = nums[0];
    for (int i = 1; i < nums.length; i++)
      mins[i] = Math.min(mins[i - 1], nums[i]);
    for (int j = nums.length - 1, k = nums.length; j >= 0; j--) {
      if (nums[j] > mins[j]) {
        while (k < nums.length && nums[k] <= mins[j]) {
          k++;
        }
        if (k < nums.length && nums[k] < nums[j]) {
          return true;
        }
        // We can remove those elements(update the index k) which aren't greater than nums[i](min[j])
        nums[--k] = nums[j];
      }
    }
    return false;
  }

  /**
   * Most Frequent Subtree Sum
   * 
   * Given the root of a binary tree, return the most frequent subtree sum. If there is a tie, return
   * all the values with the highest frequency in any order.
   * 
   * The subtree sum of a node is defined as the sum of all the node values formed by the subtree
   * rooted at that node (including the node itself).
   * 
   * <pre>
  Example 1:
  Input: root = [5,2,-3]
  Output: [2,-3,4]
  
  Example 2:
  Input: root = [5,2,-5]
  Output: [2]
   * </pre>
   */
  int maxFreq = 0;

  public int[] findFrequentTreeSum(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    Map<Integer, Integer> map = new HashMap<>();

    solveMaxFreq(root, map);

    map.forEach((k, v) ->
      {
        if (v == maxFreq) {
          list.add(k);
        }
      });

    int[] ans = new int[list.size()];
    for (int i = 0; i < ans.length; i++) {
      ans[i] = list.get(i);
    }

    return ans;

  }

  private int solveMaxFreq(TreeNode root, Map<Integer, Integer> map) {
    if (root == null) {
      return 0;
    }
    int left = solveMaxFreq(root.left, map);
    int right = solveMaxFreq(root.right, map);
    int sum = root.val + left + right;
    map.put(sum, map.getOrDefault(sum, 0) + 1);
    maxFreq = Math.max(maxFreq, map.get(sum));
    return sum;
  }

  /**
   * Video Stitching
   * 
   * You are given a series of video clips from a sporting event that lasted time seconds. These video
   * clips can be overlapping with each other and have varying lengths.
   * 
   * Each video clip is described by an array clips where clips[i] = [starti, endi] indicates that the
   * ith clip started at starti and ended at endi.
   * 
   * We can cut these clips into segments freely.
   * 
   * For example, a clip [0, 7] can be cut into segments [0, 1] + [1, 3] + [3, 7]. Return the minimum
   * number of clips needed so that we can cut the clips into segments that cover the entire sporting
   * event [0, time]. If the task is impossible, return -1.
   * 
   * <pre>
   *  
  
  Example 1:
  
  Input: clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], time = 10
  Output: 3
  Explanation: We take the clips [0,2], [8,10], [1,9]; a total of 3 clips.
  Then, we can reconstruct the sporting event as follows:
  We cut [1,9] into segments [1,2] + [2,8] + [8,9].
  Now we have segments [0,2] + [2,8] + [8,10] which cover the sporting event [0, 10].
  Example 2:
  
  Input: clips = [[0,1],[1,2]], time = 5
  Output: -1
  Explanation: We cannot cover [0,5] with only [0,1] and [1,2].
  Example 3:
  
  Input: clips = [[0,1],[6,8],[0,2],[5,6],[0,4],[0,3],[6,7],[1,3],[4,7],[1,4],[2,5],[2,6],[3,4],[4,5],[5,7],[6,9]], time = 9
  Output: 3
  Explanation: We can take clips [0,4], [4,7], and [6,9].
   * </pre>
   */
  public int videoStitching(int[][] clips, int time) {
    // Sort the array by the start and then sort by the end
    Arrays.sort(clips, (a, b) ->
      {
        int result = Integer.compare(a[0], b[0]);
        if (result == 0) {
          return Integer.compare(a[1], b[1]);
        }
        return result;
      });

    // The algorithm:
    // We iterate through the sorted array of clips
    // and try to find the best candidates for stitching.
    // Every time we find a candidate we increase counter.
    int[] current = clips[0];
    int counter = 1;

    // Edge case when there is no clip starting with 0
    if (current[0] != 0) {
      return -1;
    }

    for (int i = 1; i < clips.length; i++) {
      // Edge case when the match length is less than
      // total duration of clips.
      if (current[1] >= time) {
        return counter;
      }

      // If there are two clips with the same start time
      // we use the next one because it has end time further away
      // (because the array ordered by the end time in ascending order)
      if (clips[i][0] == current[0]) {
        current = clips[i];
        continue;
      }

      // If there is a gap between clips then we can't form the whole match
      // and return -1
      if (clips[i][0] > current[1])
        return -1;

      // In this case the next clip in the array
      // is completely covered by current clip
      if (current[1] >= clips[i][1])
        continue;

      // Here we try to find the best candidate for current clip.
      // It will be the one whose end is further away and start is within
      // current clip so that we can stitch them together
      int[] best = clips[i];
      int j = i + 1;
      while (j < clips.length && clips[j][0] <= current[1]) {
        if (clips[j][1] > best[1]) {
          best = clips[j];
          i = j;
        }
        j++;
      }

      // Every time we find the best stitching candidate we increase the counter
      // and move to the next iteration
      counter++;
      current = best;
    }

    // Edge case when there are not enough clips to stitch the whole match
    if (current[1] < time)
      return -1;

    return counter;
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
