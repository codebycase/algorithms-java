package a14_java_world_topics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Java8Samples {

	static class Animal {
		String name;

		Animal(String name) {
			this.name = name;
		}

		public static int animalCompare(Animal a1, Animal a2) {
			return a1.name.compareTo(a2.name);
		}

		public String toString() {
			return name;
		}
	}

	// Predicate & Lambda Expression
	public static int count(List<Integer> numList, Predicate<Integer> predicate) {
		int sum = 0;
		for (int number : numList) {
			if (predicate.test(number))
				sum++;
		}
		return sum;
	}

	public static void main(String[] args) {
		Animal[] animalArr = { new Animal("Lion"), new Animal("Crocodile"), new Animal("Tiger"), new Animal("Elephant") };
		Arrays.sort(animalArr, Animal::animalCompare);
		assert Arrays.toString(animalArr).equals("[Crocodile, Elephant, Lion, Tiger]");

		// Predicate
		List<Integer> numList = new ArrayList<>();

		numList.add(new Integer(10));
		numList.add(new Integer(20));
		numList.add(new Integer(30));
		numList.add(new Integer(40));
		numList.add(new Integer(50));

		File[] hiddenFiles = new File(".").listFiles(File::isHidden);
		for (File file : hiddenFiles) {
			System.out.println(file);
		}
		System.out.println(hiddenFiles);

		assert count(numList, n -> true) == 5;
		assert count(numList, n -> false) == 0;
		assert count(numList, n -> n < 25) == 2;
		assert count(numList, n -> n % 3 == 0) == 1;

		// Stream Features
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			list.add(i);
		}

		Stream<Integer> sequentialStream = list.stream();
		Stream<Integer> parallelStream = list.parallelStream();

		Stream<Integer> highNums = parallelStream.filter(a -> a > 90);
		highNums.forEach(p -> System.out.println("High Nums parallel=" + p));
		highNums = sequentialStream.filter(a -> a > 90);
		highNums.forEach(p -> System.out.println("High Nums sequential=" + p));
	}


}
