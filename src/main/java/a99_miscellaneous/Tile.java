package a99_miscellaneous;

public class Tile {
	public static boolean canTile(int small, int big, int target) {
		// return small >= (big * 5 > target ? target % 5 : target - big * 5);
		return small >= (target / 5 > big ? target - big * 5 : target % 5);
	}

	public static boolean canTile2(int small, int big, int target) {
		if (target == 0)
			return true;
		if (target < 0)
			return false;
		if (small == 0 && big == 0)
			return false;
		return (small > 0 && canTile2(small - 1, big, target - 1)) || (big > 0 && canTile2(small, big - 1, target - 5));
	}

	public static void main(String[] args) {
		assert canTile(3, 4, 23) == canTile2(3, 4, 23);
		assert canTile(3, 4, 24) == canTile2(3, 4, 24);
	}
}
