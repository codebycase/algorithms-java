package a02_arrays_strings;

import java.util.TreeMap;

public class SnapshotArray {
  TreeMap<Integer, Integer>[] maps;
  int snapId = 0;

  @SuppressWarnings("unchecked")
  public SnapshotArray(int length) {
    maps = new TreeMap[length];
    for (int i = 0; i < length; i++) {
      maps[i] = new TreeMap<Integer, Integer>();
      maps[i].put(0, 0);
    }
  }

  public void set(int index, int val) {
    maps[index].put(snapId, val);
  }

  public int snap() {
    return snapId++;
  }

  public int get(int index, int snap_id) {
    return maps[index].floorEntry(snap_id).getValue();
  }
}
