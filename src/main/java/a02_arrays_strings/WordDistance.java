package a02_arrays_strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDistance {
	Map<String, List<Integer>> map;

	public WordDistance(String[] words) {
		map = new HashMap<String, List<Integer>>();
		for (int i = 0; i < words.length; i++) {
			List<Integer> list = map.getOrDefault(words[i], new ArrayList<Integer>());
			list.add(i);
			map.put(words[i], list);
		}
	}

	public int shortest(String word1, String word2) {
		List<Integer> list1 = map.getOrDefault(word1, new ArrayList<>());
		List<Integer> list2 = map.getOrDefault(word2, new ArrayList<>());
		int i = 0, j = 0;
		int shortest = Integer.MAX_VALUE;
		while (i < list1.size() && j < list2.size()) {
			int a = list1.get(i);
			int b = list2.get(j);
			shortest = Math.min(shortest, Math.abs(b - a));
			if (a < b)
				i++;
			else if (a > b)
				j++;
		}
		return shortest;
	}
}
