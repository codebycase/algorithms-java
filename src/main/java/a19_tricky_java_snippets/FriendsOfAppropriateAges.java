package a19_tricky_java_snippets;

import java.util.Arrays;

/**
 * Some people will make friend requests. The list of their ages is given and ages[i] is the age of
 * the ith person.
 * 
 * Person A will NOT friend request person B (B != A) if any of the following conditions are true:
 * 
 * age[B] <= 0.5 * age[A] + 7 age[B] > age[A] age[B] > 100 && age[A] < 100 Otherwise, A will friend
 * request B.
 * 
 * Note that if A requests B, B does not necessarily request A. Also, people will not friend request
 * themselves.
 * 
 * How many total friend requests are made?
 * 
 * <pre>
Example 1:

Input: [16,16]
Output: 2
Explanation: 2 people friend request each other.
Example 2:

Input: [16,17,18]
Output: 2
Explanation: Friend requests are made 17 -> 16, 18 -> 17.
 * </pre>
 * 
 * @author lchen
 *
 */
public class FriendsOfAppropriateAges {
	public int numFriendRequests(int[] ages) {
		int maxAge = Arrays.stream(ages).max().getAsInt();
		int[] countAges = new int[maxAge + 1];
		for (int age : ages) {
			countAges[age]++;
		}
		int ans = 0;
		for (int ageA = 0; ageA <= maxAge; ageA++) {
			if (countAges[ageA] > 0) {
				for (int ageB = 0; ageB <= maxAge; ageB++) {
					if (countAges[ageB] > 0) {
						if (ageB <= 0.5 * ageA + 7)
							continue;
						if (ageB > ageA)
							continue;
						// no need to check ageB > 100 && ageA < 100
						ans += countAges[ageA] * countAges[ageB];
						if (ageA == ageB)
							ans -= countAges[ageA];
					}
				}
			}
		}
		return ans;
	}

}
