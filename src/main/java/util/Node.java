package util;

public class Node {
  public int val;
  public Node left;
  public Node right;
  public Node parent;
  public Node next;

  public Node() {
  }

  public Node(int val, Node next) {
    this.val = val;
    this.next = next;
  }

  public Node(int val) {
    this.val = val;
  }

  public Node(int val, Node left, Node right) {
    this(val);
    this.left = left;
    this.right = right;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public void setRight(Node right) {
    this.right = right;
  }
}
