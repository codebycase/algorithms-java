package a02_arrays_strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
 * determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 * 
 * Note:
 * 
 * The same word in the dictionary may be reused multiple times in the segmentation. You may assume
 * the dictionary does not contain duplicate words. Example 1:
 * 
 * <pre>
Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false
 * </pre>
 * 
 * @author lchen
 *
 */
public class WordBreak {
	// Recursion with memoization
	public boolean wordBreak1(String s, List<String> wordDict) {
		return wordBreak1(s, new HashSet<String>(wordDict), 0, new Boolean[s.length()]);
	}

	public boolean wordBreak1(String s, Set<String> wordDict, int start, Boolean[] memo) {
		if (start == s.length())
			return true;
		if (memo[start] != null)
			return memo[start];
		for (int end = start + 1; end <= s.length(); end++) {
			if (wordDict.contains(s.substring(start, end)) && wordBreak1(s, wordDict, end, memo))
				return memo[start] = true;
		}
		return memo[start] = false;
	}

	// Use Breadth-First-Search
	public boolean wordBreak2(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet<>(wordDict);
		Queue<Integer> queue = new LinkedList<>();
		int[] visited = new int[s.length()];
		queue.add(0);

		while (!queue.isEmpty()) {
			int start = queue.remove();
			if (visited[start] == 0) {
				for (int end = start + 1; end <= s.length(); end++) {
					if (wordDictSet.contains(s.substring(start, end))) {
						queue.add(end);
						if (end == s.length())
							return true;
					}
				}
				visited[start] = 1;
			}
		}

		return false;
	}

	// Dynamic programming
	public boolean wordBreak3(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet<>(wordDict);
		boolean[] found = new boolean[s.length() + 1];
		found[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (found[j] && wordDictSet.contains(s.substring(j, i))) {
					found[i] = true;
					break;
				}
			}
		}
		return found[s.length()];
	}

	// Question: Concatenated Words
	// A word can only be formed by words shorter than it. So we can first sort the input by length of
	// each word, and only try to form one word by using words in front of it.
	public List<String> findAllConcatenateWords(String[] words) {
		List<String> result = new ArrayList<>();
		Set<String> preWords = new HashSet<>();
		Arrays.sort(words, (a, b) -> (a.length() - b.length()));
		for (String word : words) {
			if (canForm(word, preWords))
				result.add(word);
			preWords.add(word);
		}
		return result;
	}

	private static boolean canForm(String word, Set<String> dict) {
		if (dict.isEmpty())
			return false;
		boolean[] dp = new boolean[word.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= word.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && dict.contains(word.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[word.length()];
	}

	public List<String> findAllConcatenateWords2(String[] words) {
		List<String> result = new ArrayList<>();
		Set<String> preWords = new HashSet<>();
		Arrays.sort(words, (a, b) -> (a.length() - b.length()));
		for (String word : words) {
			if (canForm(word, preWords)) {
				result.add(word);
			}
			preWords.add(word);
		}
		return result;
	}

	public static void main(String[] args) {
		String s = "applepenapple";
		List<String> wordDict = Arrays.asList("apple", "pen");
		WordBreak solution = new WordBreak();
		System.out.println(solution.wordBreak1(s, wordDict));
	}
}
