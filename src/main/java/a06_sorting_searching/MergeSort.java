package a06_sorting_searching;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import util.ListNode;

public class MergeSort {
  public static void mergeSort(int[] array) {
    int[] helper = new int[array.length];
    mergeSort(array, helper, 0, array.length - 1);
  }

  public static void mergeSort(int[] array, int[] helper, int low, int high) {
    if (low < high) {
      int middle = (low + high) / 2;
      mergeSort(array, helper, low, middle); // Sort left half
      mergeSort(array, helper, middle + 1, high); // Sort right half
      merge(array, helper, low, middle, high); // Merge them
    }
  }

  public static void merge(int[] array, int[] helper, int low, int middle, int high) {
    /* Copy both halves into a helper array */
    for (int i = low; i <= high; i++) {
      helper[i] = array[i];
    }

    int helperLeft = low;
    int helperRight = middle + 1;
    int current = low;

    /* Iterate through helper array. Compare the left and right
     * half, copying back the smaller element from the two halves
     * into the original array. */
    while (helperLeft <= middle && helperRight <= high) {
      if (helper[helperLeft] <= helper[helperRight]) {
        array[current] = helper[helperLeft];
        helperLeft++;
      } else { // If right element is smaller than left element
        array[current] = helper[helperRight];
        helperRight++;
      }
      current++;
    }

    /* Copy the rest of the left side of the array into the
     * target array */
    int remaining = middle - helperLeft;
    for (int i = 0; i <= remaining; i++) {
      array[current + i] = helper[helperLeft + i];
    }
  }

  public ListNode mergeKLists(ListNode[] lists) {
    ListNode preHead = new ListNode(0);

    Queue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);

    for (ListNode node : lists) {
      if (node != null) {
        queue.offer(node);
      }
    }

    ListNode point = preHead;
    while (!queue.isEmpty()) {
      ListNode node = queue.poll();
      point.next = new ListNode(node.val);
      point = point.next;
      if (node.next != null) {
        queue.offer(node.next);
      }
    }

    return preHead.next;
  }

  public int maximumUnits(int[][] boxTypes, int truckSize) {
    Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
    int maxUnits = 0;
    for (int[] boxType : boxTypes) {
      int fitInBoxes = Math.min(truckSize, boxType[0]);
      if (fitInBoxes == 0)
        break;
      maxUnits += fitInBoxes * boxType[1];
      truckSize -= fitInBoxes;
    }
    return maxUnits;
  }

  public static void main(String[] args) {
    int[] array = new int[] { 1, 4, 5, 2, 8, 9 };
    mergeSort(array);
    System.out.print(Arrays.toString(array));
  }
}
