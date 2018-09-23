package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * <pre>
Given two words (beginWord and endWord), and a dictionary's word list, 
find the length of shortest transformation sequence from beginWord to endWord, such that:

Only one letter can be changed at a time.
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]
As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Note:
Return 0 if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
 * </pre>
 * 
 * @author lchen
 *
 */
public class WordLadder {
	// Two-end BFS, O(n^k) -> O(2n^(k/2))
	public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordSet = new HashSet<>(wordList);
		// confirm if word list must contain end word!
		if (!wordSet.contains(endWord))
			return 0;

		int length = 1;

		Set<String> beginSet = new HashSet<>();
		Set<String> endSet = new HashSet<>();
		beginSet.add(beginWord);
		endSet.add(endWord);

		while (!beginSet.isEmpty() && !endSet.isEmpty()) {
			// always choose the smaller end
			if (beginSet.size() > endSet.size()) {
				Set<String> temp = beginSet;
				beginSet = endSet;
				endSet = temp;
			}

			Set<String> newSet = new HashSet<String>();
			for (String word : beginSet) {
				char[] chrs = word.toCharArray();
				for (int i = 0; i < chrs.length; i++) {
					char temp = chrs[i];
					for (char c = 'a'; c <= 'z'; c++) {
						chrs[i] = c;
						String target = String.valueOf(chrs);
						if (endSet.contains(target))
							return length + 1;
						if (wordSet.contains(target)) {
							newSet.add(target);
							wordSet.remove(target);
						}
					}
					chrs[i] = temp;
				}
			}
			beginSet = newSet;
			length++;
		}

		return 0;
	}

	// Use Set O(n^k)
	public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		Set<String> set = new HashSet<>(wordList);
		Queue<Ladder> queue = new LinkedList<>();
		queue.offer(new Ladder(beginWord, 1));
		while (!queue.isEmpty()) {
			Ladder ladder = queue.poll();
			char[] chrs = ladder.word.toCharArray();
			for (int i = 0; i < chrs.length; i++) {
				char temp = chrs[i];
				for (char j = 'a'; j <= 'z'; j++) {
					chrs[i] = j;
					String target = new String(chrs);
					if (set.contains(target)) {
						if (target.equals(endWord))
							return ladder.depth + 1;
						queue.offer(new Ladder(target, ladder.depth + 1));
						set.remove(target); // only use it once!
					}
				}
				chrs[i] = temp;
			}
		}
		return 0;
	}

	// Use Trie O(n^k), break loop as earlier as possible!
	public int ladderLength3(String beginWord, String endWord, List<String> wordList) {
		TrieNode trie = buildTrieTree(wordList);
		Queue<Ladder> queue = new LinkedList<>();
		queue.offer(new Ladder(beginWord, 1));
		while (!queue.isEmpty()) {
			Ladder ladder = queue.poll();
			char[] chrs = ladder.word.toCharArray();
			TrieNode node = trie;
			for (int i = 0; node != null && i < chrs.length; i++) {
				char temp = chrs[i];
				for (char j = 'a'; j <= 'z'; j++) {
					chrs[i] = j;
					if (searchAndMark(node, chrs, i)) {
						String target = new String(chrs);
						if (target.equals(endWord))
							return ladder.depth + 1;
						queue.offer(new Ladder(target, ladder.depth + 1));
					}
				}
				chrs[i] = temp;
				node = node.next[temp - 'a'];
			}
		}
		return 0;
	}

	private TrieNode buildTrieTree(List<String> words) {
		TrieNode root = new TrieNode();
		for (String word : words) {
			TrieNode node = root;
			for (int i = 0; i < word.length(); i++) {
				int j = word.charAt(i) - 'a';
				if (node.next[j] == null)
					node.next[j] = new TrieNode();
				node = node.next[j];
			}
			node.isWord = true;
		}
		return root;
	}

	private boolean searchAndMark(TrieNode node, char[] word, int start) {
		for (int i = start; i < word.length; i++) {
			node = node.next[word[i] - 'a'];
			if (node == null)
				return false;
		}
		boolean isWord = node.isWord;
		node.isWord = false;
		return isWord;
	}

	private class Ladder {
		String word;
		int depth;

		Ladder(String word, int deep) {
			this.word = word;
			this.depth = deep;
		}
	}

	class TrieNode {
		boolean isWord = false;
		TrieNode[] next = new TrieNode[26];
	}

	public static void main(String[] args) {
		WordLadder solution = new WordLadder();
		List<String> wordList = new ArrayList<>();
		wordList.addAll(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"));
		int steps = solution.ladderLength("hit", "cog", wordList);
		System.out.println(steps);
		assert steps == 5;
	}
}
