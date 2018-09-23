package a19_tricky_java_snippets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomPickWithBlacklist {
	private Random random;
	private int whitelistLen;
	private Map<Integer, Integer> map;

	public RandomPickWithBlacklist(int N, int[] blacklist) {
		map = new HashMap<>();
		random = new Random();
		whitelistLen = N - blacklist.length;
		Set<Integer> set = new LinkedHashSet<>();
		for (int i = whitelistLen; i < N; i++)
			set.add(i);
		for (int b : blacklist)
			set.remove(b);
		Iterator<Integer> iterator = set.iterator();
		for (int b : blacklist) {
			if (b < whitelistLen)
				map.put(b, iterator.next());
		}
	}

	public int pick() {
		int k = random.nextInt(whitelistLen);
		return map.getOrDefault(k, k);
	}
}
