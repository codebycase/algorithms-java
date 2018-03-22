package a03_linked_lists;

import java.util.PriorityQueue;
import java.util.Queue;

import util.ListNode;

public class LinkedListBootCamp {
	/**
	 * Given a singly linked list and an integer k, write a program to remove the kth last element.
	 */
	public ListNode deleteKthLast(ListNode l, int k) {
		ListNode dummyHead = new ListNode(0, l);

		ListNode first = dummyHead.next; // start with next!
		while (k-- > 0) {
			first = first.next;
		}

		ListNode second = dummyHead; // start with dummy head!
		while (first != null) {
			first = first.next;
			second = second.next;
		}

		// second points to the (k+1)-th last node
		second.next = second.next.next;
		return dummyHead.next;
	}

	/**
	 * The problem is concerned with removing duplicates from a sorted list of integers.
	 */
	public ListNode removeDuplicates(ListNode l) {
		ListNode cur = l;

		while (cur != null) {
			ListNode next = cur.next;
			while (next != null && next.val == cur.val) {
				next = next.next;
			}
			cur.next = next;
			cur = next;
		}

		return l;
	}

	/**
	 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
	 * 
	 * For example:
	 * 
	 * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
	 * 
	 * return 1->4->3->2->5->NULL.
	 */
	public ListNode reverseSubList(ListNode head, int m, int n) {
		if (head == null)
			return null;

		ListNode dummy = new ListNode(0);
		dummy.next = head;

		ListNode before = dummy;
		for (int i = 1; i < m; i++) {
			before = before.next;
		}

		ListNode middle = before.next;
		for (int i = 0; i < n - m; i++) {
			ListNode after = middle.next;
			middle.next = after.next;
			after.next = before.next;
			before.next = after;
		}

		return dummy.next;
	}

	/**
	 * Write a program that takes as input a singly linked list and a nonnegative integer k, and
	 * return s the list cyclically shifted to the right by k.
	 */
	public ListNode rightShiftList(ListNode list, int k) {
		if (list == null)
			return list;

		// computate the length and the tail
		int len = 1; // starts with 1
		ListNode tail = list;
		while (tail.next != null) {
			len++;
			tail = tail.next;
		}

		k %= len; // if k > len, k is actually k mod len
		if (k == 0)
			return list;

		tail.next = list; // make a cycle
		int stepsToNewHead = len - k;
		ListNode newTail = tail;
		while (stepsToNewHead-- > 0) {
			newTail = newTail.next;
		}
		ListNode newHead = newTail.next;
		newTail.next = null;

		return newHead;
	}

	/**
	 * Write a program that tests whether a singly linked list is palindromic.
	 */
	public boolean checkPalindromic(ListNode list) {
		// find the second half of l
		ListNode slow = list, fast = list;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		ListNode firstHalf = list;
		ListNode secondHalf = reverseList(slow);
		while (secondHalf != null && firstHalf != null) {
			if (secondHalf.val != firstHalf.val)
				return false;
			secondHalf = secondHalf.next;
			firstHalf = firstHalf.next;
		}
		return true;
	}

	private ListNode reverseList(ListNode list) {
		ListNode dummy = new ListNode(0, list);
		ListNode before = dummy;
		ListNode middle = before.next;
		while (middle.next != null) {
			ListNode after = middle.next;
			middle.next = after.next;
			after.next = before.next;
			before.next = after;
		}
		return dummy.next;
	}

	/**
	 * The loop runs n * k times. In every iteration of loop, we call heapify which takes O(Logk)
	 * time. Therefore, the time complexity is O(nkLog(k)).
	 * 
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

	public ListNode detectCycle(ListNode head) {
		ListNode slow = head, fast = head;

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) { // there is a cycle
				// point slow back to start
				slow = head;
				// both pointers advance at the same time
				while (slow != fast) {
					slow = slow.next;
					fast = fast.next;
				}
				return slow;
			}
		}

		return null;
	}

	/**
	 * Implement a function which takes as input a singly linked list and integer k and perform a
	 * pivot of the list respect to k.
	 */
	public ListNode listPivoting(ListNode l, int x) {
		ListNode lessHead = new ListNode(0);
		ListNode equalHead = new ListNode(0);
		ListNode greaterHead = new ListNode(0);
		ListNode lessIter = lessHead;
		ListNode equalIter = equalHead;
		ListNode greaterIter = greaterHead;
		ListNode iter = l;
		while (iter != null) {
			if (iter.val < x) {
				lessIter.next = iter;
				lessIter = iter;
			} else if (iter.val == x) {
				equalIter.next = iter;
				equalIter = iter;
			} else {
				greaterIter.next = iter;
				greaterIter = iter;
			}
			iter = iter.next;
		}
		// combine three lists
		greaterIter.next = null;
		equalIter.next = greaterHead.next;
		lessIter.next = equalHead.next;
		return lessHead.next;
	}

	/**
	 * You are given two non-empty linked lists representing two non-negative integers. The digits
	 * are stored in reverse order and each of their nodes contain a single digit. Add the two
	 * numbers and return it as a linked list.
	 * 
	 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
	 * 
	 * Example
	 * 
	 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8 Explanation: 342 + 465 = 807.
	 * 
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode head = new ListNode(0);
		ListNode prev = head;
		int carry = 0;
		while (l1 != null || l2 != null || carry != 0) {
			ListNode cur = new ListNode(0);
			int sum = ((l2 == null) ? 0 : l2.val) + ((l1 == null) ? 0 : l1.val) + carry;
			cur.val = sum % 10;
			carry = sum / 10;
			prev.next = cur;
			prev = cur;

			l1 = (l1 == null) ? l1 : l1.next;
			l2 = (l2 == null) ? l2 : l2.next;
		}
		return head.next;
	}

	/**
	 * Write a program that takes two singly linked lists, and determines if there exists a node
	 * that is common to both lists. The 2 lists may each or both have a cycle.
	 */
	public ListNode overlappingLists(ListNode l1, ListNode l2) {
		// store the start of cycle if any
		ListNode root1 = detectCycle(l1);
		ListNode root2 = detectCycle(l2);

		if (root1 == null && root2 == null) {
			return overlappingNoCycleLists(l1, l2);
		} else if ((root1 == null && root2 != null) || (root1 != null && root2 == null)) {
			return null; // one list has cycle, one has no cycle
		}

		// now both lists have cycles!
		ListNode temp = root2;
		do {
			temp = temp.next;
		} while (temp != root1 && temp != root2);

		// l1 and l2 do not end in the same cycle
		if (temp != root1) {
			return null; // cycles are disjoint
		}

		// l1 and l2 end in the same cycles, locate the overlapping node if they first overlapp
		// before cycle starts
		int stemLen1 = distance(l1, root1);
		int stemLen2 = distance(l2, root2);
		int count = Math.abs(stemLen1 - stemLen2);
		if (stemLen1 > stemLen2) {
			l1 = advance(l1, count);
		} else {
			l2 = advance(l2, count);
		}

		while (l1 != l2 && l1 != root1 && l2 != root2) {
			l1 = l1.next;
			l2 = l2.next;
		}

		// if l1 == l2, means the overlap first occurs before the cycle starts; otherwise, the first
		// overlapping node is not unique, we can return any node on the cycle.
		return l1 == l2 ? l1 : root1;
	}

	private ListNode overlappingNoCycleLists(ListNode l1, ListNode l2) {
		int l1Len = length(l1), l2Len = length(l2);

		// advance the longer list to get equal length lists
		if (l1Len > l2Len) {
			l1 = advance(l1, l1Len - l2Len);
		} else {
			l2 = advance(l2, l2Len - l1Len);
		}

		while (l1 != null && l2 != null && l1 != l2) {
			l1 = l1.next;
			l2 = l2.next;
		}

		// null implies there is no overlap between l1 and l2
		return l1;
	}

	/**
	 * Let L be a singly linked list. Assume its nodes are numbered starting at 0, Define the zip of
	 * L to be the list consisting of the interleaving of the nodes numbered 0, 1, 2,..with the
	 * nodes numbered n - 1, n - 2, n - 3,... Implement the zip function.
	 */
	public ListNode zipLinkedList(ListNode list) {
		if (list == null || list.next == null)
			return list;

		// find the second half of list
		ListNode slow = list, fast = list;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		ListNode firstHalfHead = list;
		ListNode secondHalfHead = slow.next;
		slow.next = null; // split the list

		// reverse the second half
		secondHalfHead = reverseList(secondHalfHead);

		// interleave the 2 lists
		ListNode firstHalfIter = firstHalfHead;
		ListNode secondHalfIter = secondHalfHead;
		while (secondHalfIter != null) {
			ListNode temp = secondHalfIter.next;
			secondHalfIter.next = firstHalfIter.next;
			firstHalfIter.next = secondHalfIter;
			firstHalfIter = firstHalfIter.next.next;
			secondHalfIter = temp;
		}

		return list;
	}

	/**
	 * A posting list is a single linked list with an additional "jump" field at each node. The jump
	 * field points to any other node. Implement a function which takes a postings list and returns
	 * a copy of it.
	 */
	public ListNode copyPostingList(ListNode list) {
		if (list == null)
			return list;

		// make a copy without assigning the jump field
		ListNode iter = list;
		while (iter != null) {
			ListNode newNode = new ListNode(iter.val, iter.next, null);
			iter.next = newNode;
			iter = newNode.next;
		}

		// assign the jump field in the copied list
		iter = list;
		while (iter != null) {
			if (iter.jump != null) {
				// iter.jump.next is the copied node!
				iter.next.jump = iter.jump.next;
			}
			iter = iter.next.next;
		}

		// revert original list and assign the next field of copied list
		iter = list;
		ListNode newListHead = iter.next;
		while (iter.next != null) {
			ListNode temp = iter.next;
			iter.next = temp.next;
			iter = temp;
		}

		return newListHead;
	}

	private int distance(ListNode start, ListNode end) {
		int distance = 0;
		while (start != end) {
			start = start.next;
			distance++;
		}
		return distance;
	}

	private ListNode advance(ListNode l1, int k) {
		while (k-- > 0) {
			l1 = l1.next;
		}
		return l1;
	}

	private int length(ListNode l) {
		int len = 0;
		while (l != null) {
			l = l.next;
			len++;
		}
		return len;
	}

	public String printListNode(ListNode head) {
		StringBuilder sb = new StringBuilder();
		while (head != null) {
			sb.append(head.val);
			head = head.next;
			if (head != null)
				sb.append("->");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		ListNode one = new ListNode(1);
		one.next = new ListNode(2);
		one.next.next = new ListNode(2);
		ListNode two = new ListNode(1);
		two.next = new ListNode(1);
		two.next.next = new ListNode(2);
		LinkedListBootCamp camp = new LinkedListBootCamp();
		assert camp.printListNode(camp.mergeKSortedLists(new ListNode[] { one, two })).equals("1->2");

		// check list palindrome
		ListNode list = new ListNode(1,
				new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(2, new ListNode(1, null))))));
		assert (camp.checkPalindromic(list));
		list = new ListNode(1, new ListNode(2, new ListNode(5, new ListNode(2, new ListNode(1, null)))));
		assert (camp.checkPalindromic(list));

		// check list pivoting
		list = new ListNode(6,
				new ListNode(3, new ListNode(1, new ListNode(4, new ListNode(2, new ListNode(5, null))))));
		assert camp.printListNode(camp.listPivoting(list, 4)).equals("3->1->2->4->6->5");

		// overlapping list test cases
		ListNode l1 = null;
		ListNode l2 = null;
		// L1: 1->2->3->4->5->6-
		// ^ ^ |
		// | |____|
		// L2: 7->8-----
		l1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, null))))));
		l1.next.next.next.next.next.next = l1.next.next.next.next;

		l2 = new ListNode(7, new ListNode(8, null));
		l2.next.next = l1.next.next.next;
		assert (camp.overlappingLists(l1, l2).val == 4);

		// L1: 1->2->3->4->5->6-
		// ^ ^ |
		// | |____|
		// L2: 7->8---
		l2.next.next = l1.next.next;
		assert (camp.overlappingLists(l1, l2).val == 3);
	}
}
