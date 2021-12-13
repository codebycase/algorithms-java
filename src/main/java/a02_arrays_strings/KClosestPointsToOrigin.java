package a02_arrays_strings;

import java.util.Arrays;

public class KClosestPointsToOrigin {
  public int[][] kClosest(int[][] points, int k) {
    return quickSort(points, k);
  }

  private int[][] quickSort(int[][] points, int k) {
    int left = 0, right = points.length - 1;
    int pivotIndex = points.length;

    while (pivotIndex != k) {
      // Repeatedly partition the array while narrowing in on the kth element
      pivotIndex = partition(points, left, right);
      if (pivotIndex < k) {
        left = pivotIndex;
      } else {
        right = pivotIndex - 1;
      }
    }

    // Return the first k elements of the partially sorted array
    return Arrays.copyOf(points, k);
  }

  private int partition(int[][] points, int left, int right) {
    int[] pivot = choosePivot(points, left, right);
    int pivotDist = squaredDistance(pivot);
    while (left < right) {
      // Iterate through the range and swap elements to make sure
      // that all points closer than the pivot are to the left
      if (squaredDistance(points[left]) >= pivotDist) {
        int[] temp = points[left];
        points[left] = points[right];
        points[right] = temp;
        right--;
      } else {
        left++;
      }
    }
    
    long mod = 10^9 + 7;

    // Ensure the left pointer is just past the end of
    // the left range then return it as the new pivotIndex
    if (squaredDistance(points[left]) < pivotDist)
      left++;
    return left;
  }

  private int[] choosePivot(int[][] points, int left, int right) {
    // Choose a pivot element of the array
    return points[left + (right - left) / 2];
  }

  private int squaredDistance(int[] point) {
    // Calculate and return the squared Euclidean distance
    return point[0] * point[0] + point[1] * point[1];
  }
}
