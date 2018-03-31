package a10_recursion_greedy_invariant;

import java.util.Stack;

public class HanoiTower {
	private static final int NUM_PEGS = 3;

	class Tower {
		private Stack<Integer> disks;
		private int index;

		public Tower(int i) {
			disks = new Stack<Integer>();
			index = i;
		}

		public int index() {
			return index;
		}

		public void add(int disk) {
			if (!disks.isEmpty() && disks.peek() <= disk)
				System.err.println("Error placing disk " + disk);
			else
				disks.push(disk);
		}

		public void moveTopTo(Tower tower) {
			int top = disks.pop();
			tower.add(top);
		}

		public void moveDisks(int n, Tower destination, Tower buffer) {
			if (n <= 0)
				return;
			moveDisks(n - 1, buffer, destination);
			moveTopTo(destination);
			System.out.println("Moved top from " + this.index + " to " + destination.index);
			buffer.moveDisks(n - 1, destination, this);
		}

		public String toString() {
			return disks.toString();
		}
	}

	public Tower printHanoiTower(int numRings) {
		Tower[] towers = new Tower[NUM_PEGS];
		for (int i = 0; i < towers.length; i++) {
			towers[i] = new Tower(i);
		}
		for (int i = numRings; i >= 1; i--) {
			towers[0].add(i);
		}
		towers[0].moveDisks(numRings, towers[2], towers[1]);
		return towers[2];
	}

	public static void main(String[] args) {
		HanoiTower solution = new HanoiTower();
		Tower tower = solution.printHanoiTower(5);
		System.out.println("Tower: " + tower.toString());
		assert tower.toString().equals("[5, 4, 3, 2, 1]");
	}
}
