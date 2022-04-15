package a00_collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearch {
  // Binary search to squeeze out the largest sum
  public int splitArrayLargestSum(int[] nums, int m) {
    int max = 0;
    long sum = 0;
    for (int num : nums) {
      max = Math.max(max, num);
      sum += num;
    }

    if (m == 1)
      return (int) sum;

    long lo = max, hi = sum;
    while (lo <= hi) {
      long mid = lo + (hi - lo) / 2;
      if (isValidToGroup(mid, nums, m))
        hi = mid - 1;
      else
        lo = mid + 1;
    }

    return (int) lo;
  }

  private boolean isValidToGroup(long target, int[] nums, int m) {
    int count = 1;
    int total = 0;
    for (int num : nums) {
      total += num;
      if (total > target) {
        total = num;
        count++;
        if (count > m)
          return false;
      }
    }
    return true;
  }

  /**
   * Use binary search to simulate TreeMap
   */
  class TimestampMap {
    class Data {
      String value;
      int timestamp;

      Data(String value, int timestamp) {
        this.value = value;
        this.timestamp = timestamp;
      }
    }

    private Map<String, List<Data>> map;

    public TimestampMap() {
      map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
      map.computeIfAbsent(key, k -> new ArrayList<>()).add(new Data(value, timestamp));
    }

    public String get(String key, int timestamp) {
      if (!map.containsKey(key))
        return "";
      return binarySearch(map.get(key), timestamp);
    }

    // Find floor entry!
    private String binarySearch(List<Data> list, int timestamp) {
      int low = 0, high = list.size() - 1;
      while (low < high) {
        int mid = (low + high) >> 1;
        Data data = list.get(mid);
        if (data.timestamp == timestamp)
          return data.value;
        if (data.timestamp < timestamp) {
          if (list.get(mid + 1).timestamp > timestamp)
            return data.value;
          low = mid + 1;
        } else
          high = mid - 1;
      }
      return list.get(low).timestamp <= timestamp ? list.get(low).value : "";
    }
  }

  public static void main(String[] args) {
    BinarySearch solution = new BinarySearch();

    int[] nums = { 7, 2, 5, 10, 8 };
    assertEquals(18, solution.splitArrayLargestSum(nums, 2));
    
    

    TimestampMap timestampMap = solution.new TimestampMap();
    timestampMap.set("foo", "bar", 1);
    assertEquals("bar", timestampMap.get("foo", 1));
    assertEquals("bar", timestampMap.get("foo", 3));
    timestampMap.set("foo", "bar2", 4);
    assertEquals("bar2", timestampMap.get("foo", 4));
    assertEquals("bar2", timestampMap.get("foo", 5));
  }
}
