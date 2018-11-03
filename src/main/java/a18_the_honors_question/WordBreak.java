package a18_the_honors_question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {
    public static List<String> wordBreak(String input, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        if (input.length() == 0 || wordDict.isEmpty())
            return result;
        int minLen = Integer.MAX_VALUE, maxLen = Integer.MIN_VALUE;
        Set<String> wordSet = new HashSet<>();
        for (String word : wordDict) {
            wordSet.add(word);
            minLen = Math.min(minLen, word.length());
            maxLen = Math.max(maxLen, word.length());
        }
        StringBuilder sentence = new StringBuilder();
        boolean[] failed = new boolean[input.length()]; // failed memo
        wordBreak(input, wordSet, minLen, maxLen, 0, failed, sentence, result);
        return result;
    }

    private static boolean wordBreak(String input, Set<String> wordSet, int minLen, int maxLen, int start,
            boolean[] failed, StringBuilder sentence, List<String> result) {
        if (start == input.length()) {
            sentence.setLength(sentence.length() - 1);
            result.add(sentence.toString());
            return true;
        }
        // break ealier
        if (failed[start])
            return false;
        boolean succeed = false;
        for (int i = start + minLen - 1; i < Math.min(input.length(), start + maxLen); i++) {
            String sub = input.substring(start, i + 1);
            if (wordSet.contains(sub)) {
                int sLen = sentence.length();
                sentence.append(sub).append(' ');
                if (wordBreak(input, wordSet, minLen, maxLen, i + 1, failed, sentence, result))
                    succeed = true;
                sentence.setLength(sLen); // back track
            }
        }
        failed[start] = !succeed;
        return succeed;
    }

    public static void main(String[] args) {
        String s = "pineapplepenapple";
        List<String> wordDict = Arrays.asList("apple", "pen", "applepen", "pine", "pineapple");
        assert wordBreak(s, wordDict).toString()
                .equals("[pine apple pen apple, pine applepen apple, pineapple pen apple]");
    }
}
