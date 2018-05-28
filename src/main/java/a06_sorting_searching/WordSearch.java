package a06_sorting_searching;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

For example,
Given board =

[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
word = "ABCCED", -> returns true,
word = "SEE", -> returns true,
word = "ABCB", -> returns false.
 * </pre>
 * 
 * @author lchen
 *
 */
public class WordSearch {
	public boolean exist(char[][] board, String word) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (exist(board, row, col, word, 0))
					return true;
			}
		}
		return false;
	}

	private boolean exist(char[][] board, int row, int col, String word, int start) {
		if (start == word.length())
			return true;
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length)
			return false;
		if (board[row][col] != word.charAt(start))
			return false;
		board[row][col] ^= 256;
		boolean exist = exist(board, row + 1, col, word, start + 1) || exist(board, row, col + 1, word, start + 1)
				|| exist(board, row - 1, col, word, start + 1) || exist(board, row, col - 1, word, start + 1);
		board[row][col] ^= 256;
		return exist;

	}

	/**
	 * <pre>
	Given a 2D board and a list of words from the dictionary, find all words in the board.
	
	Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
	
	For example,
	Given words = ["oath","pea","eat","rain"] and board =
	
	[
	['o','a','a','n'],
	['e','t','a','e'],
	['i','h','k','r'],
	['i','f','l','v']
	]
	Return ["eat","oath"].
	 * </pre>
	 * 
	 * @param board
	 * @param words
	 * @return
	 */
	public List<String> findWords(char[][] board, String[] words) {
		List<String> result = new ArrayList<>();
		Node trie = buildTrie(words);
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				dfs(board, row, col, trie, result);
			}
		}
		return result;
	}

	private void dfs(char[][] board, int row, int col, Node node, List<String> result) {
		char c = board[row][col];

		if (c == '#' || node.next[c - 'a'] == null)
			return;

		node = node.next[c - 'a'];
		if (node.word != null) {
			result.add(node.word);
			node.word = null; // avoid duplicate
		}

		board[row][col] = '#';
		if (row > 0)
			dfs(board, row - 1, col, node, result);
		if (col > 0)
			dfs(board, row, col - 1, node, result);
		if (row < board.length - 1)
			dfs(board, row + 1, col, node, result);
		if (col < board[0].length - 1)
			dfs(board, row, col + 1, node, result);
		board[row][col] = c;
	}

	private Node buildTrie(String[] words) {
		Node root = new Node();
		for (String word : words) {
			Node node = root;
			for (char c : word.toCharArray()) {
				int i = c - 'a';
				if (node.next[i] == null) {
					node.next[i] = new Node();
					node.count++;
				}
				node = node.next[i];
			}
			node.word = word;
		}
		return root;
	}

	class Node {
		Node[] next = new Node[26];
		int count = 0; // count children nodes, zero means a leave
		String word = null;
	}

}
