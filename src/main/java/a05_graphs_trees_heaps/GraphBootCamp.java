package a05_graphs_trees_heaps;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import util.Point;

public class GraphBootCamp {

	/**
	 * Implement a routine that takes an n x m boolean array A together with an entry (x, y) and
	 * flips the color of the region associated with (x, y).
	 * 
	 */
	// Deep-first Search
	public static void flipColorDFS(List<List<Boolean>> A, int x, int y) {
		final int[][] DIRS = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		boolean color = A.get(x).get(y);

		A.get(x).set(y, !color); // flip
		for (int[] dir : DIRS) {
			int nextX = x + dir[0], nextY = y + dir[1];
			if (nextX >= 0 && nextX < A.size() && nextY >= 0 && nextY < A.get(nextX).size()
					&& A.get(nextX).get(nextY) == color) {
				flipColorDFS(A, nextX, nextY);
			}
		}
	}

	// Bread-first Search
	public static void flipColorBFS(List<List<Boolean>> A, int x, int y) {
		final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
		boolean color = A.get(x).get(y);

		Queue<Point> queue = new LinkedList<>();
		A.get(x).set(y, !A.get(x).get(y)); // flip
		queue.add(new Point(x, y));
		while (!queue.isEmpty()) {
			Point curr = queue.element();
			for (int[] dir : DIRS) {
				Point next = new Point(curr.x + dir[0], curr.y + dir[1]);
				if (next.x >= 0 && next.x < A.size() && next.y >= 0 && next.y < A.get(x).size()
						&& A.get(next.x).get(next.y) == color) {
					A.get(next.x).set(next.y, !color);
					queue.add(next);
				}
			}
		}
		queue.remove();
	}

	public static void fillEnclosedRegions(List<List<Character>> board) {
		// starting from first or last columns
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i).get(0) == 'W')
				markBoundaryRegion(i, 0, board);
			if (board.get(i).get(board.get(i).size() - 1) == 'W')
				markBoundaryRegion(i, board.get(i).size() - 1, board);
		}
		// starting from first or last rows
		for (int j = 0; j < board.get(0).size(); j++) {
			if (board.get(0).get(j) == 'W')
				markBoundaryRegion(0, j, board);
			if (board.get(board.size() - 1).get(j) == 'W')
				markBoundaryRegion(board.size() - 1, j, board);
		}
		// marks the enclosed white regions as black
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.size(); j++) {
				board.get(i).set(j, board.get(i).get(j) != 'T' ? 'B' : 'W');
			}
		}
	}

	private static void markBoundaryRegion(int i, int j, List<List<Character>> board) {
		Queue<Point> queue = new LinkedList<>();
		queue.add(new Point(i, j));
		while (!queue.isEmpty()) {
			Point curr = queue.poll();
			if (curr.x >= 0 && curr.x < board.size() && curr.y >= 0 && curr.y < board.get(curr.x).size()
					&& board.get(curr.x).get(curr.y) == 'W') {
				board.get(curr.x).set(curr.y, 'T');
				queue.add(new Point(curr.x - 1, curr.y));
				queue.add(new Point(curr.x + 1, curr.y));
				queue.add(new Point(curr.x, curr.y + 1));
				queue.add(new Point(curr.x, curr.y - 1));
			}
		}
	}

	public static void main(String[] args) {
		List<List<Character>> A = Arrays.asList(Arrays.asList('B', 'B', 'B', 'B'), Arrays.asList('W', 'B', 'W', 'B'),
				Arrays.asList('B', 'W', 'W', 'B'), Arrays.asList('B', 'B', 'B', 'B'));
		fillEnclosedRegions(A);
		List<List<Character>> golden = Arrays.asList(Arrays.asList('B', 'B', 'B', 'B'),
				Arrays.asList('W', 'B', 'B', 'B'), Arrays.asList('B', 'B', 'B', 'B'),
				Arrays.asList('B', 'B', 'B', 'B'));
		assert (A.equals(golden));
	}

}
