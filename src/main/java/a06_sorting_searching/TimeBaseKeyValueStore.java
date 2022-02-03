package a06_sorting_searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 
public class TimeBaseKeyValueStore {

  class TimeMap {
    class Data {
      String value;
      int timestamp;

      Data(String value, int timestamp) {
        this.value = value;
        this.timestamp = timestamp;
      }
    }

    private Map<String, List<Data>> map;

    public TimeMap() {
      map = new HashMap<String, List<Data>>();
    }

    public void set(String key, String value, int timestamp) {
      map.computeIfAbsent(key, k -> new ArrayList<>()).add(new Data(value, timestamp));
    }

    public String get(String key, int timestamp) {
      if (!map.containsKey(key))
        return "";
      return binarySearch(map.get(key), timestamp);
    }

    // find floor entry!
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
}
