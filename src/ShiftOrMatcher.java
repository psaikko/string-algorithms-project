import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paul on 1/30/14.
 */
public class ShiftOrMatcher {
    long[][] B;
    int m;
    char[] alphabet;
    int w = 64; // length of machine word
    int wc; // number of machine words used

    public ShiftOrMatcher(String pattern, char[] alphabet) {
        this.m = pattern.length();
        this.wc = (m + w - 1)/w; // ceil(m / w)
        this.B = new long[wc][255];
        this.alphabet = alphabet;

        // mark i:th bit in B[][c] if pattern[i] == c
        for (int i = 0; i < m; i++) {
            B[i / w][pattern.charAt(i)] += (1L << i);
        }

        System.out.println(pattern);
        debugPreprocessing();
    }

    private void debugPreprocessing() {
        for (char c : alphabet) {
            System.out.print(c);
            for (int i = 0; i < wc; i++)
                System.out.print(" "+toBitString(B[i][c]));
            System.out.println();
        }
        System.out.println();
    }

    private String toBitString(long l) {
        StringBuilder sb = new StringBuilder();
        long bit = 1;
        for (int i = 0; i < 64; i++) {
            sb.append(((l & (bit << i)) != 0) ? "1" : "0");
        }
        return sb.toString();
    }

    public List<Occurrence> findOccurrences(String text) {
        List<Occurrence> occurrences = new LinkedList();
        long[] D = new long[wc];
        int n = text.length();

        for (int i = 0; i < n; i++) {
            // adapt shift-and for multiple machine word bitstrings
            for (int j = wc - 1; j >= 0; j--) {
                if (j > 0 && (D[j-1] & (1L << 63)) != 0)
                    D[j] += 1;
                D[j] = ((D[j] << 1) + 1) & B[j][text.charAt(i)];
            }

            //System.out.println(text.charAt(i) + " " + toBitString(D));

            if ((D[wc-1] & (1L << ((m % w)-1))) != 0)
                occurrences.add(new Occurrence(0, i));
        }
        return occurrences;
    }
}
