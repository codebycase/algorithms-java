package a02_arrays_strings;

public class MonotoneIncreasingString {
  public int minFlipsMonoIncr(String S) {
    int N = S.length();

    // prefix sum for ones
    int[] P = new int[N + 1];
    for (int i = 0; i < N; ++i)
      P[i + 1] = P[i] + (S.charAt(i) == '1' ? 1 : 0);

    int ans = Integer.MAX_VALUE;
    for (int j = 0; j <= N; j++) {
      // left part ones + right part zeros
      ans = Math.min(ans, P[j] + N - j - (P[N] - P[j]));
    }

    return ans;
  }
}
