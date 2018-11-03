package a05_graphs_trees_heaps;

import util.TreeNode;

public class DistanceInBST {
    public static int bstDistance(int[] values, int n, int node1, int node2) {
        TreeNode tree = constructBST(values);
        return distanceBetween(tree, node1, node2);
    }

    private static TreeNode constructBST(int[] values) {
        if (values == null || values.length == 0)
            return null;

        TreeNode root = null;
        for (int i = 0; i < values.length; i++) {
            root = insert(root, values[i]);
        }

        return root;
    }

    private static TreeNode insert(TreeNode root, int key) {
        if (root == null) {
            root = new TreeNode(key);
        } else if (root.val > key) {
            root.left = insert(root.left, key);
        } else {
            root.right = insert(root.right, key);
        }
        return root;
    }

    private static int distanceBetween(TreeNode root, int p, int q) {
        if (root == null)
            return 0;
        if (root.val > p && root.val > q) {
            return distanceBetween(root.left, p, q);
        } else if (root.val < p && root.val < q) {
            return distanceBetween(root.right, p, q);
        } else {
            int distance = distanceFromRoot(root, p) + distanceFromRoot(root, q);
            return distance < 0 ? -1 : distance;
        }
    }

    private static int distanceFromRoot(TreeNode root, int x) {
        if (root == null)
            return Integer.MIN_VALUE;
        else if (root.val == x)
            return 0;
        else
            return 1 + (root.val > x ? distanceFromRoot(root.left, x) : distanceFromRoot(root.right, x));
    }

    public static void main(String[] args) {
        System.out.println(bstDistance(new int[] { 5, 6, 3, 1, 2, 4 }, 6, 2, 4));
        System.out.println(bstDistance(new int[] { 5, 6, 3, 1, 2, 4 }, 6, 2, 40));
    }
}
