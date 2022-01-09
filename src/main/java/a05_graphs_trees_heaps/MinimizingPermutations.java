package a05_graphs_trees_heaps;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * In this problem, you are given an integer N, and a permutation, P of the integers from 1 to N,
 * denoted as (a_1, a_2, ..., a_N). You want to rearrange the elements of the permutation into
 * increasing order, repeatedly making the following operation: Select a sub-portion of the
 * permutation, (a_i, ..., a_j), and reverse its order.
 * 
 * Your goal is to compute the minimum number of such operations required to return the permutation
 * to increasing order.
 * 
 * <pre>
Signature
int minOperations(int[] arr)

Input
Array arr is a permutation of all integers from 1 to N, N is between 1 and 8

Output
An integer denoting the minimum number of operations required to arrange the permutation in increasing order

Example
If N = 3, and P = (3, 1, 2), we can do the following operations:
Select (1, 2) and reverse it: P = (3, 2, 1).
Select (3, 2, 1) and reverse it: P = (1, 2, 3).
output = 2
 * </pre>
 *
 */
public class MinimizingPermutations {
  int minOperations(int[] arr) {
    int result = 0;
    int[] target = arr.clone();
    Arrays.sort(target);
    Set<String> seen = new HashSet<>();
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(arr);
    seen.add(Arrays.toString(arr));
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        int[] curr = queue.poll();
        if (Arrays.equals(target, curr)) {
          return result;
        }
        for (int j = 0; j < curr.length; j++) {
          for (int k = j + 1; k < curr.length; k++) {
            int[] next = curr.clone();
            reverse(next, j, k);
            if (!seen.contains(Arrays.toString(next))) {
              queue.offer(next);
              seen.add(Arrays.toString(next));
            }
          }
        }
      }
      result++;
    }
    return result;
  }

  private void reverse(int[] arr, int from, int to) {
    for (; from < to; from++, to--) {
      int tmp = arr[from];
      arr[from] = arr[to];
      arr[to] = tmp;
    }
  }

  public static void main(String[] args) {
    MinimizingPermutations solution = new MinimizingPermutations();
    int[] arr_1 = { 1, 2, 5, 4, 3 };
    int output_1 = solution.minOperations(arr_1);
    System.out.println(output_1);
  }

}
