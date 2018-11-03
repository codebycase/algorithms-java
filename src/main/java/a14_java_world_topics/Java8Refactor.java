package a14_java_world_topics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Java8Refactor {
	private Logger logger = Logger.getAnonymousLogger();

	interface Task {
		public void execute();
	}

	public static void doSomething(Runnable r) {
		r.run();
	}

	public static void doSomething(Task t) {
		t.execute();
	}

	// Lambda can help to defer the construction of message
	public void log(Level level, Supplier<String> msgSupplier) {
		if (logger.isLoggable(level)) {
			logger.log(level, msgSupplier.get());
		}
	}

	// Execute around
	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
			return p.process(br);
		}
	}

	@FunctionalInterface
	interface BufferedReaderProcessor {
		String process(BufferedReader b) throws IOException;
	}

	// Strategy Pattern
	@FunctionalInterface
	public interface ValidationStrategy {
		boolean execute(String s);
	}

	public static class Validator {
		private final ValidationStrategy strategy;

		public Validator(ValidationStrategy v) {
			this.strategy = v;
		}

		public boolean validate(String s) {
			return strategy.execute(s);
		}
	}

	public static void debugWithPeekLog() {
		List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9);
		List<Integer> result =
				  numbers.stream()
				         .peek(x -> System.out.println("from stream: " + x))
				         .map(x -> x + 17)
				         .peek(x -> System.out.println("after map: " + x))
				         .filter(x -> x % 2 == 0)
				         .peek(x -> System.out.println("after filter: " + x))
				         .limit(3)
				         .peek(x -> System.out.println("after limit: " + x))
				         .collect(Collectors.toList());
		System.out.println(result);
	}

	public static void main(String[] args) throws Exception {

		// anonymous class
		doSomething(new Task() {
			@Override
			public void execute() {
				System.out.println("Danger danger!!");
			}
		});
		// lambda expression
		doSomething((Task) () -> System.out.println("Danger danger!!"));
		// execute around
		// processFile(b -> b.readLine());
		// strategy pattern
		Validator validator = new Validator(s -> s.matches("[a-z]+"));
		System.out.println(validator.validate("aaaaa"));
		// chain of responsiblity
		// Chain of responsibility
		UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
		UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
		Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
		System.out.println(pipeline.apply("Aren't labdas really sexy?!!"));
		debugWithPeekLog();
	}
}
