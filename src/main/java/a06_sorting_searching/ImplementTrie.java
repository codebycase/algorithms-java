package a06_sorting_searching;

public class ImplementTrie {
	private TrieNode root;

	class TrieNode {
		public char val;
		public boolean isWord;
		public TrieNode[] children = new TrieNode[26];

		public TrieNode(char val) {
			this.val = val;
		}
	}

	/** Initialize your data structure here. */
	public ImplementTrie() {
		root = new TrieNode(' ');
	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (node.children[c - 'a'] == null) {
				node.children[c - 'a'] = new TrieNode(c);
			}
			node = node.children[c - 'a'];
		}
		node.isWord = true;
	}

	/** Returns if the word is in the trie. */
	public boolean search(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			node = node.children[c - 'a'];
			if (node == null)
				return false;
		}
		return node.isWord;
	}

	/** Returns if there is any word in the trie that starts with the given prefix. */
	public boolean startsWith(String prefix) {
		TrieNode node = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.charAt(i);
			node = node.children[c - 'a'];
			if (node == null)
				return false;
		}
		return true;
	}
}