import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paul on 1/30/14.
 */
public class ShiftAndMatcher implements MultipleStringMatcher {
    long[][] B;
    int m;
    char[] alphabet;
    int w = 64; // length of machine word
    int wc; // number of machine words used
    long[] pBegin;
    long[] pEnd;

    public ShiftAndMatcher(char[][] patterns, char[] alphabet) {
        // treat patterns as one long pattern
        for (int i = 0; i < patterns.length; i++) {
            this.m += patterns[i].length;
        }

        this.wc = (m + w - 1)/w; // ceil(m / w)
        this.B = new long[256][wc];
        this.pBegin = new long[wc];
        this.pEnd = new long[wc];
        this.alphabet = alphabet;

        // remember start- and endpoints of individual patterns
        int tmp = 0;
        for (int i = 0; i < patterns.length; i++) {
            int l = patterns[i].length;
            pBegin[tmp / w]     |= (1L << (tmp % w));
            pEnd[(tmp + l - 1) / w] |= (1L << (tmp + l - 1) % w);
            tmp += l;
        }

        // mark i:th bit in B[][c] if pattern[i] == c
        tmp = 0;
        for (int i = 0; i < patterns.length; i++) {
            for (int j = 0; j < patterns[i].length; j++) {
                B[patterns[i][j]][tmp / w] += (1L << (tmp % w));
                ++tmp;
            }
        }
        //debugPreprocessing();
    }

    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        long[] D = new long[wc];
        int n = text.length;

        for (int i = 0; i < n; i++) {
            // adapt shift-and for multiple machine word bitstrings
            for (int j = wc - 1; j > 0; j--) {
                // fix for << 1 on word boundary
                long shiftD = (D[j] << 1);
                if ((D[j-1] & (1L << (w-1))) != 0) {
                    shiftD += 1;
                }
                // or bit of each pattern starting position instead of normal +1
                D[j] = (shiftD | pBegin[j]) & B[text[i]][j];
            }
            D[0] = ((D[0] << 1) | pBegin[0]) & B[text[i]][0];
            // see if D matches a pattern endpoint
            for (int j = 0; j < wc; j++) {
                if ((D[j] & pEnd[j]) != 0) {
                    occurrences.add(new Occurrence(-1, i));
                }
            }
        }
        return occurrences;
    }

    private void debugPreprocessing() {
        System.out.print("pBegin "+toBitString(pBegin)+"\n");
        System.out.print("pEnd   "+toBitString(pEnd)+"\n");

        for (char c : alphabet) {
            System.out.print(c);
            System.out.print(" "+toBitString(B[c]));
            System.out.println();
        }
        System.out.println();
    }

    private String toBitString(long[] ls) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < ls.length; j++) {
            for (int i = 0; i < w; i++) {
                sb.append(((ls[j] & (1L << i)) != 0) ? "1" : "0");
            }
            sb.append(" ");
        }
        return sb.toString();
    }
}
