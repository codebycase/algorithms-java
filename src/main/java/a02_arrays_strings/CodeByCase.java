package a02_arrays_strings;

import org.junit.Assert;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import a03_linked_lists.Node;
import util.TreeNode;

public class CodeByCase {
  public int[] longestContinuousIncreasingSubarray(int[] nums) {
    int start = 0, end = 0;
    int max = 0, anchor = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i - 1] >= nums[i])
        anchor = i;
      if (max < i - anchor + 1) {
        max = i - anchor + 1;
        start = anchor;
        end = i;
      }
    }
    return new int[] { start, end };
  }

  // dp[i] represents the length of the longest increasing subsequence that ends with the element at
  // index i.
  public int longestIncreasingSubsequence(int[] nums) {
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);

    for (int i = 1; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }

    int longest = 0;
    for (int c : dp) {
      longest = Math.max(longest, c);
    }

    return longest;
  }

  // Use map to count remainder's frequency
  public int subarraySumsDivisibleByK(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k == 0)
      return 0;

    int ans = 0, sum = 0, remainder;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // 0 as remainder to be 1

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      remainder = sum % k;
      if (remainder < 0)
        remainder += k; // convert to positive
      if (map.containsKey(remainder)) {
        ans += map.get(remainder);
      }
      map.put(remainder, map.getOrDefault(remainder, 0) + 1);
    }
    return ans;
  }

  // Use map to count different's frequency
  public int subarraySumsEqualsK(int[] nums, int k) {
    int count = 0, sum = 0;
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      if (map.containsKey(sum - k))
        count += map.get(sum - k);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
  }

  // Use monotonic stack to track indices
  public int sumOfSubarrayMinimums(int[] arr) {
    long sum = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1); // leverage a dummy index
    for (int i = 0; i <= arr.length; i++) {
      while (stack.peek() != -1 && (i == arr.length || arr[stack.peek()] > arr[i])) {
        int mid = stack.pop(); // middle min pilliar
        int left = mid - stack.peek();
        int right = i - mid;
        sum += (long) arr[mid] * left * right;
      }
      stack.push(i);
    }
    return (int) (sum % (1e9 + 7));
  }

  // Find interval ranges and print out
  public String findIdleMachines(int[] machines) {
    StringBuilder sb = new StringBuilder();

    int start = 1; // starts with 1
    for (int i = 0; i < machines.length; i++) {
      int end = machines[i] - 1;
      printIdleRange(start, end, sb);
      start = machines[i] + 1;
    }

    printIdleRange(start, 99, sb);

    // Remove trailing comma+space
    if (sb.length() > 2) {
      sb.setLength(sb.length() - 2);
    }

    return sb.toString();
  }

  private void printIdleRange(int start, int end, StringBuilder sb) {
    int idles = end - start + 1;
    if (idles > 3) {
      sb.append(start).append("-").append(end).append(", ");
    } else if (idles > 0) {
      for (int j = start; j <= end; j++) {
        sb.append(j).append(", ");
      }
    }
  }

  // Use do...while statement to track circular
  public Node insertIntoSortedCircularLinkedList(Node head, int newVal) {
    if (head == null) {
      Node newNode = new Node(newVal);
      newNode.next = newNode;
      return newNode;
    }

    Node prev = head;
    Node curr = head.next;
    do {
      if (prev.val <= newVal && newVal <= curr.val) {
        insert(prev, curr, newVal);
        return head;
      } else if (prev.val > curr.val && (newVal >= prev.val || newVal <= curr.val)) {
        insert(prev, curr, newVal);
        return head;
      }
      prev = curr;
      curr = curr.next;
    } while (prev != head);

    insert(prev, curr, newVal);
    return head;
  }

  private Node insert(Node prev, Node curr, int insertVal) {
    prev.next = new Node(insertVal);
    prev.next.next = curr;
    return prev.next;
  }

  // How to determine a squares based on other 3 corners
  public int countSquares(int[][] matrix) {
    int result = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] > 0 && i > 0 && j > 0) {
          matrix[i][j] = Math.min(matrix[i - 1][j - 1], Math.min(matrix[i - 1][j], matrix[i][j - 1])) + 1;
        }
        result += matrix[i][j];
      }
    }
    return result;
  }

  // Use hash finger print to encode a word
  // https://leetcode.com/problems/strings-differ-by-one-character/
  public boolean differByOne(String[] dict) {
    int n = dict.length;
    if (n <= 1) {
      return false;
    }
    long[] nums = new long[n];
    for (int i = 0; i < n; i++) {
      long temp = 0;
      for (char ch : dict[i].toCharArray()) {
        temp = temp * 26 + ch - 'a';
      }
      nums[i] = temp;
    }
    int m = dict[0].length();
    long base = 1;
    for (int j = m - 1; j >= 0; j--) {
      Set<Long> set = new HashSet<>();
      for (int i = 0; i < n; i++) {
        if (!set.add(nums[i] - (long) (dict[i].charAt(j) - 'a') * base)) {
          return true;
        }
      }
      base *= 26;
    }
    return false;
  }

  /**
   * Use XOR to compare each row with top row
   * https://leetcode.com/problems/remove-all-ones-with-row-and-column-flips/
   */
  public boolean removeOnes(int[][] grid) {
    if (grid.length == 1 || grid[0].length == 1) {
      return true;
    }

    for (int r = 1; r < grid.length; r++) {
      int xor = grid[0][0] ^ grid[r][0];
      for (int c = 1; c < grid[0].length; c++) {
        if ((grid[0][c] ^ grid[r][c]) != xor) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Binary tree to find track path with DFS
   * https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
   */
  public String getDirections(TreeNode root, int startValue, int destValue) {
    StringBuilder startPath = new StringBuilder();
    StringBuilder destPath = new StringBuilder();
    if (findPath(root, startValue, startPath) && findPath(root, destValue, destPath)) {
      startPath.reverse();
      destPath.reverse();
      int i = 0, len = Math.min(startPath.length(), destPath.length());
      while (i < len && startPath.charAt(i) == destPath.charAt(i)) {
        i++;
      }
      return "U".repeat(startPath.length() - i) + destPath.substring(i);
    }
    return "";
  }

  private boolean findPath(TreeNode node, int value, StringBuilder sb) {
    if (node == null) {
      return false;
    }
    if (node.val == value) {
      return true;
    }

    if (findPath(node.left, value, sb)) {
      sb.append("L");
    } else if (findPath(node.right, value, sb)) {
      sb.append("R");
    }

    return sb.length() > 0;
  }

  // Deep-First Search (Post-Order Traversal)
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // if (root == null || root == p || root == q) // two given nodes in the tree
    if (root == null || root.val == p.val || root.val == q.val)
      return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null)
      return root;
    else
      return left == null ? right : left;
  }

  // Simply move upwards with null checking and switch over
  public TreeNode lowestCommonAncestor(TreeNode p, TreeNode q) {
    TreeNode a = p, b = q;
    while (a != b) {
      a = a == null ? q : a.parent;
      b = b == null ? p : b.parent;
    }
    return a;
  }

  // https://leetcode.com/problems/minimum-number-of-refueling-stops/
  public int minRefuelStops(int target, int startFuel, int[][] stations) {
    Queue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
    int i = 0, n = stations.length, stops = 0, maxDistance = startFuel;
    while (maxDistance < target) {
      while (i < n && stations[i][0] <= maxDistance) {
        queue.offer(stations[i++][1]);
      }
      if (queue.isEmpty())
        return -1;
      maxDistance += queue.poll();
      stops++;
    }
    return stops;
  }

  /** Use stack to track longest file path */
  public int longestAbsoluteFilePath(String input) {
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

  // Precalculate the extra spaces with map
  public int sentenceScreenFitting(String[] sentence, int rows, int cols) {
    StringBuilder s = new StringBuilder();
    for (String word : sentence) {
      s.append(word).append(" ");
    }
    int len = s.length(), count = 0;
    int[] map = new int[len];
    for (int i = 1; i < len; ++i) {
      map[i] = s.charAt(i) == ' ' ? 1 : map[i - 1] - 1;
    }
    for (int i = 0; i < rows; ++i) {
      count += cols;
      count += map[count % len];
    }
    return count / len;
  }

  /**
   * How to fill in the gaps and extra spaces
   */
  public List<String> fullTextJustification(String[] words, int maxWidth) {
    List<String> result = new ArrayList<>();
    int start = 0, end = 0;
    while (start < words.length) {
      int count = words[start].length();
      // end is excluded!
      end = start + 1;
      while (end < words.length) {
        // count in the spaces
        if (count + 1 + words[end].length() > maxWidth)
          break;
        count += 1 + words[end].length();
        end++;
      }

      StringBuilder builder = new StringBuilder();
      int gaps = end - 1 - start;
      // left or middle justified
      if (end == words.length || gaps == 0) {
        for (int i = start; i < end; i++) {
          builder.append(words[i]);
          if (i < end - 1)
            builder.append(" ");
        }
        for (int i = builder.length(); i < maxWidth; i++) {
          builder.append(" ");
        }
      } else {
        int spaces = (maxWidth - count) / gaps;
        int rest = (maxWidth - count) % gaps;
        for (int i = start; i < end; i++) {
          builder.append(words[i]);
          if (i < end - 1) {
            builder.append(" ");
            for (int j = 0; j < spaces + (i - start < rest ? 1 : 0); j++)
              builder.append(" ");
          }
        }
      }
      result.add(builder.toString());
      start = end;
    }

    return result;
  }

  public static void main(String[] args) {
    CodeByCase solution = new CodeByCase();
    int[] machines = { 1, 5, 7, 8, 15, 66, 67, 90 };
    Assert.assertEquals("2, 3, 4, 6, 9-14, 16-65, 68-89, 91-99", solution.findIdleMachines(machines));

    String pathText = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext";
    Assert.assertEquals(32, solution.longestAbsoluteFilePath(pathText));
  }

}
