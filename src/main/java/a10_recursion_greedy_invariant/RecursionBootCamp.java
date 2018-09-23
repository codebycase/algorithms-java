package a10_recursion_greedy_invariant;

import java.util.ArrayList;
import java.util.List;

import util.TreeNode;

public class RecursionBootCamp {
	/**
	 * Write a program which returns all distinct binary trees with a specified number of nodes.
	 * 
	 * @param numNodes
	 * @return
	 */
	public static List<TreeNode> allBinaryTree(int numNodes) {
		List<TreeNode> result = new ArrayList<>();

		if (numNodes == 0) // empty tree, add as an null
			result.add(null);

		// reduce 1 for each recursion!
		for (int numLeftTreeNodes = 0; numLeftTreeNodes < numNodes; numLeftTreeNodes++) {
			int numRightTreeNodes = numNodes - 1 - numLeftTreeNodes;
			List<TreeNode> leftSubtrees = allBinaryTree(numLeftTreeNodes);
			List<TreeNode> rightSubtrees = allBinaryTree(numRightTreeNodes);
			for (TreeNode left : leftSubtrees) {
				for (TreeNode right : rightSubtrees) {
					result.add(new TreeNode(0, left, right));
				}
			}
		}

		return result;
	}

	/**
	 * <pre>
	The gray code is a binary numeral system where two successive values differ in only one bit.
	
	Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.
	
	For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
	
	00 - 0
	01 - 1
	11 - 3
	10 - 2
	Note:
	For a given n, a gray code sequence is not uniquely defined.
	
	For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
	
	For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
	 * </pre>
	 * 
	 * @author lchen
	 *
	 */
	public List<Integer> grayCode(int n) {
		List<Integer> result = new ArrayList<>();
		result.add(0);
		for (int i = 0; i < n; i++) {
			int size = result.size();
			for (int k = size - 1; k >= 0; k--) {
				result.add(result.get(k) | (1 << i));
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<TreeNode> trees = allBinaryTree(3);
		System.out.println(trees.size());
	}
}
