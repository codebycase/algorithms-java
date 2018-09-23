package a02_arrays_strings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TwoSum {
	Map<Integer, Integer> map = new HashMap<Integer, Integer>();

	// Add the number to an internal data structure.
	public void add(int number) {
		if (map.containsKey(number)) {
			map.put(number, map.get(number) + 1);
		} else {
			map.put(number, 1);
		}
	}

	// Find if there exists any pair of numbers which sum is equal to the value.
	public boolean find(int value) {
		Iterator<Integer> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			int num1 = iter.next();
			int num2 = value - num1;
			if (map.containsKey(num2)) {
				if (num1 != num2 || map.get(num2) >= 2) {
					return true;
				}
			}
		}
		return false;
	}
}
