package a08_dynamic_programming;

/**
 * Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper
 * left corner (row1, col1) and lower right corner (row2, col2).
 * 
 * Range Sum Query 2D The above rectangle (with the red border) is defined by (row1, col1) = (2, 1)
 * and (row2, col2) = (4, 3), which contains sum = 8.
 * 
 * <pre>
Example:

Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
 * </pre>
 * 
 * @author lchen
 *
 */
public class RangeSumQuery2D {
	private int[][] dp;

	public RangeSumQuery2D(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0)
			return;
		dp = new int[matrix.length + 1][matrix[0].length + 1];
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				dp[r + 1][c + 1] = dp[r + 1][c] + dp[r][c + 1] + matrix[r][c] - dp[r][c];
			}
		}
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
		return dp[row2 + 1][col2 + 1] - dp[row1][col2 + 1] - dp[row2 + 1][col1] + dp[row1][col1];
	}

	class RangeSumQuery2DII {
		int[][] matrix;
		int[][] colSums;

		public RangeSumQuery2DII(int[][] matrix) {
			if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
				return;
			this.matrix = matrix;
			// add one more row in favor of easy coding
			this.colSums = new int[matrix.length + 1][matrix[0].length];
			for (int r = 1; r < colSums.length; r++) {
				for (int c = 0; c < colSums[0].length; c++) {
					colSums[r][c] = colSums[r - 1][c] + matrix[r - 1][c];
				}
			}
		}

		public void update(int row, int col, int val) {
			// just update the bottom rows with the same col
			for (int r = row + 1; r < colSums.length; r++) {
				colSums[r][col] = colSums[r][col] - matrix[row][col] + val;
			}
			matrix[row][col] = val;
		}

		public int sumRegion(int row1, int col1, int row2, int col2) {
			int sum = 0;
			for (int c = col1; c <= col2; c++) {
				sum += colSums[row2 + 1][c] - colSums[row1][c];
			}
			return sum;
		}
	}
}
