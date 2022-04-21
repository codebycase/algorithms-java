package a00_collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import util.TreeNode;

public class BinaryTree {
  public class BSTIterator {
    final Deque<TreeNode> path;

    public BSTIterator(TreeNode root) {
      path = new ArrayDeque<>();
      buildPathToLeftmostChild(root);
    }

    public boolean hasNext() {
      return !path.isEmpty();
    }

    public int next() {
      TreeNode node = path.pop();
      buildPathToLeftmostChild(node.right);
      return node.val;
    }

    private void buildPathToLeftmostChild(TreeNode node) {
      TreeNode cur = node;
      while (cur != null) {
        path.push(cur);
        cur = cur.left;
      }
    }
  }

  public List<List<Integer>> findLeavesOfBinaryTree(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    heightDfs(root, result);
    return result;
  }

  private int heightDfs(TreeNode node, List<List<Integer>> result) {
    if (node == null)
      return -1;
    int level = 1 + Math.max(heightDfs(node.left, result), heightDfs(node.right, result));
    if (result.size() <= level)
      result.add(new ArrayList<>());
    result.get(level).add(node.val);
    node.left = node.right = null; // remove leaves
    return level;
  }

  /**
   * In-order traversal with the help of linked list.
   * 
   * Time complexity: O(N)
   */
  public List<Integer> closestKValues(TreeNode root, double target, int k) {
    LinkedList<Integer> list = new LinkedList<Integer>();
    inorder(list, root, target, k);
    return list;
  }

  private boolean inorder(LinkedList<Integer> list, TreeNode node, double target, int k) {
    if (node == null)
      return false;

    if (inorder(list, node.left, target, k))
      return true;

    if (list.size() == k) {
      if (Math.abs(list.getFirst() - target) < Math.abs(node.val - target))
        return true;
      else
        list.removeFirst();
    }

    list.addLast(node.val);
    return inorder(list, node.right, target, k);
  }

  /**
   * The idea is to convert BST into an array, sort it by the distance to the target, and return the k
   * closest elements.
   * 
   * Time complexity: O(NlogN). O(N) to build inorder traversal and then O(NlogN) to sort it.
   */
  public List<Integer> closestKValues2(TreeNode root, double target, int k) {
    List<Integer> nums = new ArrayList<>();
    inorder(root, nums);

    java.util.Collections.sort(nums, new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return Math.abs(o1 - target) < Math.abs(o2 - target) ? -1 : 1;
      }
    });
    return nums.subList(0, k);
  }

  private void inorder(TreeNode node, List<Integer> nums) {
    if (node == null)
      return;
    inorder(node.left, nums);
    nums.add(node.val);
    inorder(node.right, nums);
  }

  /**
   * We could use the heap of capacity k, sorted by the distance to the target.
   * 
   * Time complexity: O(Nlogk).
   */
  public List<Integer> closestKValues3(TreeNode root, double target, int k) {
    List<Integer> nums = new ArrayList<>();

    Queue<Integer> heap = new PriorityQueue<>((o1, o2) -> Math.abs(o1 - target) > Math.abs(o2 - target) ? -1 : 1);
    inorder(root, nums, heap, k);
    return new ArrayList<>(heap);
  }

  private void inorder(TreeNode r, List<Integer> nums, Queue<Integer> heap, int k) {
    if (r == null)
      return;

    inorder(r.left, nums, heap, k);
    heap.add(r.val);
    if (heap.size() > k)
      heap.remove();
    inorder(r.right, nums, heap, k);
  }

  /**
   * The idea is to compare the predecessors and successors of the closest node to the target, we can
   * use two stacks to track the predecessors and successors, then like what we do in merge sort, we
   * compare and pick the closest one to the target and put it to the result list.
   * 
   * Time complexity: O(log(n) + k)
   */
  public List<Integer> closestKValues4(TreeNode root, double target, int k) {
    List<Integer> ans = new ArrayList<>();

    Stack<Integer> s1 = new Stack<>(); // predecessors
    Stack<Integer> s2 = new Stack<>(); // successors

    inorder(root, target, false, s1);
    inorder(root, target, true, s2);

    while (k-- > 0) {
      if (s1.isEmpty())
        ans.add(s2.pop());
      else if (s2.isEmpty())
        ans.add(s1.pop());
      else if (Math.abs(s1.peek() - target) < Math.abs(s2.peek() - target))
        ans.add(s1.pop());
      else
        ans.add(s2.pop());
    }

    return ans;
  }

  private void inorder(TreeNode root, double target, boolean reverse, Stack<Integer> stack) {
    if (root == null)
      return;

    inorder(reverse ? root.right : root.left, target, reverse, stack);

    if ((reverse && root.val <= target) || (!reverse && root.val > target))
      return; // Early terminate

    stack.push(root.val);

    inorder(reverse ? root.left : root.right, target, reverse, stack);
  }

  /**
   * Quick select: Sort a list within left..right till kth less close element takes its place.
   * 
   * It has O(N) average time complexity and widely used in practice. It is worth to note that its
   * worst-case time complexity is O(N^2), although the probability of this worst-case is negligible.
   */
  public List<Integer> closestKValues5(TreeNode root, double target, int k) {
    List<Integer> nums = new ArrayList<>();
    inorder(root, nums);
    quickselect(nums, target, 0, nums.size() - 1, k);
    return nums.subList(0, k);
  }

  private void quickselect(List<Integer> nums, double target, int left, int right, int kSmallest) {
    if (left >= right)
      return;

    Random randomNum = new Random();
    int pivotIndex = left + randomNum.nextInt(right - left);

    pivotIndex = partition(nums, target, left, right, pivotIndex);

    if (kSmallest == pivotIndex) {
      return;
    } else if (kSmallest < pivotIndex) {
      quickselect(nums, target, left, pivotIndex - 1, kSmallest);
    } else {
      quickselect(nums, target, pivotIndex + 1, right, kSmallest);
    }
  }

  private int partition(List<Integer> nums, double target, int left, int right, int pivotIndex) {
    double pivotDist = Math.abs(nums.get(pivotIndex) - target);

    // 1. move pivot to end
    swap(nums, pivotIndex, right);
    int storeIndex = left;

    // 2. move more close elements to the left
    for (int i = left; i <= right; i++) {
      if (Math.abs(nums.get(i) - target) < pivotDist) {
        swap(nums, storeIndex, i);
        storeIndex++;
      }
    }

    // 3. move pivot to its final place
    swap(nums, storeIndex, right);

    return storeIndex;
  }

  private void swap(List<Integer> nums, int a, int b) {
    int tmp = nums.get(a);
    nums.set(a, nums.get(b));
    nums.set(b, tmp);
  }
}
