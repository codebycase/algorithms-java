package a02_arrays_strings;

public class RangeAddition {
    public int[] getModifiedArray(int length, int[][] updates) {
      int[] result = new int[length];
      for (int[] update : updates) {
        int s = update[0];
        int e = update[1];
        int increment = update[2];
        result[s] += increment;
        if (e < length - 1) {
          result[e + 1] -= increment;
        }
      }
      // prefix sum
      for (int i = 1; i < length; i++) {
        result[i] += result[i - 1];
      }
      return result;
    }
}
