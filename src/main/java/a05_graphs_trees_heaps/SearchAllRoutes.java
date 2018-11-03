package a05_graphs_trees_heaps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Complexity is log(V*E)
public class SearchAllRoutes {
	// bidirectional graph
	private Map<String, Set<String>> graph;

	public SearchAllRoutes() {
		this.graph = new LinkedHashMap<>();
	}

	public Map<String, Set<String>> getGraph() {
		return graph;
	}

	public void addRoute(String from, String to) {
		graph.putIfAbsent(from, new LinkedHashSet<>());
		graph.get(from).add(to);
		graph.putIfAbsent(to, new LinkedHashSet<>());
		graph.get(to).add(from);
	}

	public List<List<String>> findAllRoutes(String source, String target) {
		List<List<String>> result = new ArrayList<>();
		if (source == null || target == null)
			return result;

		Set<String> visited = new LinkedHashSet<>();
		List<String> path = new ArrayList<>();
		path.add(source);
		visited.add(source);
		findAllRoutesDfs(source, target, path, visited, result);

		return result;
	}

	private void findAllRoutesDfs(String source, String target, List<String> path, Set<String> visited, List<List<String>> result) {
		if (source.equals(target)) {
			result.add(new ArrayList<>(path));
			return;
		}

		for (String neighbor : graph.get(source)) {
			if (!visited.contains(neighbor)) {
				path.add(neighbor);
				visited.add(neighbor);
				findAllRoutesDfs(neighbor, target, path, visited, result);
				path.remove(path.size() - 1);
				visited.remove(neighbor);
			}
		}
	}

	public static void main(String[] args) {
		SearchAllRoutes solution = new SearchAllRoutes();
		solution.addRoute("A", "B");
		solution.addRoute("A", "C");
		solution.addRoute("B", "C");
		solution.addRoute("B", "D");
		solution.addRoute("C", "D");
		assert solution.findAllRoutes("A", "D").toString().equals("[[A, B, C, D], [A, B, D], [A, C, B, D], [A, C, D]]");
		assert solution.findAllRoutes("A", "A").toString().equals("[[A]]");
	}
}
