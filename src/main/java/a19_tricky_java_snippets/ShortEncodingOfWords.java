package a19_tricky_java_snippets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import util.ListNode;

/**
 * Given a list of words, we may encode it by writing a reference string S and a list of indexes A.
 * 
 * For example, if the list of words is ["time", "me", "bell"], we can write it as S = "time#bell#"
 * and indexes = [0, 2, 5].
 * 
 * Then for each index, we will recover the word by reading from the reference string from that
 * index until we reach a "#" character.
 * 
 * What is the length of the shortest reference string S possible that encodes the given words?
 * 
 * Example:
 * 
 * <pre>
Input: words = ["time", "me", "bell"]
Output: 10
Explanation: S = "time#bell#" and indexes = [0, 2, 5].
 * </pre>
 * 
 * @author lchen
 *
 */
public class ShortEncodingOfWords {
	public int minimumLengthEncoding(String[] words) {
		Node trie = new Node();
		// add each word to trie tree
		for (String word : words) {
			add(trie, word);
		}
		// collect lengths with recursion
		return collect(trie);
	}

	private void add(Node trie, String word) {
		Node node = trie;
		for (int i = word.length() - 1; i >= 0; i--) {
			node = node.get(word.charAt(i));
		}
		node.word = word;
	}

	private int collect(Node node) {
		if (node == null)
			return 0;
		if (node.count == 0)
			return node.word.length() + 1;
		int count = 0;
		for (Node n : node.next) {
			count += collect(n);
		}
		return count;
	}

	class Node {
		Node[] next = new Node[26];
		int count = 0; // count children nodes, zero means a leave
		String word = null;

		public Node get(char c) {
			int i = c - 'a';
			if (next[i] == null) {
				next[i] = new Node();
				count++;
			}
			return next[i];
		}
	}

	public int numComponents(ListNode head, int[] G) {
		Set<Integer> set = new HashSet<>();
		for (int g : G)
			set.add(g);
		int count = 0;
		ListNode node = head;
		while (node != null) {
			if (set.contains(node.val)) {
				count++;
				// scan to the end of current connected subset component
				while (node.next != null && set.contains(node.next.val))
					node = node.next;
			}
			node = node.next;
		}
		return count;
	}

	public static void main(String[] args) {
		ShortEncodingOfWords solution = new ShortEncodingOfWords();
		String[] words = { "time", "me", "bell" };
		assert solution.minimumLengthEncoding(words) == 10;
		System.out.println(Integer.numberOfLeadingZeros(3));
		Set<String> set = new HashSet<>(Arrays.asList(words));
		System.out.println(set);
	}
}
