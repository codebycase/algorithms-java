package a14_java_world_topics;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class Java8Lambda {

	/**
	 * Behavior parameterization is the ability for a method to take multiple different behaviors as parameters and use them internally to accomplish
	 * different behaviors.
	 */

	// Executing a block of code with Runnable
	public void threadABlockOfCode() {
		Thread thread = new Thread(() -> {
			System.out.println("Hello World!");
		});
		thread.run();
	}

	// Methods and lambdas as first-class citizens
	public void filterAllHiddenFiles() {
		File[] hiddenFiles = new File(".").listFiles(File::isHidden);
		// Arrays.stream(hiddenFiles).forEach((File file) -> System.out.println(file.getPath()));
		Arrays.stream(hiddenFiles).forEach(System.out::println);
	}

	/**
	 * You can use a lambda expression in the context of a functional interface. A functional interface is an interface that specifies exactly one
	 * abstract method. But they can have multiple default methods. Also these interfaces will be annotated as {@code @FunctionalInterface}
	 * 
	 * <br>
	 * 
	 * {@code java.util.Comparator} <br>
	 * {@code java.lang.Runnable} <br>
	 * {@code java.util.function.Consumer} <br>
	 * {@code java.util.function.Function} <br>
	 * {@code java.util.function.Predicate} <br>
	 * {@code java.awt.event.ActionListener} <br>
	 * {@code java.util.concurrent.Callable} <br>
	 * {@code java.security.PriviledgedAction}
	 */
	public void useLambdaFunctions() {
		Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
		stream.map(String::toUpperCase).forEach(System.out::println);
	}

	/**
	 * Boxed values are essentially a wrapper around primitive types and are stored on the heap. Therefore, boxed values use more memory and require
	 * additional memory lookups to fetch the wrapped primitive value.
	 * 
	 * Java8 defines a list of named functional interfaces those use appropriate primitive types directly!
	 * 
	 */
	public void methodComparation() {
		List<String> str = Arrays.asList("a", "b", "A", "B");
		str.sort((a, b) -> a.compareToIgnoreCase(b));
		System.out.println(str);
		str = Arrays.asList("a", "b", "A", "B");
		str.sort(String::compareToIgnoreCase);
		System.out.println(str);
	}

	public void appleSamples() {
		// filtering with lambdas
		List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));
		inventory.stream().filter(a -> "green".equals(a.getColor())).forEach(System.out::println);

		inventory.sort(new Comparator<Apple>() {
			@Override
			public int compare(Apple o1, Apple o2) {
				return o1.getWeight().compareTo(o2.getWeight());
			}
		});

		// Functional Interface
		inventory.sort((a, b) -> (a.getWeight().compareTo(b.getWeight())));
		System.out.println(inventory);

		// Static Assistant Method
		inventory.sort(Comparator.comparing(a -> a.getWeight()));
		System.out.println(inventory);

		// Method Reference
		inventory.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
		System.out.println(inventory);
	}

	public void andThenCompose() {
		Function<Integer, Integer> f = x -> x + 1;
		Function<Integer, Integer> g = x -> x * 2;
		Function<Integer, Integer> h = f.andThen(g);
		System.out.println(f.andThen(g).apply(1));
		System.out.println(f.compose(g).apply(1));
		System.out.println(f.andThen(g).apply(3)); // (3 + 1) * 2 = 8
		System.out.println(f.compose(g).apply(3)); // (3 * 2) + 1 = 7
	}

	public void transformLetters() {
		Function<String, String> addHeader = Letter::addHeader;
		addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter);

	}

	public static class Letter {
		public static String addHeader(String text) {
			return "From Raoul, Mario and Alan: " + text;
		}

		public static String addFooter(String text) {
			return text + " Kind regards";
		}

		public static String checkSpelling(String text) {
			return text.replaceAll("labda", "lambda");
		}
	}

	public static void main(String[] args) {
		Java8Lambda java8Lambda = new Java8Lambda();
		java8Lambda.threadABlockOfCode();
		java8Lambda.filterAllHiddenFiles();
		java8Lambda.useLambdaFunctions();
		java8Lambda.methodComparation();
		java8Lambda.appleSamples();
		java8Lambda.andThenCompose();
	}

}
