package a14_java_world_topics;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static a14_java_world_topics.Dish.menu;
import static a14_java_world_topics.Dish.CaloricLevel;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;

public class Java8Collector {

	private static Map<Dish.Type, List<Dish>> groupDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType));
	}

	private static Map<Dish.Type, List<String>> groupDishNamesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
	}

	private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toSet())));
	}

	private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
		return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
	}

	private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
		return menu.stream().collect(groupingBy(dish -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}));
	}

	private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
		return menu.stream().collect(groupingBy(Dish::getType, groupingBy((Dish dish) -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		})));
	}

	private static Map<Dish.Type, Long> countDishesInGroups() {
		return menu.stream().collect(groupingBy(Dish::getType, counting()));
	}

	private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
	}

	private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
		return menu.stream().collect(
				groupingBy(Dish::getType, collectingAndThen(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2), Optional::get)));
	}

	private static Map<Dish.Type, Integer> sumCaloriesByType() {
		return menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
	}

	private static Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType() {
		return menu.stream().collect(groupingBy(Dish::getType, mapping(dish -> {
			if (dish.getCalories() <= 400)
				return CaloricLevel.DIET;
			else if (dish.getCalories() <= 700)
				return CaloricLevel.NORMAL;
			else
				return CaloricLevel.FAT;
		}, toSet())));
	}

	private static Map<Boolean, List<Dish>> partitionByVegeterian() {
		return menu.stream().collect(partitioningBy(Dish::isVegetarian));
	}

	private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
		return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
	}

	private static Object mostCaloricPartitionedByVegetarian() {
		return menu.stream().collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
	}

	private static int calculateTotalCaloriesUsingSum() {
		return menu.stream().mapToInt(Dish::getCalories).sum();
	}

	private static long howManyDishes() {
		return menu.stream().collect(counting());
	}

	private static Dish findMostCaloricDish() {
		return menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
	}

	private static Dish findMostCaloricDishUsingComparator() {
		Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
		BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
		return menu.stream().collect(reducing(moreCaloricOf)).get();
	}

	private static int calculateTotalCalories() {
		return menu.stream().collect(summingInt(Dish::getCalories));
	}

	private static Double calculateAverageCalories() {
		return menu.stream().collect(averagingInt(Dish::getCalories));
	}

	private static IntSummaryStatistics calculateMenuStatistics() {
		return menu.stream().collect(summarizingInt(Dish::getCalories));
	}

	private static String getShortMenu() {
		return menu.stream().map(Dish::getName).collect(joining());
	}

	private static String getShortMenuCommaSeparated() {
		return menu.stream().map(Dish::getName).collect(joining(", "));
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
	}

}
