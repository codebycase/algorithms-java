package a02_arrays_strings;

/**
 * For example, we will insert '#apple', 'e#apple', 'le#apple', 'ple#apple', 'pple#apple',
 * 'apple#apple' into the trie. Then for a query like prefix = "ap", suffix = "le", we can find it
 * by querying our trie for le#ap.
 * 
 * https://leetcode.com/problems/prefix-and-suffix-search/
 * 
 * @author lchen676
 *
 */
public class WordFilter {

  class TrieNode {
    TrieNode[] children;
    int index;

    public TrieNode() {
      children = new TrieNode[27];
      index = 0;
    }
  }

  TrieNode trie;

  public WordFilter(String[] words) {
    trie = new TrieNode();
    for (int index = 0; index < words.length; index++) {
      String word = words[index] + "{";
      for (int i = 0; i < word.length(); i++) {
        TrieNode node = trie;
        for (int j = i; j < 2 * word.length() - 1; j++) {
          int k = word.charAt(j % word.length()) - 'a';
          if (node.children[k] == null) {
            node.children[k] = new TrieNode();
          }
          node = node.children[k];
          // use the largest
          node.index = index;
        }
      }
    }
  }

  public int f(String prefix, String suffix) {
    TrieNode node = trie;
    for (char c : (suffix + '{' + prefix).toCharArray()) {
      node = node.children[c - 'a'];
      if (node == null)
        return -1;
    }
    return node.index;
  }
}
