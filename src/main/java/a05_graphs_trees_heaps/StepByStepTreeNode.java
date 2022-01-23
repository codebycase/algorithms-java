package a05_graphs_trees_heaps;

import util.TreeNode;

public class StepByStepTreeNode {
  private boolean find(TreeNode node, int val, StringBuilder path) {
    if (node == null)
      return false;
    if (node.val == val)
      return true;
    if (find(node.left, val, path))
      path.append("L");
    else if (find(node.right, val, path))
      path.append("R");
    return path.length() > 0;
  }

  public String getDirections(TreeNode root, int startValue, int destValue) {
    StringBuilder startPath = new StringBuilder(), destPath = new StringBuilder();
    if (find(root, startValue, startPath) && find(root, destValue, destPath)) {
      startPath.reverse();
      destPath.reverse();
      int i = 0, len = Math.min(startPath.length(), destPath.length());
      while (i < len && startPath.charAt(i) == destPath.charAt(i)) {
        i++;
      }
      return "U".repeat(startPath.length() - i) + destPath.substring(i);
      // return startPath.substring(i).replaceAll(".", "U") + destPath.substring(i);
    }
    return "";
  }
}
