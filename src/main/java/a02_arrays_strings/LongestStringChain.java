package a02_arrays_strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestStringChain {
  public int longestStrChain(String[] words) {
    Map<String, Integer> dp = new HashMap<>();

    // Sorting the list in terms of the word length.
    Arrays.sort(words, (a, b) -> a.length() - b.length());

    int longesLength = 1;

    for (String word : words) {
      int presentLength = 1;
      // Find all possible predecessors for the current word by removing one letter at a time.
      for (int i = 0; i < word.length(); i++) {
        StringBuilder temp = new StringBuilder(word);
        temp.deleteCharAt(i);
        String predecessor = temp.toString();
        int previousLength = dp.getOrDefault(predecessor, 0);
        presentLength = Math.max(presentLength, previousLength + 1);
      }
      dp.put(word, presentLength);
      longesLength = Math.max(longesLength, presentLength);
    }
    return longesLength;
  }
}
