package a02_arrays_strings;

public class MaximumScore {
  private long maxSumFrom(int[] nums1, int nums1Place, int[] nums2, int nums2Place) {
    long total1 = 0, total2 = 0;
    while (nums1Place < nums1.length || nums2Place < nums2.length) {
      if (nums1Place >= nums1.length) {
        total2 += nums2[nums2Place++];
      } else if (nums2Place >= nums2.length) {
        total1 += nums1[nums1Place++];
      } else {
        if (nums1[nums1Place] < nums2[nums2Place]) {
          total1 += nums1[nums1Place++];
        } else if (nums1[nums1Place] > nums2[nums2Place]) {
          total2 += nums2[nums2Place++];
        } else {
          total1 += nums1[nums1Place++];
          total2 += nums2[nums2Place++];
          long nextTotal = maxSumFrom(nums1, nums1Place, nums2, nums2Place);
          total1 += nextTotal;
          total2 += nextTotal;
          break;
        }
      }
    }
    return Math.max(total1, total2);
  }

  public int maxSum2(int[] nums1, int[] nums2) {
    return (int) (maxSumFrom(nums1, 0, nums2, 0) % 1000000007);
  }

  public int maxSum1(int[] nums1, int[] nums2) {
    long sum1 = 0, sum2 = 0, ans = 0;
    int i = 0, j = 0;

    while (i < nums1.length && j < nums2.length) {
      if (nums1[i] < nums2[j])
        sum1 += nums1[i++];
      else if (nums1[i] > nums2[j])
        sum2 += nums2[j++];
      else {
        ans += Math.max(sum1, sum2) + nums1[i];
        sum1 = 0;
        sum2 = 0;
        i++;
        j++;
      }
    }

    while (i < nums1.length)
      sum1 += nums1[i++];
    while (j < nums2.length)
      sum2 += nums2[j++];

    return (int) ((ans += Math.max(sum1, sum2)) % 1000000007);
  }
}
