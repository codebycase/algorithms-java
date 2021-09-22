package a08_dynamic_programming;

/**
 * Given a m x n matrix mat and an integer k, return a matrix answer where each answer[i][j] is the
 * sum of all elements mat[r][c] for:
 * 
 * i - k <= r <= i + k, j - k <= c <= j + k, and (r, c) is a valid position in the matrix.
 * 
 * 
 * Example 1:
 * 
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 1 Output: [[12,21,16],[27,45,33],[24,39,28]]
 * 
 * Example 2:
 * 
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], k = 2 Output: [[45,45,45],[45,45,45],[45,45,45]]
 * 
 * Solution:
 * 
 * For each row, use a sliding window of size 2 * k to keep the sum updated.
 * 
 * After finishing each row, do it for each columns.
 * 
 * <pre>
 * 1  2  3
 * 4  5  6
 * 7  8  9

 * 3  6  5
 * 9  15 11
 * 15 24 17

 * 12 21 16
 * 27 45 33
 * 24 39 28
 * </pre>
 */
public class MatrixBlockSum {
  public int[][] matrixBlockSum(int[][] mat, int k) {
    int m = mat.length, n = mat[0].length;
    int[][] tmp = new int[m][n];
    int[][] ans = new int[m][n];

    for (int i = 0; i < m; i++) {
      int sum = 0;
      for (int j = 0; j < n + k; j++) {
        // minus left num
        if (j > 2 * k) {
          sum -= mat[i][j - 2 * k - 1];
        }
        // add right num
        if (j < n) {
          sum += mat[i][j];
        }
        // cach the sum
        if (j >= k) {
          tmp[i][j - k] = sum;
        }
      }
    }

    for (int j = 0; j < n; j++) {
      int sum = 0;
      for (int i = 0; i < m + k; i++) {
        if (i > 2 * k) {
          sum -= tmp[i - 2 * k - 1][j];
        }
        if (i < m) {
          sum += tmp[i][j];
        }
        if (i >= k) {
          ans[i - k][j] = sum;
        }
      }
    }

    return ans;
  }
}
