package a05_graphs_trees_heaps;

public class BinaryIndexedTree {
  private int[] tree;

  public BinaryIndexedTree(int[] nums) {
    // Plus one to make tree easier to operate
    tree = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
      update(i, nums[i]);
    }
  }

  public int getSum(int index) {
    int sum = 0;
    index++; // index is 1 more than nums' length
    while (index > 0) {
      sum += tree[index];
      index -= (index & -index);
    }
    return sum;
  }

  // Add val to tree[i] and all of its ancestores
  public void update(int index, int val) {
    index++;
    while (index <= tree.length) {
      tree[index] += val;
      index += (index & -index);
    }
  }

  public static void main(String args[]) {
    int freq[] = { 2, 1, 1, 3, 2, 3, 4, 5, 6, 7, 8, 9 };
    BinaryIndexedTree tree = new BinaryIndexedTree(freq);
    System.out.println("Sum of elements in arr[0..5]" + " is " + tree.getSum(5));
    // Update value for both array and tree
    freq[3] += 6;
    tree.update(3, 6);
    System.out.println("Sum of elements in arr[0..5]" + " after update is " + tree.getSum(5));
  }
}
