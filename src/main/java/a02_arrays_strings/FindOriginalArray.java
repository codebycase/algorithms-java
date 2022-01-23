package a02_arrays_strings;

public class FindOriginalArray {
  public int[] findOriginalArray(int[] changed) {
    if (changed.length == 0 || changed.length % 2 != 0)
      return new int[0];

    int[] ans = new int[changed.length / 2];
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < changed.length; i++) {
      max = Math.max(max, changed[i]);
    }

    // bucket sort + frequency
    int[] freq = new int[max + 1];
    for (int i : changed) {
      freq[i]++;
    }

    int idx = 0;
    for (int i = 0; i < freq.length; i++) {
      if (freq[i] == 0)
        continue; // no num

      if (i == 0) {
        // expect even zeros to pair themselves
        if (freq[i] % 2 == 1)
          return new int[0];
        // ans[idx] is zero by default
        idx = idx + freq[i] / 2;
      } else {
        // not enough bigger nums to pair
        if (2 * i >= freq.length || freq[2 * i] < freq[i])
          return new int[0];
        for (int j = 0; j < freq[i]; j++) {
          ans[idx++] = i;
        }
        freq[2 * i] -= freq[i];
      }
    }
    return ans;
  }

}
