package a03_linked_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
Passing Yearbooks
There are n students, numbered from 1 to n, each with their own yearbook. They would like to pass their yearbooks around and get them signed by other students.
You're given a list of n integers arr[1..n], which is guaranteed to be a permutation of 1..n (in other words, it includes the integers from 1 to n exactly once each, in some order). The meaning of this list is described below.
Initially, each student is holding their own yearbook. The students will then repeat the following two steps each minute: Each student i will first sign the yearbook that they're currently holding (which may either belong to themselves or to another student), and then they'll pass it to student arr[i-1]. It's possible that arr[i-1] = i for any given i, in which case student i will pass their yearbook back to themselves. Once a student has received their own yearbook back, they will hold on to it and no longer participate in the passing process.
It's guaranteed that, for any possible valid input, each student will eventually receive their own yearbook back and will never end up holding more than one yearbook at a time.
You must compute a list of n integers output, whose element at i-1 is equal to the number of signatures that will be present in student i's yearbook once they receive it back.
Signature
int[] findSignatureCounts(int[] arr)
Input
n is in the range [1, 100,000].
Each value arr[i] is in the range [1, n], and all values in arr[i] are distinct.
Output
Return a list of n integers output, as described above.
Example 1
n = 2
arr = [2, 1]
output = [2, 2]
Pass 1:
Student 1 signs their own yearbook. Then they pass the book to the student at arr[0], which is Student 2.
Student 2 signs their own yearbook. Then they pass the book to the student at arr[1], which is Student 1.
Pass 2:
Student 1 signs Student 2's yearbook. Then they pass it to the student at arr[0], which is Student 2.
Student 2 signs Student 1's yearbook. Then they pass it to the student at arr[1], which is Student 1.
Pass 3:
Both students now hold their own yearbook, so the process is complete.
Each student received 2 signatures.
 * </pre>
 *
 */
public class PassingYearbooks {
  // Find loop back to it's original position
  int[] findSignatureCounts(int[] arr) {
    int[] result = new int[arr.length];
    List<Set<Integer>> loops = new ArrayList<>();

    Arrays.fill(result, 0);
    for (int i = 0; i < arr.length; i++) {
      for (Set<Integer> loop : loops) {
        if (loop.contains(arr[i])) {
          result[i] = loop.size();
          break;
        }
      }

      if (result[i] == 0) {
        // Use linked hashset
        Set<Integer> loop = new LinkedHashSet<Integer>();
        loop.add(arr[i]); // signed own's book
        int index = arr[i] - 1;
        while (index != i) {
          loop.add(arr[index]);
          index = arr[index] - 1;
        }
        result[i] = loop.size();
      }
    }
    return result;
  }

}
