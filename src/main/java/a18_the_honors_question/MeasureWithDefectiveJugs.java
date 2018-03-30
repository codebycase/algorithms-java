package a18_the_honors_question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MeasureWithDefectiveJugs {
	class Range {
		int low, high;

		public Range(int low, int high) {
			this.low = low;
			this.high = high;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || !(obj instanceof Range))
				return false;
			Range range = (Range) obj;
			return low == range.low && high == range.high;
		}

		@Override
		public int hashCode() {
			return Objects.hash(low, high);
		}
	}

	class Jug extends Range {
		public Jug(int low, int high) {
			super(low, high);
		}
	}

	public boolean checkFeasible(List<Jug> jugs, int L, int H) {
		Set<Range> cache = new HashSet<>();
		return checkFeasible(jugs, L, H, cache);
	}

	private boolean checkFeasible(List<Jug> jugs, int L, int H, Set<Range> cache) {
		if (L > H || cache.contains(new Range(L, H)) || (L < 0 && H < 0))
			return false;

		// check each jug to see if it is possible
		for (Jug jug : jugs) {
			// base case: jug is in [L, H]
			if ((L < jug.low && jug.high < H))
				return true;
			if (checkFeasible(jugs, L - jug.low, H - jug.high, cache))
				return true;
		}

		// marks this range as impossible
		cache.add(new Range(L, H));

		return false;
	}

	public static void main(String[] args) {
		MeasureWithDefectiveJugs solution = new MeasureWithDefectiveJugs();
		List<Jug> jugs = new ArrayList<>();
		jugs.add(solution.new Jug(230, 240));
		jugs.add(solution.new Jug(290, 310));
		jugs.add(solution.new Jug(500, 515));
		assert solution.checkFeasible(jugs, 2100, 2300);
		assert solution.checkFeasible(jugs, 2100, 2150) == false;
	}
}
