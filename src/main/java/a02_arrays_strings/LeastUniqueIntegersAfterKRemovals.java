package a02_arrays_strings;

import java.util.Arrays;

public class LeastUniqueIntegersAfterKRemovals {
  public int findLeastNumOfUniqueInts(int[] arr, int k) {
    Arrays.sort(arr);
    // Counting how many times the same frequency has shown!
    int[] freqCount = new int[arr.length + 1];
    int unique = 1, freqency = 1;
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] == arr[i - 1]) {
        freqency++;
      } else {
        freqCount[freqency]++;
        unique++;
        freqency = 1;
      }
    }
    freqCount[freqency]++;

    freqency = 1;
    while (k > 0) {
      if (k >= freqCount[freqency] * freqency) {
        k -= freqCount[freqency] * freqency;
        unique -= freqCount[freqency];
        freqency++;
      } else {
        // Remove partial unique numbers with this frequency.
        unique -= k / freqency;
        break;
      }
    }

    return unique;
  }

  // Memory limit exceeded for large numbers!
  public int findLeastNumOfUniqueInts2(int[] arr, int k) {
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int n : arr) {
      min = Math.min(min, n);
      max = Math.max(max, n);
    }

    int[] numCount = new int[max - min + 1];
    for (int n : arr) {
      numCount[n - min]++;
    }

    int unique = 0;
    int[] freqCount = new int[arr.length + 1];
    for (int c : numCount) {
      if (c > 0) {
        unique++;
        freqCount[c]++;
      }
    }

    int freqency = 1;
    while (k > 0) {
      if (k >= freqCount[freqency] * freqency) {
        k -= freqCount[freqency] * freqency;
        unique -= freqCount[freqency];
        freqency++;
      } else {
        // Remove partial unique numbers with this frequency.
        unique -= k / freqency;
        break;
      }
    }

    return unique;
  }
}
