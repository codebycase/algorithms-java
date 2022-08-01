package a00_collections;

public class UnionFind3 {
  /**
   * Lexicographically Smallest Equivalent String
   * 
   * You are given two strings of the same length s1 and s2 and a string baseStr.
   * 
   * We say s1[i] and s2[i] are equivalent characters.
   * 
   * For example, if s1 = "abc" and s2 = "cde", then we have 'a' == 'c', 'b' == 'd', and 'c' == 'e'.
   * Equivalent characters follow the usual rules of any equivalence relation:
   * 
   * <pre>
  Reflexivity: 'a' == 'a'.
  Symmetry: 'a' == 'b' implies 'b' == 'a'.
  Transitivity: 'a' == 'b' and 'b' == 'c' implies 'a' == 'c'.
   * </pre>
   * 
   * For example, given the equivalency information from s1 = "abc" and s2 = "cde", "acd" and "aab"
   * are equivalent strings of baseStr = "eed", and "aab" is the lexicographically smallest equivalent
   * string of baseStr.
   * 
   * Return the lexicographically smallest equivalent string of baseStr by using the equivalency
   * information from s1 and s2.
   * 
   * <pre>
  Example 1:
  Input: s1 = "parker", s2 = "morris", baseStr = "parser"
  Output: "makkek"
  Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [m,p], [a,o], [k,r,s], [e,i].
  The characters in each group are equivalent and sorted in lexicographical order.
  So the answer is "makkek".
  
  Example 2:
  Input: s1 = "hello", s2 = "world", baseStr = "hold"
  Output: "hdld"
  Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [h,w], [d,e,o], [l,r].
  So only the second letter 'o' in baseStr is changed to 'd', the answer is "hdld".
  
  Example 3:
  Input: s1 = "leetcode", s2 = "programs", baseStr = "sourcecode"
  Output: "aauaaaaada"
  Explanation: We group the equivalent characters in s1 and s2 as [a,o,e,r,s,c], [l,p], [g,t] and [d,m], thus all letters in baseStr except 'u' and 'd' are transformed to 'a', the answer is "aauaaaaada".
   * </pre>
   */
  public String smallestEquivalentString(String s1, String s2, String baseStr) {
    UnionFindRank uf = new UnionFindRank(26);
    for (int i = 0; i < s1.length(); i++) {
      int u = s1.charAt(i) - 'a';
      int v = s2.charAt(i) - 'a';
      uf.union(u, v);
    }

    char ans[] = new char[baseStr.length()];
    for (int i = 0; i < baseStr.length(); i++) {
      ans[i] = (char) ('a' + uf.find(baseStr.charAt(i) - 'a'));
    }

    return new String(ans);
  }

  class UnionFindRank {
    int[] parent;
    int[] rank;

    UnionFindRank(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = i; // Lexicographical order!
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]);
      }
      return parent[x];
    }

    boolean union(int x, int y) {
      int rX = find(x);
      int rY = find(y);

      if (rX == rY) {
        return false;
      }

      if (rank[rY] < rank[rX]) {
        parent[rX] = rY;
      } else {
        parent[rY] = rX;
      }

      return true;
    }
  }
}
