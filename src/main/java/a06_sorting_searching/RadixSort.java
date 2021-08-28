package a06_sorting_searching;

public class RadixSort {
  static void countSort(int[] nums, int exp) {
    int len = nums.length;
    int[] output = new int[len];
    int[] count = new int[10];

    // Store count of occurrences in count[]
    for (int i = 0; i < len; i++)
      count[(nums[i] / exp) % 10]++;

    // Change count[i] so that count[i] now contains actual position of this digit in output[]
    for (int i = 1; i < 10; i++)
      count[i] += count[i - 1];

    // Build the output array
    for (int i = len - 1; i >= 0; i--) {
      output[count[(nums[i] / exp) % 10] - 1] = nums[i];
      count[(nums[i] / exp) % 10]--;
    }

    // Copy the output array to nums, so that nums now contains sorted numbers according to current
    // digit
    for (int i = 0; i < len; i++)
      nums[i] = output[i];
  }

  static void radixSort(int[] nums) {
    int max = 0;
    for (int num : nums) {
      max = Math.max(max, num);
    }
    // Do counting sort for every digit.
    for (int exp = 1; max / exp > 0; exp *= 10)
      countSort(nums, exp);
  }

  public static void main(String[] args) {
    int nums[] = { 170, 45, 75, 90, 802, 24, 2, 66 };
    radixSort(nums);
    for (int i = 0; i < nums.length; i++)
      System.out.print(nums[i] + " ");
  }

}
