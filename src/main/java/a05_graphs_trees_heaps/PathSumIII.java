package a05_graphs_trees_heaps;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import util.TreeNode;

public class PathSumIII {
  /**
   * Given the root of a binary tree and an integer targetSum, return the number of paths where the
   * sum of the values along the path equals targetSum.
   * 
   * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e.,
   * traveling only from parent nodes to child nodes).
   * 
   * Solution:
   * 
   * Parse the tree using recursive preorder traversal, also use prefix sum technique along the way to
   * "Find a number of continuous subarrays/submatrices/tree paths that sum to target."
   * 
   * Complexity Analysis:
   * 
   * Time complexity: O(N), where N N is a number of nodes. During preorder traversal, each node is
   * visited once.
   * 
   * Space complexity: up to O(N) to keep the hashmap of prefix sums, where N is a number of nodes.
   *
   */
  public int pathSumIII(TreeNode root, int target) {
    AtomicInteger count = new AtomicInteger(0);
    Map<Integer, Integer> sumMap = new HashMap<>();
    // sumMap.put(0, 1); // Also able to add a default zero
    recurseTree(root, count, sumMap, 0, target);
    return count.get();
  }

  private void recurseTree(TreeNode node, AtomicInteger count, Map<Integer, Integer> sumMap, int prefixSum, int targetSum) {
    if (node == null) {
      return;
    }

    // Current prefix sum
    prefixSum += node.val;

    // Continous subarray starts from the beggining of the array
    // Can skip this section if used sumMap.put(0, 1)
    if (prefixSum == targetSum) {
      count.addAndGet(1);
    }

    // Number of times the current - target has occured already.
    count.addAndGet(sumMap.getOrDefault(prefixSum - targetSum, 0));

    sumMap.put(prefixSum, sumMap.getOrDefault(prefixSum, 0) + 1);

    recurseTree(node.left, count, sumMap, prefixSum, targetSum);
    recurseTree(node.right, count, sumMap, prefixSum, targetSum);

    sumMap.put(prefixSum, sumMap.get(prefixSum) - 1);
  }

  /**
   * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where
   * each path's sum equals targetSum.
   * 
   * Time Complexity: O(N^2), In the worst case, we could have a complete binary tree and if that is
   * the case, then there would be N/2 leafs. For every leaf, we perform a potential O(N) operation of
   * copying over the pathNodes nodes to a new list to be added to the final pathsList. Hence, the
   * complexity in the worst case could be O(N^2).
   * 
   * Space Complexity: O(N) or O(N^2) if count in the space occupied by the output.
   * 
   */
  public List<List<Integer>> pathSumII(TreeNode root, int sum) {
    List<List<Integer>> results = new ArrayList<>();
    recurseTree(results, new ArrayList<>(), root, sum);
    return results;
  }

  private void recurseTree(List<List<Integer>> results, List<Integer> path, TreeNode node, int remain) {
    if (node == null) {
      return;
    }

    path.add(node.val);

    if (node.val == remain && node.left == null && node.right == null) {
      results.add(new ArrayList<>(path));
    } else {
      recurseTree(results, path, node.left, remain - node.val);
      recurseTree(results, path, node.right, remain - node.val);
    }

    path.remove(path.size() - 1);
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.left.left = new TreeNode(3);
    root.left.left.left = new TreeNode(3);
    root.left.left.right = new TreeNode(-2);

    root.left.right = new TreeNode(2);
    root.left.right.right = new TreeNode(1);

    root.right = new TreeNode(-3);
    root.right.right = new TreeNode(11);

    PathSumIII solution = new PathSumIII();
    Assert.assertEquals(3, solution.pathSumIII(root, 8));
  }
}
