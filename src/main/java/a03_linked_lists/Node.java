package a03_linked_lists;

public class Node {
  int val;
  Node next;

  public Node(int val) {
    this.val = val;
    this.next = this; // circular to itself
  }

  public Node insert(int newVal) {
    Node prev = this, curr = this.next;

    do {
      if (prev.val <= newVal && newVal <= curr.val) {
        return insert(prev, curr, newVal);
      } else if (prev.val > curr.val && (newVal >= prev.val || newVal <= curr.val)) {
        return insert(prev, curr, newVal);
      }
      prev = curr;
      curr = curr.next;
    } while (prev != this);

    return insert(prev, curr, newVal);
  }

  private Node insert(Node prev, Node curr, int newVal) {
    prev.next = new Node(newVal);
    prev.next.next = curr;
    return prev.next; // return the new node
  }
}