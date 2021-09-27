package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CountSmallerNumbersAfterSelf {
  public List<Integer> countSmaller2(int[] nums) {
    List<Integer> ans = new LinkedList<>(); // only insert
    List<Integer> visited = new ArrayList<>(); // binary search array

    for (int i = nums.length - 1; i >= 0; i--) {
      int count = getIndex(visited, nums[i]);
      if (count < visited.size()) {
        visited.add(count, nums[i]);
      } else {
        visited.add(nums[i]);
      }
      ans.add(0, count);
    }

    return ans;
  }

  private int getIndex(List<Integer> visited, int target) {
    if (visited.isEmpty())
      return 0;
    int start = 0, end = visited.size();
    while (start < end) {
      int mid = start + (end - start) / 2;
      if (visited.get(mid) < target) {
        start = mid + 1;
      } else {
        end = mid;
      }
    }
    return end;
  }

  // Use num as index, and always +1 for prefix sum
  public List<Integer> countSmaller(int[] nums) {
    if (nums.length == 0)
      return new ArrayList<>();
    Integer[] ans = new Integer[nums.length];
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < nums.length; i++) {
      min = Math.min(min, nums[i]);
    }
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
      // Let index starts from 1
      nums[i] = nums[i] - min + 1;
      max = Math.max(max, nums[i]);
    }
    // build a binary indexed tree
    int[] tree = new int[max + 1];
    for (int i = nums.length - 1; i >= 0; i--) {
      ans[i] = getCount(nums[i] - 1, tree); // smaller numbers
      updateCount(nums[i], tree);
    }
    return Arrays.asList(ans);
  }

  // O(log(n))
  private void updateCount(int idx, int[] tree) {
    while (idx < tree.length) {
      tree[idx]++;
      idx += (idx & -idx); // plus right most set bit to get parents
    }
  }

  // O(log(n))
  private int getCount(int idx, int[] tree) {
    int count = 0;
    while (idx > 0) {
      count += tree[idx];
      idx -= (idx & -idx); // minus right most set bit to get children
    }
    return count;
  }

  public static void main(String[] args) {
    CountSmallerNumbersAfterSelf solution = new CountSmallerNumbersAfterSelf();
    List<Integer> result = solution.countSmaller(new int[] { 5, 2, 6, 1 });
    System.out.println(result);
    result = solution.countSmaller(new int[] { 6, 3, 7, 2 });
    System.out.println(result);
  }
}
