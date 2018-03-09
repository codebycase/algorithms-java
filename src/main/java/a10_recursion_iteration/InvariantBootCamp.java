package a10_recursion_iteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InvariantBootCamp {

	/**
	 * There are N gas stations along a circular route, where the amount of gas at station i is
	 * gas[i].
	 * 
	 * You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from station
	 * i to its next station (i+1). You begin the journey with an empty tank at one of the gas
	 * stations.
	 * 
	 * Return the starting gas station's index if you can travel around the circuit once, otherwise
	 * return -1.
	 */
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		int sumGas = 0, sumCost = 0;
		int start = 0, tank = 0;
		for (int i = 0; i < gas.length; i++) {
			sumGas += gas[i];
			sumCost += cost[i];
			tank += gas[i] - cost[i]; // track tank's gas left!
			if (tank < 0) {
				start = i + 1;
				tank = 0;
			}
		}
		return sumGas < sumCost ? -1 : start;
	}

	public static List<String> searchFrequentItems(Iterable<String> stream, int k) {
		// Finds the candidates which may occur > n / k times
		Map<String, Integer> table = new HashMap<>();
		int count = 0; // Counts the number of items

		Iterator<String> sequence = stream.iterator();
		while (sequence.hasNext()) {
			String item = sequence.next();
			table.put(item, table.getOrDefault(item, 0) + 1);
			count++;
			// Detecting k items in table, at least one of them must have exactly one in it, We will
			// discard those k items by one for each
			if (table.size() == k) {
				List<String> delKeys = new ArrayList<>();
				for (Map.Entry<String, Integer> entry : table.entrySet()) {
					if (entry.getValue() - 1 == 0)
						delKeys.add(entry.getKey());
					else
						table.put(entry.getKey(), entry.getValue() - 1);
				}
				for (String delKey : delKeys) {
					table.remove(delKey);
				}
			}
		}

		// Reset table for the following counting.
		for (String key : table.keySet()) {
			table.put(key, 0);
		}

		// Counts the occurence of each candidate word.
		sequence = stream.iterator();
		while (sequence.hasNext()) {
			String item = sequence.next();
			if (table.containsKey(item)) {
				table.put(item, table.get(item) + 1);
			}
		}

		// Selects the word which occurs > n/k times
		List<String> result = new ArrayList<>();
		for (Map.Entry<String, Integer> it : table.entrySet()) {
			if (count * 1.0 / k < (double) it.getValue()) {
				result.add(it.getKey());
			}
		}

		return result;
	}
}
