package a08_dynamic_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StackOfBoxes {
	class Box {
		int width, height, depth;

		public Box(int width, int height, int depth) {
			this.width = width;
			this.height = height;
			this.depth = depth;
		}

		public boolean canBeAbove(Box b) {
			if (b == null)
				return false;
			return b.width > width && b.height > height && b.depth > depth;
		}

		public String toString() {
			return "Box(" + width + "," + height + "," + depth + ")";
		}
	}

	public int createStack(List<Box> boxes) {
		Collections.sort(boxes, (a, b) -> (b.height - a.height));
		int maxHeight = 0;
		int[] stackMap = new int[boxes.size()];
		for (int i = 0; i < boxes.size(); i++) {
			int height = createStack(boxes, i, stackMap);
			maxHeight = Math.max(maxHeight, height);
		}
		return maxHeight;
	}

	private int createStack(List<Box> boxes, int bottomIndex, int[] stackMap) {
		if (bottomIndex < boxes.size() && stackMap[bottomIndex] > 0)
			return stackMap[bottomIndex];

		Box bottom = boxes.get(bottomIndex);
		int maxHeight = 0;
		for (int i = bottomIndex + 1; i < boxes.size(); i++) {
			if (boxes.get(i).canBeAbove(bottom)) {
				int height = createStack(boxes, i, stackMap);
				maxHeight = Math.max(height, maxHeight);
			}
		}

		maxHeight += bottom.height;
		stackMap[bottomIndex] = maxHeight;
		return maxHeight;
	}

	private int createStack2(List<Box> boxes) {
		Collections.sort(boxes, (a, b) -> (b.height - a.height));
		int[] stackMap = new int[boxes.size()];
		return createStack2(boxes, null, 0, stackMap);
	}

	private int createStack2(List<Box> boxes, Box bottom, int offset, int[] stackMap) {
		if (offset >= boxes.size())
			return 0; // Base case

		Box newBottom = boxes.get(offset);
		int heightWithBottom = 0;
		if (bottom == null || newBottom.canBeAbove(bottom)) {
			if (stackMap[offset] == 0) {
				stackMap[offset] = createStack2(boxes, newBottom, offset + 1, stackMap);
				stackMap[offset] += newBottom.height;
			}
			heightWithBottom = stackMap[offset];
		}

		int heightWithoutBottom = createStack2(boxes, bottom, offset + 1, stackMap);

		return Math.max(heightWithBottom, heightWithoutBottom);
	}

	public void test() {
		Box[] boxList = { new Box(6, 4, 4), new Box(8, 6, 2), new Box(5, 3, 3), new Box(7, 8, 3), new Box(4, 2, 2),
				new Box(9, 7, 3) };
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (Box b : boxList) {
			boxes.add(b);
		}

		int height = createStack(boxes);
		int height2 = createStack2(boxes);
		assert height == height2;
	}

	public static void main(String[] args) {
		StackOfBoxes solution = new StackOfBoxes();
		solution.test();
	}
}
