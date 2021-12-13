package a02_arrays_strings;

public class PairOfSongs {
  public int numPairsDivisibleBy60(int[] times) {
    int seconds = 60;
    int remainders[] = new int[seconds];
    int count = 0, mod = 0;
    for (int time : times) {
      mod = time % seconds;
      count += remainders[mod == 0 ? 0 : seconds - mod];
      remainders[mod]++;
    }
    return count;
  }
}
