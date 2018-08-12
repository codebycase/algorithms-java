package a08_dynamic_programming;

/**
 * <h2>Climbing Stairs</h2>
 * 
 * You are climbing a stair case. It takes n steps to reach to the top.
 * 
 * Each time you can climb 1 to k steps. In how many distinct ways can you climb to the top?
 * 
 * Note: Given n will be a positive integer.
 * 
 * @author lchen
 *
 */
public class ClimbingStairs {
	/**
	 * Time complexity is O(kn), the space complexity is O(n)
	 * 
	 * @param n
	 * @param k
	 * @return
	 */
	public static int climbStairs(int n, int k) {
		return climbStairs(n, k, new int[n + 1]);
	}

	private static int climbStairs(int n, int k, int[] memo) {
		if (n < 0)
			return 0;
		else if (n == 0)
			return 1; // use one or zero?
		else if (n == 1)
			return 1;

		if (memo[n] == 0) {
			for (int i = 1; i <= Math.min(k, n); i++) {
				memo[n] += climbStairs(n - i, k, memo);
			}
		}

		return memo[n];
	}

	public static void main(String[] args) {
		System.out.println(climbStairs(3, 2));
		System.out.println(climbStairs(4, 2));
		System.out.println(climbStairs(10, 5));
		
	    int vInt = Integer.parseUnsignedInt("4294967295");
	    System.out.println(vInt); // -1
	    String sInt = Integer.toUnsignedString(vInt);
	    System.out.println(sInt); // 4294967295
	   
		
	}
}
