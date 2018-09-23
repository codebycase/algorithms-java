package a01_fundamentals;

import java.util.ArrayList;
import java.util.List;

public class NumberBootCamp {

	public List<Integer> lexicalOrder(int n) {
		List<Integer> res = new ArrayList<>();
		for (int i = 1; i < 10; ++i) {
			lexicalOrderDfs(i, n, res);
		}
		return res;
	}

	public void lexicalOrderDfs(int cur, int n, List<Integer> res) {
		if (cur > n)
			return;
		else {
			res.add(cur);
			for (int i = 0; i < 10; ++i) {
				lexicalOrderDfs(10 * cur + i, n, res);
			}
		}
	}

	public int excelColumnToNumber(String s) {
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			ans += ((s.charAt(i) - 'A') + 1) * Math.pow(26, s.length() - i - 1);
		}
		return ans;
	}
}
