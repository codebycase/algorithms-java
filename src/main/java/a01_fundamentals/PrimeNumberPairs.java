package a01_fundamentals;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberPairs {

    /* count number of pairs of prime numbers p, q such that p * q <= n */
    public static int countPrimePairs(int n) {
        int count = 0;
        List<Integer> primes = getPrimeNumbers(n);
        int i = 0, j = primes.size() - 1;
        while (i < j) {
            if (primes.get(i) * primes.get(j) > n) {
                j--;
            } else {
                count += j - i;
                i++;
                j--; // optional
            }
        }
        return count;
    }

    public static List<Integer> getPrimeNumbers(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] notPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            if (notPrime[i] == false) {
                primes.add(i);
                for (int j = 2; i * j < n; j++) {
                    notPrime[i * j] = true;
                }
            }
        }
        return primes;
    }

    // bottom-up recursion, trace all the way up to root
    public static int findNodeValue(int n, int k) {
        if (n == 1)
            return k - 1; // root's value
        int parent = findNodeValue(n - 1, (k + 1) / 2);
        if (k % 2 != 0) {
            return parent;
        } else {
            return parent == 0 ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        assert countPrimePairs(10) == 2;
        assert countPrimePairs(25) == 6;
        assert findNodeValue(1, 1) == 0;
        assert findNodeValue(2, 1) == 0;
        assert findNodeValue(2, 2) == 1;
        assert findNodeValue(3, 1) == 0;
        assert findNodeValue(3, 2) == 1;
        assert findNodeValue(3, 3) == 1;
        assert findNodeValue(3, 4) == 0;
    }

}
