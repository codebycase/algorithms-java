package a07_hash_cache_memory;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * <pre>
Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LFUCache cache = new LFUCache(2);

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.get(3);       // returns 3.
cache.put(4, 4);    // evicts key 1.
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
 * </pre>
 * 
 * @author lchen
 * @category Hard
 *
 */
public class LFUCache {
	private int capacity = 0;
	private Node head = null;
	private HashMap<Integer, Integer> valueHash = new HashMap<Integer, Integer>();
	private HashMap<Integer, Node> nodeHash = new HashMap<Integer, Node>();

	public LFUCache(int capacity) {
		this.capacity = capacity;
	}

	public int get(int key) {
		if (valueHash.containsKey(key)) {
			increaseCount(key);
			return valueHash.get(key);
		}
		return -1;
	}

	public void set(int key, int value) {
		if (capacity == 0)
			return;
		if (valueHash.containsKey(key)) {
			valueHash.put(key, value);
		} else {
			if (valueHash.size() >= capacity)
				removeLeastUsed();
			valueHash.put(key, value);
			addToHead(key);
		}
		increaseCount(key);
	}

	private void addToHead(int key) {
		if (head == null) {
			head = new Node(0);
			head.keys.add(key);
		} else if (head.count > 0) {
			Node node = new Node(0);
			node.keys.add(key);
			node.next = head;
			head.prev = node;
			head = node;
		} else {
			head.keys.add(key);
		}
		nodeHash.put(key, head);
	}

	private void increaseCount(int key) {
		Node node = nodeHash.get(key);
		node.keys.remove(key);

		if (node.next == null) {
			node.next = new Node(node.count + 1);
			node.next.prev = node;
			node.next.keys.add(key);
		} else if (node.next.count == node.count + 1) {
			node.next.keys.add(key);
		} else {
			Node tmp = new Node(node.count + 1);
			tmp.keys.add(key);
			tmp.prev = node;
			tmp.next = node.next;
			node.next.prev = tmp;
			node.next = tmp;
		}

		nodeHash.put(key, node.next);
		if (node.keys.size() == 0)
			removeNode(node);
	}

	private void removeLeastUsed() {
		if (head == null)
			return;
		int leastUsed = head.keys.iterator().next();
		head.keys.remove(leastUsed);
		if (head.keys.size() == 0)
			removeNode(head);
		nodeHash.remove(leastUsed);
		valueHash.remove(leastUsed);
	}

	private void removeNode(Node node) {
		if (node.prev == null) {
			head = node.next;
		} else {
			node.prev.next = node.next;
		}
		if (node.next != null) {
			node.next.prev = node.prev;
		}
	}

	private class Node {
		private int count = 0;
		private Set<Integer> keys = new LinkedHashSet<Integer>();
		private Node prev = null, next = null;

		private Node(int count) {
			this.count = count;
		}
	}
	
	public static void main(String[] args) {
		LFUCache cache = new LFUCache(2);
		cache.set(1, 1);
		cache.set(2, 2);
		assert cache.get(1) == 1;
		cache.set(3, 3); // evicts key 2
		assert cache.get(2) == -1; // returns -1 (not found)
		assert cache.get(3) == 3; // returns 3.
		cache.set(4, 4); // evicts key 1.
		assert cache.get(1) == -1; // returns -1 (not found)
		assert cache.get(3) == 3; // returns 3
		assert cache.get(4) == 4; // returns 4   
	}
}