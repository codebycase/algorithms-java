package a00_collections;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import util.ListNode;

public class ListAndLinkedList {
  public class Node {
    public int val;
    public Node next;

    public Node(int val) {
      this.val = val;
      this.next = this; // circular to itself
    }

    // Solution inside the node, note the do...while
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

  // Solution outside the node, note the do...while
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

  /**
   * The loop runs n * k times. In every iteration of loop, we call heapify which takes O(Log(k))
   * time. Therefore, the time complexity is O(nkLog(k)).
   */
  public ListNode mergeKSortedLists(ListNode[] lists) {
    if (lists == null || lists.length == 0)
      return null;

    Queue<ListNode> queue = new PriorityQueue<>(lists.length, (a, b) -> (a.val - b.val));
    for (ListNode node : lists) {
      if (node != null)
        queue.offer(node);
    }

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (!queue.isEmpty()) {
      ListNode node = queue.poll();

      // remove duplicates if required
      while (node.next != null && node.val == node.next.val) {
        node = node.next;
      }

      // check duplicates if required
      if (tail.val != node.val) {
        tail.next = node;
        tail = node;
      }

      if (node.next != null)
        queue.offer(node.next);
    }

    return dummy.next;
  }

  // simply merge two sorted list
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null)
      return l2;
    if (l2 == null)
      return l1;
    if (l1.val < l2.val) {
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    } else {
      l2.next = mergeTwoLists(l2.next, l1);
      return l2;
    }
  }

  /**
   * Add Two Numbers II
   * 
   * You are given two non-empty linked lists representing two non-negative integers. The most
   * significant digit comes first and each of their nodes contain a single digit. Add the two numbers
   * and return it as a linked list.
   * 
   * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
   * 
   * Example:
   * 
   * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 8 -> 0 -> 7
   * 
   * _Reverse the 2 lists or use 2 stacks._
   */

  public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    while (l1 != null) {
      stack1.push(l1.val);
      l1 = l1.next;
    }

    while (l2 != null) {
      stack2.push(l2.val);
      l2 = l2.next;
    }

    int sum = 0;
    ListNode node = new ListNode(0);
    while (!stack1.isEmpty() || !stack2.isEmpty()) {
      if (!stack1.isEmpty())
        sum += stack1.pop();
      if (!stack2.isEmpty())
        sum += stack2.pop();
      node.val = sum % 10;
      ListNode head = new ListNode(sum /= 10);
      head.next = node;
      node = head;
    }

    return node.val == 0 ? node.next : node;
  }

  public static void main(String[] args) {
    ListAndLinkedList solution = new ListAndLinkedList();
    Random random = new Random();
    int bound = 10;

    int val = random.nextInt(bound);
    Node list1 = solution.new Node(val), list2 = solution.new Node(val);
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
