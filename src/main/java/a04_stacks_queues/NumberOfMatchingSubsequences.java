package a04_stacks_queues;

import java.util.ArrayList;
import java.util.List;

public class NumberOfMatchingSubsequences {
  class Node {
    String word;
    int index;

    public Node(String word, int index) {
      this.word = word;
      this.index = index;
    }
  }

  public int numMatchingSubseq(String s, String[] words) {
    @SuppressWarnings("unchecked")
    List<Node>[] heads = new ArrayList[26];
    // do not use fill here as it's read only
    // Arrays.fill(heads, new ArrayList<Node>());
    for (int i = 0; i < heads.length; i++) {
      heads[i] = new ArrayList<Node>();
    }
    for (String word : words)
      heads[word.charAt(0) - 'a'].add(new Node(word, 0));

    int result = 0;
    for (char c : s.toCharArray()) {
      List<Node> oldBucket = heads[c - 'a'];
      heads[c - 'a'] = new ArrayList<Node>();

      for (Node node : oldBucket) {
        node.index++;
        if (node.index == node.word.length()) {
          result++;
        } else {
          heads[node.word.charAt(node.index) - 'a'].add(node);
        }
      }
      // oldBucket.clear();
    }

    return result;
  }
}
