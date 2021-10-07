package a01_fundamentals;

/**
 * Given a binary string s and an integer k.
 * 
 * Return true if every binary code of length k is a substring of s. Otherwise, return false.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "00110110", k = 2 <br>
 * Output: true <br>
 * Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as
 * substrings at indicies 0, 1, 3 and 2 respectively.
 * 
 * Solution: With rolling hash method, we only need O(1) to calculate the next hash, because bitwise
 * operations (&, <<, |, etc.) are only cost O(1).
 *
 */
public class ContainsBinaryCodes {
  public static boolean hasAllCodes(String s, int k) {
    int need = 1 << k;
    boolean[] got = new boolean[need];
    int allOne = need - 1;
    int hashVal = 0;

    for (int i = 0; i < s.length(); i++) {

      hashVal = ((hashVal << 1) & allOne) | (s.charAt(i) - '0');
      // hash only available when i-k+1 > 0
      if (i >= k - 1 && !got[hashVal]) {
        got[hashVal] = true;
        need--;
        if (need == 0) {
          return true;
        }
      }
    }
    return false;
  }
}
