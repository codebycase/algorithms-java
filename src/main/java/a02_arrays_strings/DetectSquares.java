package a02_arrays_strings;

import java.util.ArrayList;
import java.util.List;

public class DetectSquares {
  private List<int[]> points;
  private int[][] cntPoints;

  public DetectSquares() {
    points = new ArrayList<>();
    cntPoints = new int[1001][1001];
  }

  public void add(int[] point) {
    points.add(point);
    cntPoints[point[0]][point[1]]++;
  }

  public int count(int[] point) {
    int ans = 0;
    int[] p1 = point;
    for (int[] p3 : points) {
      if (p1[0] - p3[0] == 0 || Math.abs(p1[0] - p3[0]) != Math.abs(p1[1] - p3[1])) {
        continue;
      }
      ans += cntPoints[p1[0]][p3[1]] * cntPoints[p3[0]][p1[1]];
    }
    return ans;
  }
}
