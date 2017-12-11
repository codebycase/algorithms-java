package a06_sorting_searching;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PartitionArray {
	private static class Person {
		public Integer age;
		public String name;

		public Person(Integer k, String n) {
			age = k;
			name = n;
		}

		public String toString() {
			return age + ":" + name;
		}
	}

	public static void groupByAge(List<Person> people) {
		Map<Integer, Integer> ageToCount = new TreeMap<>();
		for (Person p : people) {
			if (ageToCount.containsKey(p.age)) {
				ageToCount.put(p.age, ageToCount.get(p.age) + 1);
			} else {
				ageToCount.put(p.age, 1);
			}
		}
		Map<Integer, Integer> ageToOffset = new HashMap<>();
		int offset = 0;
		for (Map.Entry<Integer, Integer> kc : ageToCount.entrySet()) {
			ageToOffset.put(kc.getKey(), offset);
			offset += kc.getValue();
		}

		while (!ageToOffset.isEmpty()) {
			System.out.println(people);
			Map.Entry<Integer, Integer> from = ageToOffset.entrySet().iterator().next();
			Integer toAge = people.get(from.getValue()).age;
			Integer toValue = ageToOffset.get(toAge);
			Collections.swap(people, from.getValue(), toValue);
			// Use ageToCount to see when we are finished with a particular age.
			Integer count = ageToCount.get(toAge) - 1;
			ageToCount.put(toAge, count);
			if (count > 0) {
				ageToOffset.put(toAge, toValue + 1);
			} else {
				ageToOffset.remove(toAge);
			}
		}
	}

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(new Person(30, "tom"), new Person(20, "foo"), new Person(10, "bar"),
				new Person(20, "widget"), new Person(20, "something"));
		groupByAge(people);
	}
}
