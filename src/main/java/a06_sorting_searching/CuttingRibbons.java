package a06_sorting_searching;

public class CuttingRibbons {
  public int maxLength(int[] ribbons, int k) {
    int low = 1, high = 0;
    for (int ribbon : ribbons) {
      high = Math.max(high, ribbon);
    }
    high++; // Handle all same heights

    while (low < high) {
      int mid = low + (high - low) / 2;
      if (!canCut(ribbons, k, mid))
        high = mid;
      else
        low = mid + 1;
    }

    // Low just exceeded the limit
    return low - 1;
  }

  private boolean canCut(int[] ribbons, int k, int i) {
    int count = 0;
    for (int r : ribbons) {
      count += r / i;
      if (count >= k) {
        return true;
      }
    }
    return false;
  }

}
