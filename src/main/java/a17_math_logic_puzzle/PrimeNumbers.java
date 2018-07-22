package a17_math_logic_puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeNumbers {
	/**
	 * The sqrt(n) is sufficient because where a * b = n, if a > sqrt(n), then b < sqrt(n), and we
	 * already checked b.
	 */
	public static boolean checkPrimality(int n) {
		if (n < 2)
			return false;
		int sqrt = (int) Math.sqrt(n);
		for (int i = 2; i <= sqrt; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public static List<Integer> generatePrimes(int max) {
		List<Integer> primes = new ArrayList<>();
		boolean[] flags = new boolean[max + 1];

		// Set all flags to true other than 0 and 1
		Arrays.fill(flags, true);
		flags[0] = false;
		flags[1] = false;

		int prime = 2;
		while (prime <= Math.sqrt(max)) {
			// Cross off remaining multiples of prime
			// Starts with (prime * prime) because if we have a k * prime, where k < prime, this value
			// would have already been crossed off in a prior iteraton
			for (int i = prime * prime; i < flags.length; i += prime) {
				flags[i] = false;
			}
			// Find next value which is true
			prime = prime + 1;
			while (prime < flags.length && !flags[prime]) {
				prime++;
			}
		}
		// Collect all prime numbers
		for (int i = 0; i < flags.length; i++) {
			if (flags[i])
				primes.add(i);
		}

		return primes;

	}

	public static void main(String[] args) {
		assert checkPrimality(17) == true;
		assert checkPrimality(9) == false;
		List<Integer> primes = generatePrimes(20);
		assert primes.toString().equals("[2, 3, 5, 7, 11, 13, 17, 19]");
	}
}
