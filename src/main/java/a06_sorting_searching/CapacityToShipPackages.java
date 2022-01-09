package a06_sorting_searching;

public class CapacityToShipPackages {
  public int shipWithinDays(int[] weights, int days) {
    int low = 0, high = 0;
    for (int weight : weights) {
      low = Math.max(low, weight);
    }
    high = (int) Math.ceil((double) weights.length / days) * low;

    while (low <= high) {
      int mid = low + (high - low) / 2;
      if (isPossible(weights, days, mid)) {
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }

    return low;
  }

  public boolean isPossible(int[] weights, int days, int capacity) {
    int weightSum = 0;
    int daysCount = 0;
    for (int weight : weights) {
      if (weightSum + weight <= capacity) {
        weightSum += weight;
      } else {
        daysCount++;
        weightSum = weight;
        if (daysCount >= days || weight > capacity) {
          return false;
        }
      }
    }
    return true;
  }
}
