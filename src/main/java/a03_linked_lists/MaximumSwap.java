package a03_linked_lists;

public class MaximumSwap {
  public int maximumSwap(int num) {
    char[] chars = String.valueOf(num).toCharArray();

    int left = -1, right = 0;
    for (int i = 1; i < chars.length; i++) {
      // Remember the first turnning point
      if (left == -1 && chars[i] > chars[i - 1]) {
        left = i - 1;
        right = i;
      }
      // Track the highest right digiit
      if (right > 0 && chars[i] >= chars[right]) {
        right = i;
      }
    }

    if (left != -1) {
      // Swap with the first smaller digit!
      for (int i = 0; i <= left; i++) {
        if (chars[i] < chars[right]) {
          char t = chars[i];
          chars[i] = chars[right];
          chars[right] = t;
          return Integer.valueOf(String.valueOf(chars));
        }
      }
    }

    return num;
  }

  public int maximumSwap2(int num) {
    char[] chars = Integer.toString(num).toCharArray();

    // Track the right/last highest
    int[] lastDigits = new int[10];
    for (int i = 0; i < chars.length; i++) {
      lastDigits[chars[i] - '0'] = i;
    }

    for (int i = 0; i < chars.length; i++) {
      int digit = chars[i] - '0';
      // Found the first right number which is bigger
      for (int j = lastDigits.length - 1; j > digit; j--) {
        if (lastDigits[j] > i) {
          int idx = lastDigits[j];
          char t = chars[i];
          chars[i] = chars[idx];
          chars[idx] = t;
          return Integer.parseInt(String.valueOf(chars));
        }
      }
    }

    return num;
  }

  public static void main(String[] args) {
    MaximumSwap solution = new MaximumSwap();
    System.out.println(solution.maximumSwap(115));
  }
}
