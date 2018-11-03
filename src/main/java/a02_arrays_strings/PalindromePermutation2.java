package a02_arrays_strings;

import java.util.ArrayList;
import java.util.List;

public class PalindromePermutation2 {
    public static List<String> generatePalindromes(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0)
            return result;
        int[] count = new int[128];
        for (char c : s.toCharArray()) {
            count[c]++;
        }
        int odd = -1;
        for (int i = 0; i < count.length; i++) {
            if (count[i] % 2 != 0) {
                if (odd != -1)
                    return result;
                odd = i;
            }
            count[i] /= 2;
        }
        StringBuilder builder = new StringBuilder();
        generatePalindromesDfs(result, builder, count, s.length() / 2, odd);
        return result;
    }

    private static void generatePalindromesDfs(List<String> result, StringBuilder builder, int[] count, int len,
            int odd) {
        if (builder.length() == len) {
            if (odd != -1)
                builder.append((char) odd);
            for (int i = builder.length() - (odd == -1 ? 1 : 2); i >= 0; i--) {
                builder.append(builder.charAt(i));
            }
            result.add(builder.toString());
            return;
        }
        int prevLen = builder.length();
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                builder.append((char) i);
                count[i]--;
                generatePalindromesDfs(result, builder, count, len, odd);
                count[i]++;
                builder.setLength(prevLen);
            }
        }

    }

    public static void main(String[] args) {

        assert generatePalindromes("aababac").toString().equals("[aabcbaa, abacaba, baacaab]");
    }
}
