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
  public List<List<String>> solveNQueens(int n) {
    List<List<String>> boards = new ArrayList<>();
    List<int[]> results = new ArrayList<>();
    // Store column index for each row
    int[] columns = new int[n];
    // Initialize column index as -1
    Arrays.fill(columns, -1);
    placeQueens(0, columns, results);
    for (int[] result : results) {
      boards.add(drawBoard(result));
    }
    return boards;
  }

  public void placeQueens(int row, int[] columns, List<int[]> results) {
    if (row == columns.length) {
      results.add(columns.clone());
      return;
    }
    // Try to place queue at all possible columns
    for (int col = 0; col < columns.length; col++) {
      if (checkValid(columns, row, col)) {
        columns[row] = col; // Place queue
        placeQueens(row + 1, columns, results);
      }
    }
  }

  public boolean checkValid(int[] columns, int row1, int col1) {
    for (int row2 = 0; row2 < row1; row2++) {
      int col2 = columns[row2];
      // Check if rows have a queen in the same column
      if (col1 == col2)
        return false;
      // Check diagonals: means they have same distances.
      int colDistance = Math.abs(col1 - col2);
      int rowDistance = row1 - row2;

      if (colDistance == rowDistance)
        return false;
    }
    return true;
  }

  public List<String> drawBoard(int[] columns) {
    List<String> board = new ArrayList<>();
    for (int i = 0; i < columns.length; i++) {
      char[] row = new char[columns.length];
      Arrays.fill(row, '.');
      for (int j = 0; j < columns.length; j++) {
        if (columns[i] == j) {
          row[j] = 'Q';
        }
      }
      board.add(new String(row));
    }
    return board;
  }

  public static void main(String[] args) {
    NQueensChessBoards solution = new NQueensChessBoards();
    List<List<String>> results = solution.solveNQueens(8);
    System.out.println(results);
    System.out.println(results.size());
  }
}
