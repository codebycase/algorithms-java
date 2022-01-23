package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.TreeNode;

public class FindDuplicateSubtrees {
  List<TreeNode> res = new ArrayList<>();
  Map<String, Integer> freq = new HashMap<>();

  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
    dfs(root);
    return res;
  }

  public String dfs(TreeNode root) {
    if (root == null)
      return "#";
    StringBuilder sb = new StringBuilder();
    sb.append("L");
    sb.append(dfs(root.left));
    sb.append(root.val);
    sb.append("R");
    sb.append(dfs(root.right));
    String cur_config = sb.toString();
    freq.put(cur_config, freq.getOrDefault(cur_config, 0) + 1);
    if (freq.get(cur_config) == 2)
      res.add(root);
    return cur_config;
  }
}
