package a05_graphs_trees_heaps;

/**
 * In an alien language, surprisingly, they also use English lowercase letters, but possibly in a
 * different order. The order of the alphabet is some permutation of lowercase letters.
 * 
 * Given a sequence of words written in the alien language, and the order of the alphabet, return
 * true if and only if the given words are sorted lexicographically in this alien language.
 *
 * <pre>
 * Example 1:
 * 
 * Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
 * Output: true
 * Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
 * </pre>
 */
public class VerifyAlienDictionary {
  public boolean isAlienSorted(String[] words, String order) {
    int[] orderMap = new int[26];
    for (int i = 0; i < order.length(); i++) {
      orderMap[order.charAt(i) - 'a'] = i;
    }

    for (int i = 0; i < words.length - 1; i++) {
      for (int j = 0; j < words[i].length(); j++) {
        // Handle the case like ("apple", "app").
        if (j >= words[i + 1].length()) {
          return false;
        }
        if (words[i].charAt(j) != words[i + 1].charAt(j)) {
          int curr = words[i].charAt(j) - 'a';
          int next = words[i + 1].charAt(j) - 'a';
          if (orderMap[curr] > orderMap[next]) {
            return false;
          } else {
            break; // Skip rest!
          }
        }
      }
    }

    return true;
  }
}
