package a18_the_honors_question;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of words and a length L, format the text such that each line has exactly L
 * characters and is fully (left and right) justified.
 * 
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each
 * line. Pad extra spaces ' ' when necessary so that each line has exactly L characters.
 * 
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces
 * on a line do not divide evenly between words, the empty slots on the left will be assigned more
 * spaces than the slots on the right.
 * 
 * For the last line of text, it should be left justified and no extra space is inserted between
 * words.
 * 
 * For example, words: ["This", "is", "an", "example", "of", "text", "justification."] L: 16.
 * 
 * Return the formatted lines as:
 * 
 * <pre>
[
   "This    is    an",
   "example  of text",
   "justification.  "
]
 * </pre>
 *
 */
public class TextJustification {
	public List<String> fullJustify(String[] words, int maxWidth) {
		List<String> result = new ArrayList<>();
		int start = 0, end = 0;
		while (start < words.length) {
			int count = words[start].length();
			// end is excluded!
			end = start + 1;
			while (end < words.length) {
				if (words[end].length() + count + 1 > maxWidth)
					break;
				count += words[end].length() + 1;
				end++;
			}

			StringBuilder builder = new StringBuilder();
			int gaps = end - start - 1;
			// left or middle justified
			if (end == words.length || gaps == 0) {
				for (int i = start; i < end; i++) {
					builder.append(words[i]);
					if (i < end - 1)
						builder.append(" ");
				}
				for (int i = builder.length(); i < maxWidth; i++) {
					builder.append(" ");
				}
			} else {
				int spaces = (maxWidth - count) / gaps;
				int rest = (maxWidth - count) % gaps;
				for (int i = start; i < end; i++) {
					builder.append(words[i]);
					if (i < end - 1) {
						builder.append(" ");
						for (int j = 0; j < spaces + (i - start < rest ? 1 : 0); j++)
							builder.append(" ");
					}
				}
			}
			result.add(builder.toString());
			start = end;
		}

		return result;
	}
}
