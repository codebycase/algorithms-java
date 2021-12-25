package a03_linked_lists;

/**
 * 
 * https://leetcode.com/problems/diagonal-traverse/
 */
public class DiagonalTraverse {

  // Simulate upwards and downwards
  public int[] findDiagonalOrder(int[][] matrix) {
    if (matrix == null || matrix.length == 0)
      return null;

    int rows = matrix.length, cols = matrix[0].length;
    int total = rows * cols, count = 0;
    int[] result = new int[total];

    int row = 0, col = 0;
    boolean upwards = true;
    while (count < total) {
      result[count++] = matrix[row][col];
      if (upwards) {
        if (row == 0 || col == cols - 1) {
          upwards = false;
          if (col + 1 <= cols - 1) {
            col++;
          } else {
            row++;
          }
          continue;
        }
        row--;
        col++;
      } else {
        if (row == rows - 1 || col == 0) {
          upwards = true;
          if (row + 1 <= rows - 1) {
            row++;
          } else {
            col++;
          }
          continue;
        }
        row++;
        col--;
      }
    }

    return result;
  }
}
