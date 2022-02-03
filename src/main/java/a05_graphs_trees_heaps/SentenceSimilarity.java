package a05_graphs_trees_heaps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SentenceSimilarity {
  private Map<String, String> parents = new HashMap<>();

  public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
    if (sentence1.length != sentence2.length) {
      return false;
    }
    for (List<String> list : similarPairs) {
      union(list.get(0), list.get(1), parents);
    }
    for (int i = 0; i < sentence1.length; i++) {
      if (!find(sentence1[i]).equals(find(sentence2[i]))) {
        return false;
      }
    }
    return true;
  }

  private void union(String s1, String s2, Map<String, String> roots) {
    String ps1 = find(s1);
    String ps2 = find(s2);
    if (!ps1.equals(ps2)) {
      roots.put(s2, ps1); // halve path
      roots.put(ps2, ps1);
    }
  }

  private String find(String s) {
    while (parents.containsKey(s)) {
      s = parents.get(s);
    }
    return s;
  }

  public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
    if (words1.length != words2.length)
      return false;
    Map<String, List<String>> graph = new HashMap<>();
    for (String[] pair : pairs) {
      for (String word : pair) {
        if (!graph.containsKey(word))
          graph.put(word, new ArrayList<>());
      }
      graph.get(pair[0]).add(pair[1]);
      graph.get(pair[1]).add(pair[0]);
    }
    Queue<String> queue = new ArrayDeque<>();
    Set<String> visited = new HashSet<>();
    for (int i = 0; i < words1.length; i++) {
      queue.clear();
      visited.clear();
      queue.offer(words1[i]);
      visited.add(words1[i]);
      search: {
        while (!queue.isEmpty()) {
          String word = queue.poll();
          if (word.equals(words2[i]))
            break search;
          if (graph.containsKey(word)) {
            for (String nei : graph.get(word)) {
              if (!visited.contains(nei)) {
                queue.offer(nei);
                visited.add(nei);
              }
            }
          }
        }

        return false;
      }
    }
    return true;
  }

  public int numSimilarGroups(String[] A) {
    if (A == null || A.length == 0)
      return 0;
    int count = 0;
    boolean[] visited = new boolean[A.length];
    for (int i = 0; i < A.length; i++) {
      if (!visited[i]) {
        visited[i] = true;
        dfs(A, A[i], visited);
        count++;
      }
    }
    return count;
  }

  private void dfs(String[] A, String str, boolean[] visited) {
    for (int i = 0; i < A.length; i++) {
      if (!visited[i] && isDiffBy2(str, A[i])) {
        visited[i] = true;
        dfs(A, A[i], visited);
      }
    }
  }

  private boolean isDiffBy2(String str1, String str2) {
    if (str1.length() != str2.length())
      return false;
    int diff = 0;
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != str2.charAt(i))
        diff++;
      if (diff > 2)
        return false;
    }
    return true;
  }

  public int kSimilarity(String source, String target) {
    Queue<String> queue = new ArrayDeque<>();
    queue.offer(source);

    Map<String, Integer> distances = new HashMap<>();
    distances.put(source, 0);

    while (!queue.isEmpty()) {
      String word = queue.poll();
      if (word.equals(target))
        return distances.get(word);
      for (String neighbor : neighbors(word, target)) {
        if (!distances.containsKey(neighbor)) {
          distances.put(neighbor, distances.get(word) + 1);
          queue.offer(neighbor);
        }
      }
    }

    return 0;
  }

  public List<String> neighbors(String source, String target) {
    List<String> ans = new ArrayList<>();

    int i = 0;
    while (i < source.length() && source.charAt(i) == target.charAt(i)) {
      i++;
    }

    char[] neighbor = source.toCharArray();
    for (int j = i + 1; j < source.length(); ++j) {
      if (source.charAt(j) == target.charAt(i)) {
        swap(neighbor, i, j);
        ans.add(new String(neighbor));
        swap(neighbor, i, j);
      }
    }

    return ans;
  }

  public void swap(char[] s, int i, int j) {
    char t = s[i];
    s[i] = s[j];
    s[j] = t;
  }
}
