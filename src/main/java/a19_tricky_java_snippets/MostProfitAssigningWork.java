package a19_tricky_java_snippets;

import java.util.Arrays;

import util.Point;

/**
 * We have jobs: difficulty[i] is the difficulty of the ith job, and profit[i] is the profit of the
 * ith job.
 * 
 * Now we have some workers. worker[i] is the ability of the ith worker, which means that this
 * worker can only complete a job with difficulty at most worker[i].
 * 
 * Every worker can be assigned at most one job, but one job can be completed multiple times.
 * 
 * For example, if 3 people attempt the same job that pays $1, then the total profit will be $3. If
 * a worker cannot complete any job, his profit is $0.
 * 
 * What is the most profit we can make?
 * 
 * Example 1:
 * 
 * Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
 * 
 * Output: 100
 * 
 * Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get profit of
 * [20,20,30,30] seperately.
 * 
 * @author lchen
 *
 */
public class MostProfitAssigningWork {
	public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
		int N = difficulty.length;
		Point[] jobs = new Point[N];
		for (int i = 0; i < N; ++i)
			jobs[i] = new Point(difficulty[i], profit[i]);
		Arrays.sort(jobs, (a, b) -> (a.x - b.x));
		Arrays.sort(worker);
		// Use a "two pointer" approach to process jobs in order and keep track of best
		int ans = 0, i = 0, best = 0;
		for (int skill : worker) {
			while (i < N && skill >= jobs[i].x)
				best = Math.max(best, jobs[i++].y);
			ans += best;
		}

		return ans;
	}
}
