package a04_stacks_queues;

import java.util.BitSet;
import java.util.Deque;
import java.util.LinkedList;

public class ExpressionTreeWithEvaluateFunction {
  abstract class Node {
    public abstract int evaluate();
  };

  class TreeNode extends Node {
    public TreeNode left;
    public TreeNode right;
    public int value;
    public String operator;

    public TreeNode(int value) {
      this.value = value;
    }

    public TreeNode(String operator) {
      this.operator = operator;
    }

    public int evaluate() {
      if (operator == null) {
        return this.value;
      } else if ("+".equals(operator)) {
        return this.left.evaluate() + this.right.evaluate();
      } else if ("-".equals(operator)) {
        return this.left.evaluate() - this.right.evaluate();
      } else if ("*".equals(operator)) {
        return this.left.evaluate() * this.right.evaluate();
      } else if ("/".equals(operator)) {
        return this.left.evaluate() / this.right.evaluate();
      } else {
        throw new IllegalArgumentException();
      }
    }
  }

  class TreeBuilder {
    private BitSet bitset = new BitSet(256);

    public TreeBuilder() {
      bitset.set('+');
      bitset.set('-');
      bitset.set('*');
      bitset.set('/');
    }

    public Node buildTree(String[] postfix) {
      Deque<TreeNode> stack = new LinkedList<>();
      for (String p : postfix) {
        if (p.length() == 1 && bitset.get(p.charAt(0))) {
          TreeNode node = new TreeNode(p);
          node.right = stack.pop();
          node.left = stack.pop();
          stack.push(node);
        } else {
          stack.push(new TreeNode(Integer.valueOf(p)));
        }
      }
      return stack.peek();
    }

  };
}
