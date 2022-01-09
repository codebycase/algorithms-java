package a10_recursion_greedy_invariant;

import java.util.Arrays;
import java.util.LinkedList;

public class SeatingArrangements {
  int minOverallAwkwardness(int[] arr) {
    Arrays.sort(arr);
    int diff = arr[1] - arr[0];

    for (int i = 2; i < arr.length; i += 2) {
      diff = Math.max(diff, arr[i] - arr[i - 2]);
    }
    for (int i = 3; i < arr.length; i += 2) {
      diff = Math.max(diff, arr[i] - arr[i - 2]);
    }

    return Math.max(diff, arr[arr.length - 1] - arr[arr.length - 2]);
  }

  private static int arrange(int nums[]) {
    Arrays.sort(nums);
    int len = nums.length;

    LinkedList<Integer> list = new LinkedList<>();
    for (int i = len - 1; i >= 0; i--) {
      if (i % 2 == 0) {
        list.addLast(nums[i]);
      } else {
        list.addFirst(nums[i]);
      }
    }
    System.out.println(list);
    int max = 0;
    int prev = list.get(len - 1);
    for (int i = 0; i < len; i++) {
      max = Math.max(Math.abs(list.get(i) - prev), max);
      prev = list.get(i);
    }

    return max;
  }

  int minOverallAwkwardness2(int[] arr) {
    int[] res = new int[arr.length];
    boolean left = true;
    int max = Integer.MIN_VALUE;

    Arrays.sort(arr);

    for (int i = 0; i < arr.length; i++) {
      if (left)
        res[i / 2] = arr[i];
      else
        res[arr.length - 1 - i / 2] = arr[i];
      left = !left;
    }

    for (int i = 0; i < res.length - 1; i++) {
      max = Math.max(max, Math.abs(res[i] - res[i + 1]));
    }
    return max;
  }
}
