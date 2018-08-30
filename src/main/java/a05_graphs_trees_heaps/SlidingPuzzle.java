package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class SlidingPuzzle {
	public int slidingPuzzle(int[][] board) {
		int m = board.length, n = board[0].length;
		int sr = 0, sc = 0;
		search: for (sr = 0; sr < m; sr++)
			for (sc = 0; sc < n; sc++)
				if (board[sr][sc] == 0)
					break search;

		int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		Queue<Node> queue = new ArrayDeque<>();
		Node start = new Node(board, sr, sc, 0);
		queue.add(start);

		Set<String> visited = new HashSet<>();
		visited.add(start.boardHash);

		String target = Arrays.deepToString(new int[][] { { 1, 2, 3 }, { 4, 5, 0 } });

		while (!queue.isEmpty()) {
			Node node = queue.remove();
			if (node.boardHash.equals(target))
				return node.depth;

			for (int[] dir : dirs) {
				int neiR = dir[0] + node.zeroR;
				int neiC = dir[1] + node.zeroC;

				if (neiR < 0 || neiR >= m || neiC < 0 || neiC >= n)
					continue;

				int[][] newBoard = new int[m][n];
				int t = 0;
				for (int[] row : node.curBoard)
					newBoard[t++] = row.clone();
				newBoard[node.zeroR][node.zeroC] = newBoard[neiR][neiC];
				newBoard[neiR][neiC] = 0;

				Node nei = new Node(newBoard, neiR, neiC, node.depth + 1);
				if (visited.contains(nei.boardHash))
					continue;
				queue.add(nei);
				visited.add(nei.boardHash);
			}
		}

		return -1;
	}

	class Node {
		int[][] curBoard;
		String boardHash;
		int zeroR;
		int zeroC;
		int depth;

		Node(int[][] curBoard, int zeroR, int zeorC, int depth) {
			this.curBoard = curBoard;
			this.zeroR = zeroR;
			this.zeroC = zeorC;
			this.depth = depth;
			this.boardHash = Arrays.deepToString(curBoard);
		}
	}

	public static void main(String[] args) {
		int[][] board = { { 4, 1, 2 }, { 5, 0, 3 } };
		SlidingPuzzle solution = new SlidingPuzzle();
		assert solution.slidingPuzzle(board) == 5;
		board = new int[][] { { 3, 2, 4 }, { 1, 5, 0 } };
		assert solution.slidingPuzzle(board) == 14;
		board = new int[][] { { 1, 2, 3 }, { 5, 4, 0 } };
		assert solution.slidingPuzzle(board) == -1;
	}

}
