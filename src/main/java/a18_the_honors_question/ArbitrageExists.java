package a18_the_honors_question;

import java.util.Arrays;
import java.util.Random;

public class ArbitrageExists {
	public static boolean isArbitrageExist(double[][] graph) {
		// transforms each edge's weight
		for (double[] edges : graph) {
			for (int i = 0; i < edges.length; i++) {
				edges[i] = -Math.log10(edges[i]);
			}
		}

		// use Bellman-Ford algorithm to find negative weight cycle
		double[] distances = new double[graph.length];
		Arrays.fill(distances, Double.MAX_VALUE);
		distances[0] = 0.0;
		// repeat on all edges until no more cost updates occur.
		for (int times = 1; times < graph.length; times++) {
			boolean haveUpdated = false;
			for (int i = 0; i < graph.length; i++) {
				for (int j = 0; j < graph.length; j++) {
					if (distances[i] != Double.MAX_VALUE && distances[j] > distances[i] + graph[i][j]) {
						haveUpdated = true;
						distances[j] = distances[i] + graph[i][j];
					}
				}
			}
			// no update in this iteration means no negative cycle
			if (!haveUpdated) {
				return false;
			}
		}

		// detects cycle if there is any further update
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (distances[i] != Double.MAX_VALUE && distances[i] > distances[i] + graph[i][j]) {
					return true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Random r = new Random();
		int n = r.nextInt(100) + 1;
		double[][] G = new double[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(G[i], 0.0);
		}
		// assume the input is a complete graph.
		for (int i = 0; i < G.length; ++i) {
			G[i][i] = 1.0;
			for (int j = i + 1; j < G.length; ++j) {
				G[i][j] = r.nextDouble();
				G[j][i] = 1.0 / G[i][j];
			}
		}
		boolean res = isArbitrageExist(G);
		System.out.println(res);
	}
}
