package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
		Set<String> beginSet = new HashSet<>();
		Set<String> endSet = new HashSet<>();

		int length = 1;
		Set<String> visited = new HashSet<>();

		beginSet.add(beginWord);
		endSet.add(endWord);
		while (!beginSet.isEmpty() && !endSet.isEmpty()) {
			// always choose the smaller end
			if (beginSet.size() > endSet.size()) {
				Set<String> set = beginSet;
				beginSet = endSet;
				endSet = set;
			}

			Set<String> temp = new HashSet<String>();
			for (String word : beginSet) {
				char[] chs = word.toCharArray();

				for (int i = 0; i < chs.length; i++) {
					char old = chs[i];
					for (char c = 'a'; c <= 'z'; c++) {
						chs[i] = c;
						String target = String.valueOf(chs);
						if (endSet.contains(target)) {
							return length + 1;
						}
						if (!visited.contains(target) && wordList.contains(target)) {
							temp.add(target);
							visited.add(target);
						}
					}
					chs[i] = old;
				}
			}
			beginSet = temp;
			length++;
		}

		return 0;
	}

	public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		LinkedList<WordNode> queue = new LinkedList<>();
		queue.add(new WordNode(beginWord, 1));
		wordList.add(endWord);

		while (!queue.isEmpty()) {
			WordNode top = queue.remove();
			String word = top.word;
			if (word.equals(endWord))
				return top.steps;

			char[] chs = word.toCharArray();
			for (int i = 0; i < chs.length; i++) {
				char old = chs[i];
				for (char c = 'a'; c <= 'z'; c++) {
					chs[i] = c;
					String target = new String(chs);
					if (wordList.contains(target)) {
						queue.add(new WordNode(target, top.steps + 1));
						wordList.remove(target);
					}
				}
				chs[i] = old;
			}
		}

		return 0;
	}

	private class WordNode {
		String word;
		int steps;

		public WordNode(String word, int steps) {
			this.word = word;
			this.steps = steps;
		}
	}

	public static void main(String[] args) {
		WordLadder solution = new WordLadder();
		List<String> wordList = new ArrayList<>();
		wordList.addAll(Arrays.asList("hot", "dot", "dog", "lot", "log"));
		int steps = solution.ladderLength2("hit", "cog", wordList);
		System.out.println(steps);
		assert steps == 5;
	}
}
