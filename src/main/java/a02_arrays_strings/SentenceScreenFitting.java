package a02_arrays_strings;

public class SentenceScreenFitting {
  public int wordsTyping2(String[] sentence, int rows, int cols) {
    StringBuilder sb = new StringBuilder();
    for (String word : sentence) {
      sb.append(word).append(" ");
    }

    int capacity = 0, length = sb.length();
    for (int i = 0; i < rows; i++) {
      capacity += cols;
      if (sb.charAt(capacity % length) == ' ') {
        capacity++;
      } else {
        while (capacity > 0 && sb.charAt((capacity - 1) % length) != ' ') {
          capacity--;
        }
      }
    }

    return capacity / length;
  }

  public int wordsTyping(String[] sentence, int rows, int cols) {
    StringBuilder s = new StringBuilder();
    for (String word : sentence) {
      s.append(word).append(" ");
    }
    int len = s.length(), count = 0;
    int[] map = new int[len];
    for (int i = 1; i < len; ++i) {
      map[i] = s.charAt(i) == ' ' ? 1 : map[i - 1] - 1;
    }
    for (int i = 0; i < rows; ++i) {
      count += cols;
      count += map[count % len];
    }
    return count / len;
  }
}
