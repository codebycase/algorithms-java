package a18_the_honors_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.

Examples: 
"123", 6 -> ["1+2+3", "1*2*3"] 
"232", 8 -> ["2*3+2", "2+3*2"]
"105", 5 -> ["1*0+5","10-5"]
"00", 0 -> ["0+0", "0-0", "0*0"]
"3456237490", 9191 -> []
 * </pre>
 * 
 * @author lchen
 *
 */
public class ExpressionAddOperators {
	public List<String> addOperators(String num, int target) {
		List<String> result = new ArrayList<>();
		if (num == null || num.length() == 0)
			return result;
		helper(result, "", num, target, 0, 0, 0);
		return result;
	}

	private void helper(List<String> result, String path, String num, int target, int pos, long eval, long multied) {
		if (pos == num.length()) {
			if (target == eval)
				result.add(path);
			return;
		}
		for (int i = pos; i < num.length(); i++) {
			// when starts with 0, only one digit is valid!
			if (num.charAt(pos) == '0' && i != pos)
				break;
			long cur = Long.parseLong(num.substring(pos, i + 1));
			if (pos == 0) {
				helper(result, path + cur, num, target, i + 1, cur, cur);
			} else {
				helper(result, path + "+" + cur, num, target, i + 1, eval + cur, cur);
				helper(result, path + "-" + cur, num, target, i + 1, eval - cur, -cur);
				helper(result, path + "*" + cur, num, target, i + 1, eval - multied + multied * cur, multied * cur);
			}
		}
	}

}
