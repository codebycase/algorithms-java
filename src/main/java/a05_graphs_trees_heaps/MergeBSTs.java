package a05_graphs_trees_heaps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.TreeNode;

public class MergeBSTs {
  public TreeNode canMerge(List<TreeNode> trees) {
    // Collect the leaves
    Set<Integer> leaves = new HashSet<>();
    Map<Integer, TreeNode> map = new HashMap<>();
    for (TreeNode node : trees) {
      map.put(node.val, node);
      if (node.left != null) {
        leaves.add(node.left.val);
      }
      if (node.right != null) {
        leaves.add(node.right.val);
      }
    }

    // Decide the root of the resulting tree
    TreeNode result = null;
    for (TreeNode tree : trees) {
      if (!leaves.contains(tree.val)) {
        result = tree;
        break;
      }
    }
    if (result == null) {
      return null;
    }

    return traverse(result, map, Integer.MIN_VALUE, Integer.MAX_VALUE) && map.size() == 1 ? result : null;
  }

  private boolean traverse(TreeNode root, Map<Integer, TreeNode> map, int min, int max) {
    if (root == null)
      return true;
    if (root.val <= min || root.val >= max)
      return false;

    if (root.left == null && root.right == null) {
      if (map.containsKey(root.val) && root != map.get(root.val)) {
        TreeNode next = map.get(root.val);
        root.left = next.left;
        root.right = next.right;
        map.remove(root.val);
      }
    }
    return traverse(root.left, map, min, root.val) && traverse(root.right, map, root.val, max);
  }

  public TreeNode canMerge2(List<TreeNode> trees) {
    final int size = trees.size();
    final Map<Integer, TreeNode> roots = new HashMap<>(size);
    for (final TreeNode node : trees) {
      roots.put(node.val, node);
    }
    for (final TreeNode node : trees) {
      if (roots.containsKey(node.val)) {
        final TreeNode root = buildTree(roots, node);
        roots.put(root.val, root);
      }
    }
    if (roots.size() != 1)
      return null;
    final TreeNode root = roots.values().iterator().next();
    return isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE) ? root : null;
  }

  private TreeNode buildTree(Map<Integer, TreeNode> roots, TreeNode node) {
    final TreeNode next = roots.remove(node.val);
    if (next != null) {
      if (next.left != null)
        node.left = buildTree(roots, next.left);
      if (next.right != null)
        node.right = buildTree(roots, next.right);
    }
    return node;
  }

  private boolean isValid(TreeNode node, int min, int max) {
    if (node == null)
      return true;
    final int value = node.val;
    if (value <= min || max <= value)
      return false;
    return isValid(node.left, min, value) && isValid(node.right, value, max);
  }
}
