package a03_linked_lists;

public class MaximumSwap {
  public int maximumSwap(int num) {
    char[] chars = Integer.toString(num).toCharArray();
    // Track the right/last highest
    int[] digitsMap = new int[10];
    for (int i = 0; i < chars.length; i++) {
      digitsMap[chars[i] - '0'] = i;
    }
    for (int i = 0; i < chars.length; i++) {
      int digit = chars[i] - '0';
      // Found the first right number which is bigger
      for (int j = digitsMap.length - 1; j > digit; j--) {
        if (digitsMap[j] > i) {
          int idx = digitsMap[j];
          char t = chars[i];
          chars[i] = chars[idx];
          chars[idx] = t;
          return Integer.parseInt(String.valueOf(chars));
        }
      }
    }
    return num;
  }
}
