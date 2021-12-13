package a06_sorting_searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSugguestionsSystem {
  class Solution {
    List<List<String>> suggestedProducts(String[] products, String searchWord) {
      Trie trie = new Trie();
      List<List<String>> result = new ArrayList<>();

      for (String w : products)
        trie.insert(w);

      String prefix = new String();
      for (char c : searchWord.toCharArray()) {
        prefix += c;
        result.add(trie.getWordsStartingWith(prefix));
      }
      return result;
    }

    class Trie {
      class Node {
        boolean isWord = false;
        Node[] children = new Node[26];
      };

      Node root;
      List<String> result;

      void dfsWithPrefix(Node curr, String word) {
        if (result.size() == 3)
          return;
        if (curr.isWord)
          result.add(word);

        // Look into children nodes
        for (char c = 'a'; c <= 'z'; c++)
          if (curr.children[c - 'a'] != null)
            dfsWithPrefix(curr.children[c - 'a'], word + c);
      }

      Trie() {
        root = new Node();
      }

      void insert(String s) {
        Node curr = root;
        for (char c : s.toCharArray()) {
          if (curr.children[c - 'a'] == null)
            curr.children[c - 'a'] = new Node();
          curr = curr.children[c - 'a'];
        }
        curr.isWord = true;
      }

      public List<String> getWordsStartingWith(String prefix) {
        Node curr = root;
        result = new ArrayList<String>();
        for (char c : prefix.toCharArray()) {
          if (curr.children[c - 'a'] == null)
            return result;
          curr = curr.children[c - 'a'];
        }
        dfsWithPrefix(curr, prefix);
        return result;
      }
    };
  };

  // Second solution
  private int left;
  private int right;

  // Squeez into middle letter by letter!
  private List<String> findMatchingWords(String[] products, String searchWord, int index) {
    List<String> result = new ArrayList<>();
    char letter = searchWord.charAt(index);
    while (left <= right && (products[left].length() <= index || products[left].charAt(index) != letter)) {
      left++;
    }
    while (left <= right && (products[right].length() <= index || products[right].charAt(index) != letter)) {
      right--;
    }
    for (int i = left; i <= right && i < left + 3; i++) {
      result.add(products[i]);
    }
    return result;
  }

  public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    Arrays.sort(products);
    left = 0;
    right = products.length - 1;
    List<List<String>> result = new ArrayList<>();
    if (products == null || products.length == 0 || searchWord == null || searchWord.isEmpty()) {
      return result;
    }
    for (int i = 0; i < searchWord.length(); i++) {
      List<String> res = new ArrayList<>();
      res = findMatchingWords(products, searchWord, i);
      result.add(res);
    }
    return result;
  }
}
