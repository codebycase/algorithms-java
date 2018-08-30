package a05_graphs_trees_heaps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * There is a new alien language which uses the latin alphabet. However, the order among letters are
 * unknown to you. You receive a list of non-empty words from the dictionary, where words are sorted
 * lexicographically by the rules of this new language. Derive the order of letters in this
 * language.
 * 
 * <pre>
Example 1:

Input:
[
  "wrt",
  "wrf",
  "er",
  "ett",
  "rftt"
]

Output: "wertf"
 * </pre>
 * 
 * @author lchen
 *
 */
public class AlienDictionary {
	public String alienOrder(String[] words) {
		Map<Character, Set<Character>> map = new HashMap<>();
		Map<Character, Integer> degree = new HashMap<>();
		StringBuilder result = new StringBuilder();

		for (String word : words) {
			for (char c : word.toCharArray()) {
				degree.put(c, 0);
			}
		}

		for (int i = 0; i < words.length - 1; i++) {
			String curr = words[i];
			String next = words[i + 1];
			for (int j = 0; j < Math.min(curr.length(), next.length()); j++) {
				char c1 = curr.charAt(j);
				char c2 = next.charAt(j);
				if (c1 != c2) {
					if (!map.containsKey(c1))
						map.put(c1, new HashSet<>());
					Set<Character> set = map.get(c1);
					if (!set.contains(c2)) {
						set.add(c2);
						degree.put(c2, degree.getOrDefault(c2, 0) + 1);
					}
					break;
				}
			}
		}

		Queue<Character> queue = new LinkedList<>();
		for (Map.Entry<Character, Integer> entry : degree.entrySet()) {
			if (entry.getValue() == 0)
				queue.add(entry.getKey());
		}
		while (!queue.isEmpty()) {
			char c = queue.remove();
			result.append(c);
			if (map.containsKey(c)) {
				for (char c2 : map.get(c)) {
					degree.put(c2, degree.get(c2) - 1);
					if (degree.get(c2) == 0)
						queue.add(c2);
				}
			}
		}
		if (result.length() != degree.size())
			result.setLength(0);

		return result.toString();
	}
}
