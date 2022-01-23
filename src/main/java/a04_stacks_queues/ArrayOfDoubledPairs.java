package a04_stacks_queues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayOfDoubledPairs {
  public boolean canReorderDoubled(int[] arr) {

    Map<Integer, Integer> map = new HashMap<>();

    for (int x : arr) {
      map.put(x, map.getOrDefault(x, 0) + 1);
    }
    List<Integer> keys = new ArrayList<>(map.keySet());

    Collections.sort(keys, Comparator.comparing(x -> Math.abs(x)));

    for (int k : keys) {
      int x = map.get(k);
      if (x == 0)
        continue;
      if (x > map.getOrDefault(k + k, 0))
        return false;
      map.put(k + k, map.get(k + k) - x);
    }
    
    return true;
  }
  
  
}
