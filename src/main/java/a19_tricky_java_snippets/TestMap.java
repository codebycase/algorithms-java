package a19_tricky_java_snippets;

import java.util.HashMap;
import java.util.Map;

public class TestMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("a", 1);
		map.compute("a", (k, v) -> {
			return v + 1;
		});
		map.computeIfAbsent("a", k -> {
			return 1;
		});
		System.out.println(map);
	}

}
