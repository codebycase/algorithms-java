package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.List;

import util.TreeNode;

public class AllNotesDistanceKInBinaryTree {
  List<Integer> ans;
  int k;

  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    ans = new ArrayList<>();
    this.k = k;
    helper(root, target, Integer.MAX_VALUE);
    return ans;
  }

  int helper(TreeNode node, TreeNode target, int dis) { // return distance upwards
    if (node == null)
      return Integer.MAX_VALUE;

    if (dis == k) {
      ans.add(node.val);
      return Integer.MAX_VALUE;
    }

    if (node == target) {
      dis = 0;
      if (k == 0) {
        ans.add(node.val);
        return Integer.MAX_VALUE;
      }
    }
    if (dis == Integer.MAX_VALUE) {
      dis--;
    }

    int l = helper(node.left, target, dis + 1);
    int r = helper(node.right, target, dis + 1);

    if (l != Integer.MAX_VALUE) {
      if (l == k)
        ans.add(node.val);
      else {
        helper(node.right, target, l + 1);
        return l + 1;
      }
    }
    if (r != Integer.MAX_VALUE) {
      if (r == k)
        ans.add(node.val);
      else {
        helper(node.left, target, r + 1);
        return r + 1;
      }
    }
    return node == target ? 1 : Integer.MAX_VALUE;
  }
}
