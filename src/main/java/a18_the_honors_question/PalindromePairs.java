package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PalindromePairs {
	public List<List<Integer>> palindromePairs(String[] words) {
		List<List<Integer>> res = new ArrayList<>();

		// build trie with word reversed
		TrieNode root = new TrieNode();
		for (int i = 0; i < words.length; i++)
			addWord(root, words[i], i);

		for (int i = 0; i < words.length; i++)
			search(words, i, root, res);

		return res;
	}

	private void addWord(TrieNode node, String word, int index) {
		for (int i = word.length() - 1; i >= 0; i--) {
			int j = word.charAt(i) - 'a';
			if (node.next[j] == null)
				node.next[j] = new TrieNode();
			// store all palindromes below current node
			if (isPalindrome(word, 0, i))
				node.list.add(index);
			node = node.next[j];
		}
		node.index = index;
		node.list.add(index); // always palindromes for blank
	}

	private void search(String[] words, int i, TrieNode node, List<List<Integer>> res) {
		for (int j = 0; j < words[i].length(); j++) {
			if (node.index >= 0 && node.index != i && isPalindrome(words[i], j, words[i].length() - 1)) {
				res.add(Arrays.asList(i, node.index));
			}
			node = node.next[words[i].charAt(j) - 'a'];
			if (node == null)
				return;
		}
		// the stored palindromes under this node
		for (int j : node.list) {
			if (i == j)
				continue;
			res.add(Arrays.asList(i, j));
		}
	}

	private boolean isPalindrome(String word, int i, int j) {
		while (i < j) {
			if (word.charAt(i++) != word.charAt(j--))
				return false;
		}
		return true;
	}

	class TrieNode {
		TrieNode[] next;
		int index;
		List<Integer> list;

		TrieNode() {
			next = new TrieNode[26];
			index = -1;
			list = new ArrayList<>();
		}
	}

	// say this pair of words: {"dcbab","cd"}, we need check both "dcbabcd" and "cddcbab"
	public List<List<Integer>> palindromePairs2(String[] words) {
		List<List<Integer>> pairs = new LinkedList<>();
		if (words == null || words.length == 0)
			return pairs;
		HashMap<String, Integer> map = new HashMap<>();
		// add reversed words to map!
		for (int i = 0; i < words.length; i++)
			map.put(new StringBuilder(words[i]).reverse().toString(), i);
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			int l = 0, r = 0;
			while (l <= r) {
				Integer j = map.get(word.substring(l, r));
				// check both left and right side
				if (j != null && i != j && isPalindrome(word.substring(l == 0 ? r : 0, l == 0 ? word.length() : l)))
					pairs.add(l == 0 ? Arrays.asList(i, j) : Arrays.asList(j, i));
				if (r < word.length())
					r++;
				else
					l++;
			}
		}
		return pairs;
	}

	private boolean isPalindrome(String word) {
		int i = 0, j = word.length() - 1;
		while (i < j) {
			if (word.charAt(i++) != word.charAt(j--))
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		String[] words = new String[] { "abcd", "dcba", "lls", "s", "sssll" };
		PalindromePairs solution = new PalindromePairs();
		assert solution.palindromePairs(words).toString().equals("[[0, 1], [1, 0], [2, 4], [3, 2]]");
	}
}
