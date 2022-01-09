package a06_sorting_searching;

public class BillionUsers {
//GP n-th element formula: [a * (r^ (n- 1)]
  // In this case as a == r, it simplifies to r^n
  private double userOnDay(float rate, int day) {
    return Math.pow(rate, day);
  }

  public int getBillionUsersDay(float[] growthRates) {
    // Write your code here
    int start = 1;
    int end = 2_000; // considering this to be the upper_limit; can be discussed with the interviewer
    double target = 1_000_000_000;

    while (start < end) {
      double total = 0;
      int mid = start + (end - start) / 2;

      // calculate mid value
      for (float growthRate : growthRates) {
        total += userOnDay(growthRate, mid);
      }

      if (total == target) {
        return mid;
      }
      if (total > target) {
        end = mid;
      } else {
        start = mid + 1;
      }
    }
    return start;
  }
}
