package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.TreeNode;

/**
 * Vertical Order Traversal of a Binary Tree
 * 
 * https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
 * 
 * Solution: Use BFS/DFS with Partition Sorting
 * 
 * Time Complexity: O(Nlog(N/k)), k is the width/columns of the tree.
 */
public class VerticalOrderTraversal {
  int minCol = 0, maxCol = 0;
  Map<Integer, List<int[]>> colMap = new HashMap<>();

  private void dfsTraverse(TreeNode node, int row, int col) {
    if (node == null)
      return;

    minCol = Math.min(minCol, col);
    maxCol = Math.max(maxCol, col);

    // preorder/inorder/postorder all work here!
    dfsTraverse(node.left, row + 1, col - 1);
    colMap.computeIfAbsent(col, key -> new ArrayList<>()).add(new int[] { row, node.val });
    dfsTraverse(node.right, row + 1, col + 1);
  }

  public List<List<Integer>> verticalTraversal(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();

    dfsTraverse(root, 0, 0);

    for (int i = minCol; i <= maxCol; i++) {
      Collections.sort(colMap.get(i), (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

      List<Integer> values = new ArrayList<>();
      colMap.get(i).forEach(a -> values.add(a[1]));
      result.add(values);
    }

    return result;
  }
}
