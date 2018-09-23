package a07_hash_cache_memory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

Follow up:
Could you do both operations in O(1) time complexity?

Example:

LRUCache cache = new LRUCache(2);

cache.put(1, 1);
cache.put(2, 2);
cache.get(1);       // returns 1
cache.put(3, 3);    // evicts key 2
cache.get(2);       // returns -1 (not found)
cache.put(4, 4);    // evicts key 1
cache.get(1);       // returns -1 (not found)
cache.get(3);       // returns 3
cache.get(4);       // returns 4
 * </pre>
 * 
 * Achieve the LRU Cache by using LinkedHashMap.
 * 
 * @author lchen
 *
 */
public class LRUCache {
	private Map<Integer, Node> cacheMap;
	private Node head, tail;
	private int capacity;

	public LRUCache(int capacity) {
		if (capacity <= 0)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		cacheMap = new HashMap<>();
		head = new Node(0, 0);
		tail = new Node(0, 0);
		head.next = tail;
		tail.prev = head;
	}

	public int get(int key) {
		if (cacheMap.containsKey(key)) {
			Node node = cacheMap.get(key);
			deleteNode(node);
			addToHead(node);
			return node.value;
		}
		return -1;
	}

	public void put(int key, int value) {
		if (cacheMap.containsKey(key)) {
			Node node = cacheMap.get(key);
			node.value = value;
			deleteNode(node);
			addToHead(node);
		} else {
			Node node = new Node(key, value);
			cacheMap.put(key, node);
			if (cacheMap.size() > capacity) {
				cacheMap.remove(tail.prev.key);
				deleteNode(tail.prev);
			}
			addToHead(node);
		}
	}

	private void deleteNode(Node node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	private void addToHead(Node node) {
		node.next = head.next;
		node.next.prev = node;
		node.prev = head;
		head.next = node;
	}

	class Node {
		int key, value;
		Node prev, next;

		public Node(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}

	public class LRUCache2<K, V> {
		private Map<K, V> map;
		protected int maxCapacity; // cache capacity

		public LRUCache2(int maxCapacity) {
			// Keys are sorted on the basis of access order e.g Invoking the put, putIfAbsent, get,
			// getOrDefault, compute, computeIfAbsent, computeIfPresent, or merge methods results in an access
			// to the corresponding entry.
			map = new LinkedHashMap<K, V>(maxCapacity, 0.75f, true) {
				private static final long serialVersionUID = 1L;

				protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
					return size() > maxCapacity;
				}
			};
		}

		public V get(K key) {
			return map.getOrDefault(key, null);
		}

		public void set(K key, V value) {
			map.put(key, value);
		}
	}

	public static void main(String[] args) {
		LRUCache cache = new LRUCache(2);
		cache.put(1, 1);
		cache.put(2, 2);
		assert cache.get(1) == 1;
		cache.put(3, 3); // evicts key 2
		assert cache.get(2) == -1; // returns -1 (not found)
		cache.put(4, 4); // evicts key 1.
		assert cache.get(1) == -1; // returns -1 (not found)
		assert cache.get(3) == 3; // returns 3
		assert cache.get(4) == 4; // returns 4
	}
}