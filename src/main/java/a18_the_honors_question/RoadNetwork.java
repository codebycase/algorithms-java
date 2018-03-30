package a18_the_honors_question;

import java.util.Arrays;

public class RoadNetwork {
	static class Section {
		int x, y;
		double distance;

		public Section(int x, int y, double distance) {
			this.x = x;
			this.y = y;
			this.distance = distance;
		}

		@Override
		public String toString() {
			return x + "->" + y + ": " + distance;
		}
	}

	public static Section findBestProposals(Section[] H, Section[] P, int n) {
		// graph stores the shortest path distances between all pairs of vertices.
		double[][] graph = new double[n][n];
		// prepare the graph in favor of Floyd Warshall algorithm
		for (int i = 0; i < n; i++) {
			Arrays.fill(graph[i], Double.MAX_VALUE);
		}
		for (int i = 0; i < n; i++) {
			graph[i][i] = 0.0; // self
		}
		// build an undirected graph based on existing sections
		for (Section h : H) {
			graph[h.x][h.y] = h.distance;
			graph[h.y][h.x] = h.distance;
		}

		// perform Floyd-Warshall algorithm: O(n^3)
		for (int k = 0; k < graph.length; k++) {
			for (int i = 0; i < graph.length; i++) {
				for (int j = 0; j < graph.length; j++) {
					if (graph[i][k] != Double.MAX_VALUE && graph[k][j] != Double.MAX_VALUE) {
						graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
					}
				}
			}
		}

		// examines each proposal for shorter distance for all pairs.
		double bestDistanceSaving = Double.MIN_VALUE;
		Section bestProposal = new Section(-1, -1, 0.0);
		for (Section p : P) {
			double proposalSaving = 0.0;
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < n; b++) {
					double saving = graph[a][b] - (graph[a][p.x] + p.distance + graph[p.y][b]);
					proposalSaving += saving > 0.0 ? saving : 0.0;
				}
			}
			if (proposalSaving > bestDistanceSaving) {
				bestDistanceSaving = proposalSaving;
				bestProposal = p;
			}
		}

		return bestProposal;
	}

	public static void main(String[] args) {
		Section[] H = new Section[] { new Section(0, 1, 10), new Section(1, 2, 10), new Section(2, 3, 10) };
		Section[] P = new Section[] { new Section(0, 3, 1), new Section(0, 2, 2), new Section(0, 1, 3) };
		Section best = findBestProposals(H, P, 4);
		assert (best.x == 0 && best.y == 3 && best.distance == 1.0);
	}
}
