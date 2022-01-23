package a06_sorting_searching;

import java.util.Arrays;

public class KClosestPointsToOrigin {
  public int[][] kClosest(int[][] points, int k) {
    int left = 0, right = points.length - 1;
    int pivotIndex = points.length;

    while (pivotIndex != k) {
      pivotIndex = partition(points, left, right);
      if (pivotIndex < k) {
        left = pivotIndex;
      } else {
        right = pivotIndex - 1;
      }
    }

    return Arrays.copyOf(points, k);
  }

  private int partition(int[][] points, int left, int right) {
    int[] pivot = points[left + (right - left) / 2];
    int pivotDist = squaredDistance(pivot);
    // equals can make sure the left pointer just past the end of left range
    while (left <= right) {
      while (squaredDistance(points[left]) < pivotDist) {
        left++;
      }
      while (squaredDistance(points[right]) > pivotDist) {
        right--;
      }
      if (left <= right) {
        int[] temp = points[left];
        points[left] = points[right];
        points[right] = temp;
        left++;
        right--;
      }
    }

    return left;
  }

  private int squaredDistance(int[] point) {
    return point[0] * point[0] + point[1] * point[1];
  }
}
