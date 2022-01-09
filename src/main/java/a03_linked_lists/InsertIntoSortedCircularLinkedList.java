package a03_linked_lists;

import java.util.Random;

public class InsertIntoSortedCircularLinkedList {
  public Node insert(Node head, int newVal) {
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

  public void printList(Node node) {
    Node head = node;
    do {
      System.out.print(node.val + "\t");
      node = node.next;
    } while (head != node);
  }

  public static void main(String[] args) {
    InsertIntoSortedCircularLinkedList solution = new InsertIntoSortedCircularLinkedList();
    Random random = new Random();
    int bound = 10;

    int val = random.nextInt(bound);
    Node list1 = new Node(val), list2 = new Node(val);
    Node node = list2;
    for (int i = 0; i < 5; i++) {
      val = random.nextInt(bound);
      solution.insert(list1, val);
      node = node.insert(val);
    }

    solution.printList(list1);
    System.out.println();
    solution.printList(list2);
  }
}
