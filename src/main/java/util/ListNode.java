package util;

public class ListNode {
	public int val;
	public ListNode next;
	public ListNode jump; // points to any other node

	public ListNode(int val) {
		this.val = val;
	}

	public ListNode(int val, ListNode next) {
		this(val);
		this.next = next;
	}

	public ListNode(int val, ListNode next, ListNode jump) {
		this(val, next);
		this.jump = jump;
	}

	public String toString() {
		return String.valueOf(val);
	}
}
