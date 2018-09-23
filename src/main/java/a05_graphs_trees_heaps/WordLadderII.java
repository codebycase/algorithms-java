package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordLadderII {
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
		List<List<String>> ladders = new ArrayList<>();
		Set<String> wordSet = new HashSet<>(wordList);
		// confirm if word list must contain end word!
		if (!wordSet.contains(endWord))
			return ladders;

		Map<String, List<String>> beginMap = new HashMap<>();
		Map<String, List<String>> endMap = new HashMap<>();
		beginMap.put(beginWord, new LinkedList<>(Arrays.asList(beginWord)));
		endMap.put(endWord, new LinkedList<>(Arrays.asList(endWord)));
		wordSet.remove(endWord);

		boolean isForward = true;
		while (!beginMap.isEmpty() && !endMap.isEmpty()) {
			// always choose the smaller end
			if (beginMap.size() > endMap.size()) {
				Map<String, List<String>> temp = beginMap;
				beginMap = endMap;
				endMap = temp;
				isForward = !isForward;
			}

			boolean found = false;
			Map<String, List<String>> newMap = new HashMap<>();
			for (Map.Entry<String, List<String>> entry : beginMap.entrySet()) {
				char[] chrs = entry.getKey().toCharArray();
				for (int i = 0; i < chrs.length; i++) {
					char temp = chrs[i];
					for (char c = 'a'; c <= 'z'; c++) {
						if (temp == c)
							continue;
						chrs[i] = c;
						String target = String.valueOf(chrs);
						if (wordSet.contains(target)) {
							List<String> list = new LinkedList<>(entry.getValue());
							list.add(isForward ? list.size() : 0, target);
							newMap.put(target, list);
							wordSet.remove(target);
						}
						if (endMap.containsKey(target)) {
							List<String> list = new LinkedList<>();
							list.addAll(isForward ? entry.getValue() : endMap.get(target));
							list.addAll(isForward ? endMap.get(target) : entry.getValue());
							ladders.add(list);
							found = true;
						}
					}
					chrs[i] = temp;
				}
			}
			beginMap = newMap;
			if (found)
				break;
		}

		return ladders;
	}

	public List<List<String>> findLadders2(String start, String end, List<String> wordList) {
		List<List<String>> ladders = new ArrayList<>();

		Set<String> wordSet = new HashSet<>(wordList);
		if (!wordSet.contains(end))
			return ladders;

		Map<String, List<String>> graph = new HashMap<>(wordList.size());

		Set<String> beginSet = new HashSet<>();
		beginSet.add(start);

		Set<String> endSet = new HashSet<>();
		endSet.add(end);

		if (!helper(wordSet, beginSet, endSet, graph, true))
			return ladders;

		List<String> list = new ArrayList<>();
		list.add(start);

		generateLadder(start, end, graph, list, ladders);

		return ladders;
	}

	private boolean helper(Set<String> wordSet, Set<String> beginSet, Set<String> endSet,
			Map<String, List<String>> graph, boolean isForward) {
		if (beginSet.isEmpty() || endSet.isEmpty())
			return false;
		boolean found = false;
		wordSet.removeAll(beginSet);
		Set<String> newSet = new HashSet<>();
		for (String word : beginSet) {
			char[] chrs = word.toCharArray();
			for (int i = 0; i < chrs.length; i++) {
				char temp = chrs[i];
				for (char c = 'a'; c <= 'z'; c++) {
					if (temp == c)
						continue;
					chrs[i] = c;
					String target = new String(chrs);
					if (!wordSet.contains(target))
						continue;
					newSet.add(target);
					String key = isForward ? word : target;
					String value = isForward ? target : word;
					if (!graph.containsKey(key))
						graph.put(key, new ArrayList<>());
					graph.get(key).add(value);
					if (endSet.contains(target))
						found = true;
				}
				chrs[i] = temp;
			}
		}
		if (found)
			return true;
		if (newSet.size() > endSet.size())
			return helper(wordSet, endSet, newSet, graph, !isForward);
		return helper(wordSet, newSet, endSet, graph, isForward);
	}

	private void generateLadder(String beginWord, String endWord, Map<String, List<String>> graph, List<String> list,
			List<List<String>> result) {
		if (beginWord.equals(endWord)) {
			result.add(new ArrayList<>(list));
			return;
		}
		if (!graph.containsKey(beginWord))
			return;
		for (String word : graph.get(beginWord)) {
			list.add(word);
			generateLadder(word, endWord, graph, list, result);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String[] args) {
		WordLadderII solution = new WordLadderII();
		List<String> words = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
		List<List<String>> ladders = solution.findLadders2("hit", "cog", words);
		System.out.println(ladders);
		System.out.println(solution.findLadders("hit", "cog", words));
		words = Arrays.asList("flail", "halon", "lexus", "joint", "pears", "slabs", "lorie", "lapse", "wroth", "yalow",
				"swear", "cavil", "piety", "yogis", "dhaka", "laxer", "tatum", "provo", "truss", "tends", "deana",
				"dried", "hutch", "basho", "flyby", "miler", "fries", "floes", "lingo", "wider", "scary", "marks",
				"perry", "igloo", "melts", "lanny", "satan", "foamy", "perks", "denim", "plugs", "cloak", "cyril",
				"women", "issue", "rocky", "marry", "trash", "merry", "topic", "hicks", "dicky", "prado", "casio",
				"lapel", "diane", "serer", "paige", "parry", "elope", "balds", "dated", "copra", "earth", "marty",
				"slake", "balms", "daryl", "loves", "civet", "sweat", "daley", "touch", "maria", "dacca", "muggy",
				"chore", "felix", "ogled", "acids", "terse", "cults", "darla", "snubs", "boats", "recta", "cohan",
				"purse", "joist", "grosz", "sheri", "steam", "manic", "luisa", "gluts", "spits", "boxer", "abner",
				"cooke", "scowl", "kenya", "hasps", "roger", "edwin", "black", "terns", "folks", "demur", "dingo",
				"party", "brian", "numbs", "forgo", "gunny", "waled", "bucks", "titan", "ruffs", "pizza", "ravel",
				"poole", "suits", "stoic", "segre", "white", "lemur", "belts", "scums", "parks", "gusts", "ozark",
				"umped", "heard", "lorna", "emile", "orbit", "onset", "cruet", "amiss", "fumed", "gelds", "italy",
				"rakes", "loxed", "kilts", "mania", "tombs", "gaped", "merge", "molar", "smith", "tangs", "misty",
				"wefts", "yawns", "smile", "scuff", "width", "paris", "coded", "sodom", "shits", "benny", "pudgy",
				"mayer", "peary", "curve", "tulsa", "ramos", "thick", "dogie", "gourd", "strop", "ahmad", "clove",
				"tract", "calyx", "maris", "wants", "lipid", "pearl", "maybe", "banjo", "south", "blend", "diana",
				"lanai", "waged", "shari", "magic", "duchy", "decca", "wried", "maine", "nutty", "turns", "satyr",
				"holds", "finks", "twits", "peaks", "teems", "peace", "melon", "czars", "robby", "tabby", "shove",
				"minty", "marta", "dregs", "lacks", "casts", "aruba", "stall", "nurse", "jewry", "knuth");
		List<List<String>> ladders1 = solution.findLadders("magic", "pearl", words);
		List<List<String>> ladders2 = solution.findLadders2("magic", "pearl", words);
		for (List<String> ladder2 : ladders2) {
			if (ladders1.contains(ladder2)) {
				System.out.println(ladder2);
			}
			if (!ladders1.contains(ladder2)) {
				System.out.println();
				System.out.println(ladder2);
			}
		}

	}
}
