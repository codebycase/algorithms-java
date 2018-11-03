package a02_arrays_strings;

import java.util.ArrayList;
import java.util.List;

public class PalindromePermutation {
    public static List<String> generatePalindromes(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0)
            return result;
        int[] count = new int[128];
        for (int i = 0; i < s.length(); i++)
            count[s.charAt(i)]++;
        int odd = -1;
        for (int i = 0; i < count.length; i++) {
            if (count[i] % 2 != 0) {
                if (odd != -1) // found more than one odd!
                    return result;
                odd = i; // cache this odd char
            }
            count[i] = count[i] / 2; // half it!
        }
        StringBuilder temp = new StringBuilder();
        // just need to generate half length
        generatePalindromesDfs(result, temp, count, s.length() / 2, odd);
        return result;
    }

    private static void generatePalindromesDfs(List<String> result, StringBuilder builder, int[] count, int halfLen,
            int oddChar) {
        if (builder.length() == halfLen) {
            // replicate another half!
            if (oddChar != -1)
                builder.append((char) oddChar);
            for (int i = builder.length() - (oddChar == -1 ? 1 : 2); i >= 0; i--) {
                builder.append(builder.charAt(i));
            }
            result.add(builder.toString());
            return;
        }
        int prevLen = builder.length();
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                builder.append((char) (i));
                count[i]--;
                generatePalindromesDfs(result, builder, count, halfLen, oddChar);
                count[i]++;
                builder.setLength(prevLen);
            }
        }
    }

    public static void main(String[] args) {
        assert generatePalindromes("aababac").toString().equals("[aabcbaa, abacaba, baacaab]");
    }
}
