package util;

public class ListNode {
	public int val;
	public ListNode prev;
	public ListNode next;
	public ListNode jump; // points to any other node

	public ListNode(int val) {
		this.val = val;
	}

	public ListNode(int val, ListNode next) {
		this(val);
		this.next = next;
	}

	public ListNode(int val, ListNode prev, ListNode next) {
		this(val, next);
		this.prev = prev;
	}

	public ListNode(int val, ListNode prev, ListNode next, ListNode jump) {
		this(val, prev, next);
		this.jump = jump;
	}

	public String toString() {
		return String.valueOf(val);
	}
}
