package a19_tricky_java_snippets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class RandomPickWithWeight {
	List<Integer> psum = new ArrayList<>();
	int tot = 0;
	Random rand = new Random();
	TreeMap<Integer, Integer> map = new TreeMap<>();

	public RandomPickWithWeight(int[] w) {
		for (int x : w) {
			tot += x;
			psum.add(tot);
		}
	}

	public int pickIndex() {
		int targ = rand.nextInt(tot);
		int lo = 0;
		int hi = psum.size() - 1;
		while (lo != hi) {
			int mid = (lo + hi) / 2;
			if (targ >= psum.get(mid))
				lo = mid + 1;
			else
				hi = mid;
		}
		return lo;
	}

	public int pickIndex2() {
		int key = map.ceilingKey(rand.nextInt(tot) + 1);
		// int key= map.heigherKey(rnd.nextInt(cnt));
		return map.get(key);
	}
}
