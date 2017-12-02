package a07_hash_cache_memory;

import java.util.Map;
import java.util.Objects;

public class HashTable<K, V> {
	// Some VMs reserve some header words in an array
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	// The hash table data
	private transient Entry<?, ?>[] table;
	// The total number of entries in the hash table
	private transient int count;
	// The table is rehashed when it's size exceeds this threshould (capacity * loadFactor)
	private int threshold;
	// The load factor for the hash table
	private float loadFactor;

	public HashTable() {
		int capacity = 10;
		table = new Entry<?, ?>[1];
		loadFactor = 0.75f;
		threshold = (int) Math.min(capacity * loadFactor, MAX_ARRAY_SIZE + 1);
	}

	public int size() {
		return count;
	}

	public boolean containsValue(Object value) {
		Entry<?, ?> tab[] = table;
		for (int i = tab.length; i-- > 0;) {
			for (Entry<?, ?> e = tab[i]; e != null; e = e.next) {
				if (e.value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	// Compare both hash and key due to different key could have same hash code
	public boolean containsKey(Object key) {
		Entry<?, ?> tab[] = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<?, ?> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key.equals(key)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		Entry<?, ?> tab[] = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for (Entry<?, ?> e = tab[index]; e != null; e = e.next) {
			if ((e.hash == hash) && e.key.equals(key)) {
				return (V) e.value;
			}
		}
		return null;
	}

	public V put(K key, V value) {
		if (value == null)
			throw new NullPointerException();

		Entry<?, ?> tab[] = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		@SuppressWarnings("unchecked")
		Entry<K, V> entry = (Entry<K, V>) tab[index];
		for (; entry != null; entry = entry.next) {
			if ((entry.hash == hash) && entry.key.equals(key)) {
				V old = entry.value;
				entry.value = value;
				return old;
			}
		}

		addEntry(hash, key, value, index);
		return null;
	}

	private void addEntry(int hash, K key, V value, int index) {
		Entry<?, ?> tab[] = table;
		if (count >= threshold) {
			rehash();
			tab = table;
			hash = key.hashCode();
			index = (hash & 0x7FFFFFFF) % tab.length;
		}
		@SuppressWarnings("unchecked")
		Entry<K, V> e = (Entry<K, V>) tab[index];
		tab[index] = new Entry<>(hash, key, value, e);
		count++;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		int oldCapacity = table.length;
		Entry<?, ?>[] oldMap = table;

		// overflow-conscious code
		int newCapacity = (oldCapacity << 1) + 1;
		if (newCapacity - MAX_ARRAY_SIZE > 0) {
			if (oldCapacity == MAX_ARRAY_SIZE)
				// Keep running with max buckets
				return;
			newCapacity = MAX_ARRAY_SIZE;
		}
		Entry<?, ?>[] newMap = new Entry<?, ?>[newCapacity];

		threshold = (int) Math.min(newCapacity * loadFactor, MAX_ARRAY_SIZE);
		table = newMap;

		for (int i = oldCapacity; i-- > 0;) {
			for (Entry<K, V> old = (Entry<K, V>) oldMap[i]; old != null;) {
				Entry<K, V> e = old;
				old = old.next;

				int index = (e.hash & 0x7FFFFFFF) % newCapacity;
				e.next = (Entry<K, V>) newMap[index];
				newMap[index] = e;
			}
		}
	}

	private static class Entry<K, V> implements Map.Entry<K, V> {
		final int hash;
		final K key;
		V value;
		Entry<K, V> next;

		protected Entry(int hash, K key, V value, Entry<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			if (value == null)
				throw new NullPointerException();
			V oldValue = this.value;
			this.value = value;
			return oldValue;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
			return (key == null ? e.getKey() == null : key.equals(e.getKey()))
					&& (value == null ? e.getValue() == null : value.equals(e.getValue()));
		}

		public int hashCode() {
			return hash ^ Objects.hashCode(value);
		}

		public String toString() {
			return key.toString() + "=" + value.toString();
		}

		@SuppressWarnings("unchecked")
		protected Object clone() {
			return new Entry<>(hash, key, value, (next == null ? null : (Entry<K, V>) next.clone()));
		}
	}

	public static void main(String[] args) {
		HashTable<String, String> table = new HashTable<>();
		table.put("abc", "edf");
		table.put("abc", "hij");
		assert table.get("abc").equals("hij");
		table.put("abc2", "edf2");
		assert table.get("abc2").equals("edf2");
		HashTable<Integer, Integer> hashTable = new HashTable<>();
		for (int i = 0; i < 100; i++) {
			hashTable.put(i, i);
		}
		for (int i = 0; i < 100; i++) {
			assert hashTable.get(i) == i;
		}
	}

}
