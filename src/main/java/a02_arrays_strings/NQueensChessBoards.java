package a02_arrays_strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two
 * queens attack each other.
 * 
 * @author lchen
 *
 */
public class NQueensChessBoards {
	public static int GRID_SIZE = 8;

	/* Check if (row1, column1) is a valid spot for a queen by checking if there
	 * is a queen in the same column or diagonal. We don't need to check it for queens
	 * in the same row because the calling placeQueen only attempts to place one queen at
	 * a time. We know this row is empty. 
	 */
	public static boolean checkValid(int[] columns, int row1, int column1) {
		for (int row2 = 0; row2 < row1; row2++) {
			if (columns[row2] != -1) {
				int column2 = columns[row2];
				/* Check if (row2, column2) invalidates (row1, column1) as a queen spot. */

				/* Check if rows have a queen in the same column */
				if (column1 == column2) {
					return false;
				}

				/* Check diagonals: if the distance between the columns equals the distance
				 * between the rows, then they’re in the same diagonal. */
				int columnDistance = Math.abs(column2 - column1);
				int rowDistance = row1 - row2; // row1 > row2, so no need to use absolute value
				if (columnDistance == rowDistance) {
					return false;
				}
			}
		}
		return true;
	}

	public static void placeQueens(int row, int[] columns, List<int[]> results) {
		if (row == GRID_SIZE) { // Found valid placement
			results.add(columns.clone());
		} else {
			for (int col = 0; col < GRID_SIZE; col++) {
				if (checkValid(columns, row, col)) {
					columns[row] = col; // Place queen
					// Why don't we need to clone columns here?!
					placeQueens(row + 1, columns, results);
				}
			}
		}
	}

	public static void clear(int[] columns) {
		for (int i = 0; i < GRID_SIZE; i++) {
			columns[i] = -1;
		}
	}

	public static void printBoard(int[] columns) {
		drawLine();
		for (int i = 0; i < GRID_SIZE; i++) {
			System.out.print("|");
			for (int j = 0; j < GRID_SIZE; j++) {
				if (columns[i] == j) {
					System.out.print("Q|");
				} else {
					System.out.print(" |");
				}
			}
			System.out.print("\n");
			drawLine();
		}
		System.out.println("");
	}

	private static void drawLine() {
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < GRID_SIZE * 2 + 1; i++)
			line.append('-');
		System.out.println(line.toString());
	}

	public static void printBoards(ArrayList<int[]> boards) {
		for (int i = 0; i < boards.size(); i++) {
			int[] board = boards.get(i);
			printBoard(board);
		}
	}

	public static void main(String[] args) {
		ArrayList<int[]> results = new ArrayList<int[]>();
		int[] columns = new int[GRID_SIZE];
		Arrays.fill(columns, -1);
		placeQueens(0, columns, results);
		printBoards(results);
		System.out.println(results.size());
	}

}
