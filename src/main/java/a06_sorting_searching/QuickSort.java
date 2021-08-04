package a06_sorting_searching;

import java.util.Arrays;

public class QuickSort {
  public static int[] sortArray(int[] nums) {
    quickSort(nums, 0, nums.length - 1);
    return nums;
  }

  public static void swap(int[] array, int i, int j) {
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  public static int partition(int[] array, int left, int right) {
    // Pick a pivot point. Can be an element
    int pivot = array[(left + right) / 2];
    while (left <= right) { // Until we've gone through the whole array
      // Find element on left that should be on right
      while (array[left] < pivot) {
        left++;
      }
      // Find element on right that should be on left
      while (array[right] > pivot) {
        right--;
      }
      // Swap elements, and move left and right indices
      if (left <= right) {
        swap(array, left, right);
        left++;
        right--;
      }
    }
    return left;
  }

  public static void quickSort(int[] array, int left, int right) {
    int index = partition(array, left, right);
    if (left < index - 1) { // Sort left half
      quickSort(array, left, index - 1);
    }
    if (index < right) { // Sort right half
      quickSort(array, index, right);
    }
  }

  public static void main(String[] args) {
    int[] array = new int[] { 1, 4, 5, 2, 8, 9 };
    quickSort(array, 0, array.length - 1);
    System.out.print(Arrays.toString(array));
  }
}
