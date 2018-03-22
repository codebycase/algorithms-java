package a18_the_honors_question;

import java.util.Arrays;

public class PositionsAttackedByRooks {
	public static void positionsAttackedByRooks(int[][] grid) {
		int m = grid.length, n = grid[0].length;

		boolean hasFirstRowZero = false;
		for (int j = 0; j < n; j++) {
			if (grid[0][j] == 0) {
				hasFirstRowZero = true;
				break;
			}
		}

		boolean hasFirstColZero = false;
		for (int i = 0; i < m; i++) {
			if (grid[i][0] == 0) {
				hasFirstColZero = true;
				break;
			}
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (grid[i][j] == 0) {
					grid[i][0] = 0;
					grid[0][j] = 0;
				}
			}
		}

		for (int i = 1; i < m; i++) {
			if (grid[i][0] == 0)
				Arrays.fill(grid[i], 0);
		}

		for (int j = 1; j < n; j++) {
			if (grid[0][j] == 0) {
				for (int i = 1; i < m; i++) {
					grid[i][j] = 0;
				}
			}
		}

		if (hasFirstRowZero)
			Arrays.fill(grid[0], 0);

		if (hasFirstColZero) {
			for (int i = 0; i < m; i++) {
				grid[i][0] = 0;
			}
		}
	}
}
