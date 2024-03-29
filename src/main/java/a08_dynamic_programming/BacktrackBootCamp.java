package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a set of distinct integers, nums, return all possible subsets. also called the Powerset
 * P(n).
 * 
 * Note: The solution set must not contain duplicate subsets.
 * 
 * For example, If nums = [1,2,3], a solution is:
 * 
 * [ [3], [1], [2], [1,2,3], [1,3], [2,3], [1,2], [] ]
 * 
 * @author lchen
 *
 */
public class BacktrackBootCamp {
  /**
   * This is the sample to generate permutation in order, no need for backtrace.
   * 
   * @param listOfLists
   * @return
   */
  public List<String> enumerate(List<List<Object>> listOfLists) {
    List<String> results = new ArrayList<>();
    if (listOfLists.size() == 0)
      return results;
    results.add("");
    for (List<Object> list : listOfLists) {
      List<String> temp = new ArrayList<>();
      for (String result : results) {
        for (Object obj : list) {
          temp.add(result + obj);
        }
      }
      results = temp;
    }
    return results;
  }

  /**
   * When we generate a subset, each element can be either being chosen or not. <br>
   * The best case time is actually the total number of elements across all of the subsets. <br>
   * The solutions will be roughly O(n2^n) in space or time complexity.
   * 
   * @author lchen
   *
   */
  // Simply use iteration
  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> allSubsets = new ArrayList<>();
    allSubsets.add(new ArrayList<>());

    for (int i = 0; i < nums.length; i++) {
      List<List<Integer>> moreSubsets = new ArrayList<>();
      int item = nums[i];
      for (List<Integer> subset : allSubsets) {
        List<Integer> newSubset = new ArrayList<>(subset);
        newSubset.add(item);
        moreSubsets.add(newSubset);
      }
      allSubsets.addAll(moreSubsets);
    }

    return allSubsets;
  }

  // Use backtracking
  public List<List<Integer>> subsets2(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    backtrack(list, new ArrayList<>(), nums, 0);
    // recursive(list, new ArrayList<>(), nums, 0);
    return list;
  }

  private void backtrack(List<List<Integer>> list, List<Integer> temp, int[] nums, int start) {
    list.add(new ArrayList<>(temp));
    for (int i = start; i < nums.length; i++) {
      temp.add(nums[i]);
      backtrack(list, temp, nums, i + 1);
      temp.remove(temp.size() - 1);
    }
  }

  protected void recursive(List<List<Integer>> list, List<Integer> temp, int[] nums, int start) {
    list.add(temp);
    for (int i = start; i < nums.length; i++) {
      List<Integer> clone = new ArrayList<>(temp);
      clone.add(nums[i]);
      recursive(list, clone, nums, i + 1);
    }
  }

  /**
   * There are a number of testing applications in which it's required to compute all subsets of a
   * given size for a specified set.
   */
  public List<List<Integer>> subsetsK(int[] nums, int k) {
    List<List<Integer>> list = new ArrayList<>();
    backtrackK(list, new ArrayList<>(), nums, 0, k);
    return list;
  }

  private void backtrackK(List<List<Integer>> list, List<Integer> temp, int[] nums, int start, int k) {
    if (temp.size() == k) {
      list.add(new ArrayList<>(temp));
    } else {
      int numRemaining = k - temp.size();
      // Just skip if not enough nums left!
      for (int i = start; i < nums.length && numRemaining <= nums.length - i; i++) {
        temp.add(nums[i]);
        backtrackK(list, temp, nums, i + 1, k);
        temp.remove(temp.size() - 1);
      }
    }
  }

  /**
   * Given a collection of integers that might contain duplicates, nums, return all possible subsets.
   * 
   * @param nums
   * @return
   */
  public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrackWithDup(list, new ArrayList<>(), nums, 0);
    return list;
  }

  private void backtrackWithDup(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
    list.add(new ArrayList<>(tempList));
    for (int i = start; i < nums.length; i++) {
      if (i > start && nums[i] == nums[i - 1])
        continue; // skip duplicates
      tempList.add(nums[i]);
      backtrack(list, tempList, nums, i + 1);
      tempList.remove(tempList.size() - 1);
    }
  }

  /**
   * Given a collection of distinct numbers, return all possible unique permutations.
   * 
   * For example, [1,2,3] have the following permutations: [ [1,2,3], [1,3,2], [2,1,3], [2,3,1],
   * [3,1,2], [3,2,1] ]
   * 
   * @param nums
   * @return
   */
  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
    return result;
  }

  private void backtrack(List<List<Integer>> results, List<Integer> temp, int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
      results.add(new ArrayList<>(temp));
    } else {
      for (int i = 0; i < nums.length; i++) {
        // if (temp.contains(nums[i]))
        if (used[i])
          continue; // element already exists, skip!
        used[i] = true;
        temp.add(nums[i]);
        backtrack(results, temp, nums, used);
        used[i] = false;
        temp.remove(temp.size() - 1);
      }
    }
  }

  // If contains duplicates, e.g. [1,1,2]
  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> results = new ArrayList<>();
    Arrays.sort(nums); // sort the list first!
    backtrack2(results, new ArrayList<>(), nums, new boolean[nums.length]);
    return results;
  }

  private void backtrack2(List<List<Integer>> results, List<Integer> temp, int[] nums, boolean[] used) {
    if (temp.size() == nums.length) {
      results.add(new ArrayList<>(temp));
    } else {
      for (int i = 0; i < nums.length; i++) {
        // used[i - 1] to bind nums[i] and nums[i - 1] together!
        // either use[i - 1] or !use[i - 1] works for this case 
        if (used[i] || (i > 0 && nums[i] == nums[i - 1] && used[i - 1]))
          continue;
        used[i] = true;
        temp.add(nums[i]);
        backtrack2(results, temp, nums, used);
        used[i] = false;
        temp.remove(temp.size() - 1);
      }
    }
  }

  public List<List<Integer>> permuteUnique2(int[] nums) {
    List<List<Integer>> results = new ArrayList<>();
    Map<Integer, Integer> counts = new HashMap<>();
    for (int num : nums) {
      counts.put(num, counts.getOrDefault(num, 0) + 1);
    }
    backtrack3(results, new ArrayList<>(), nums.length, counts);
    return results;
  }

  private void backtrack3(List<List<Integer>> results, List<Integer> temp, int length, Map<Integer, Integer> counts) {
    if (temp.size() == length) {
      results.add(new ArrayList<>(temp));
    } else {
      counts.forEach((key, count) ->
        {
          if (count > 0) {
            temp.add(key);
            counts.put(key, count - 1);
            backtrack3(results, temp, length, counts);
            temp.remove(temp.size() - 1);
            counts.put(key, count);
          }
        });
    }
  }

  /**
   * Given a set of candidate numbers (C) (<b>without duplicates</b>) and a target number (T), find
   * all unique combinations in C where the candidate numbers sums to T.
   * 
   * The same repeated number may be chosen from C unlimited number of times.
   * 
   * Note: All numbers (including target) will be positive integers. The solution set must not contain
   * duplicate combinations. For example, given candidate set [2, 3, 6, 7] and target 7, A solution
   * set is: [ [7], [2, 2, 3] ]
   * 
   * @param nums
   * @param target
   * @return
   */
  // The comment out lines to handle the case: Each number can only be used once, also with
  // duplicates!
  public List<List<Integer>> combinationSum(int[] nums, int target) {
    List<List<Integer>> list = new ArrayList<>();
    // Arrays.sort(nums); // sort first in favor of skipping duplicates!
    backtrack(list, new ArrayList<>(), nums, target, 0);
    return list;
  }

  private void backtrack(List<List<Integer>> list, List<Integer> temp, int[] nums, int remain, int start) {
    if (remain < 0)
      return;
    else if (remain == 0)
      list.add(new ArrayList<>(temp));
    else {
      for (int i = start; i < nums.length; i++) {
        // if (i > start && nums[i] == nums[i - 1])
        // continue; // skip duplicates
        temp.add(nums[i]);
        // backtrack(list, temp, nums, remain - nums[i], i + 1); // can be only used once!
        backtrack(list, temp, nums, remain - nums[i], i); // not i + 1 because we can reuse
        // same elements
        temp.remove(temp.size() - 1);
      }
    }
  }

  /**
   * Given a string s, partition s such that every substring of the partition is a palindrome.
   * 
   * Return all possible palindrome partitioning of s.
   * 
   * For example, given s = "aab", Return
   * 
   * [ ["aa","b"], ["a","a","b"] ]
   * 
   * @param s
   * @return
   */
  public List<List<String>> partition(String s) {
    List<List<String>> list = new ArrayList<>();
    backtrack(list, new ArrayList<>(), s, 0);
    return list;
  }

  public void backtrack(List<List<String>> list, List<String> temp, String s, int start) {
    if (start == s.length())
      list.add(new ArrayList<>(temp));
    else {
      for (int i = start; i < s.length(); i++) {
        if (isPalindrome(s, start, i)) {
          temp.add(s.substring(start, i + 1));
          backtrack(list, temp, s, i + 1);
          temp.remove(temp.size() - 1);
        }
      }
    }
  }

  public boolean isPalindrome(String s, int low, int high) {
    while (low < high)
      if (s.charAt(low++) != s.charAt(high--))
        return false;
    return true;
  }

  public int minCut(String s) {
    int n = s.length();
    int[] dp = new int[n]; // min cut for s[0:j) to be patitioned
    boolean[][] isPal = new boolean[n][n]; // true means s[j:i) is a valid palindrome

    for (int i = 0; i < n; i++) {
      int min = i;
      for (int j = 0; j <= i; j++) {
        // [j, i] is palindrome if [j + 1, j - 1] is palindrome and s[j] == s[i]
        if (s.charAt(j) == s.charAt(i) && (j + 1 > i - 1 || isPal[j + 1][i - 1])) {
          isPal[j][i] = true;
          min = j == 0 ? 0 : Math.min(min, dp[j - 1] + 1);
        }
      }
      dp[i] = min;
    }
    return dp[n - 1];
  }

  public static void main(String[] args) {
    BacktrackBootCamp solution = new BacktrackBootCamp();
    int[] nums = new int[] { 1, 2, 3, 2 };
    assert solution.subsets(nums).size() == 16;
    assert solution.subsets2(nums).size() == 16;
    assert solution.subsetsK(nums, 3).size() == 4;
    System.out.println(solution.subsetsK(nums, 3));
    assert solution.subsetsWithDup(nums).size() == 14;

    assert solution.permuteUnique(nums).toString().equals(solution.permuteUnique2(nums).toString());

    List<List<Object>> listOfLists = new ArrayList<>();
    listOfLists.add(Arrays.asList("a", "b", "c", "d"));
    listOfLists.add(Arrays.asList(1, 2, 3, 4, 5));
    listOfLists.add(Arrays.asList("Tom", "Mike", "Joe"));

    List<String> results = solution.enumerate(listOfLists);
    assert results.size() == 60;
  }

}
