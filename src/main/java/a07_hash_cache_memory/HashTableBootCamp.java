package a07_hash_cache_memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashTableBootCamp {
	/**
	 * <pre>
	Given an array of strings, group anagrams together.
	
	For example, given: ["eat", "tea", "tan", "ate", "nat", "bat"], 
	Return:
	
	[
	  ["ate", "eat","tea"],
	  ["nat","tan"],
	  ["bat"]
	]
	Note: All inputs will be in lower-case.
	 * </pre>
	 */
	public List<List<String>> groupAnagrams(String[] strs) {
		int[] prime = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
				103 };

		List<List<String>> result = new ArrayList<>();
		if (strs == null || strs.length == 0)
			return result;

		Map<Integer, List<String>> map = new HashMap<>();
		for (String s : strs) {
			int key = 1;
			for (char c : s.toCharArray()) {
				key *= prime[c - 'a'];
			}
			if (!map.containsKey(key))
				map.put(key, new ArrayList<>());
			map.get(key).add(s);
		}
		result.addAll(map.values());

		return result;
	}

	public static void main(String[] args) {
		HashTableBootCamp bootCamp = new HashTableBootCamp();
		String[] strs = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };
		assert bootCamp.groupAnagrams(strs).toString().equals("[[eat, tea, ate], [bat], [tan, nat]]");
	}
}
