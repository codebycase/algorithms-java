package a18_the_honors_question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanEncoding {
	static class Symbol {
		char chr;
		double freq;
		String code;

		public Symbol(char chr, double freq) {
			this.chr = chr;
			this.freq = freq;
		}
	}

	static class TreeNode {
		double freqSum;
		Symbol symbol;
		TreeNode left, right;

		public TreeNode(double freqSum, Symbol symbol, TreeNode left, TreeNode right) {
			this.freqSum = freqSum;
			this.symbol = symbol;
			this.left = left;
			this.right = right;
		}
	}

	public static Map<Character, String> huffmanEncoding(List<Symbol> symbols) {
		Queue<TreeNode> candidates = new PriorityQueue<>((a, b) -> (Double.compare(a.freqSum, b.freqSum)));
		// add symbols as leaves
		for (Symbol symbol : symbols) {
			candidates.add(new TreeNode(symbol.freq, symbol, null, null));
		}
		// keep combining two nodes utils there is one node left, which is the root.
		while (candidates.size() > 1) {
			TreeNode left = candidates.remove();
			TreeNode right = candidates.remove();
			candidates.add(new TreeNode(left.freqSum + right.freqSum, null, left, right));
		}
		Map<Character, String> huffmanEncoding = new HashMap<>();
		assignHuffmanCode(candidates.peek(), new StringBuilder(), huffmanEncoding);
		return huffmanEncoding;
	}

	public static void assignHuffmanCode(TreeNode tree, StringBuilder code, Map<Character, String> huffmanEncoding) {
		if (tree != null) {
			if (tree.symbol != null) {
				// this node is a leaf
				huffmanEncoding.put(tree.symbol.chr, code.toString());
				tree.symbol.code = code.toString();
			} else {
				assignHuffmanCode(tree.left, code.append('0'), huffmanEncoding);
				code.setLength(code.length() - 1); // backtrack
				assignHuffmanCode(tree.right, code.append('1'), huffmanEncoding);
				code.setLength(code.length() - 1); // backtrack
			}
		}
	}

	public static void main(String[] args) {
		double[] ENGLISH_FREQ = { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025,
				2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074 };
		List<Symbol> symbols = new ArrayList<>();
		for (int i = 0; i < 26; ++i) {
			symbols.add(new Symbol((char) ('a' + i), ENGLISH_FREQ[i]));
		}
		Map<Character, String> result = huffmanEncoding(symbols);
		double avg = 0.0;
		for (Symbol symbol : symbols) {
			System.out.println(symbol.chr + "\t" + symbol.freq + "\t" + result.get(symbol.chr));
			avg += symbol.freq / 100 * result.get(symbol.chr).length();
		}
		System.out.println("Average huffman code length = " + avg);
		assert avg > 4.2 && avg < 4.3;
	}
}
