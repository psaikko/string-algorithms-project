import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paul on 1/30/14.
 *
 * Implements the Shift-And algorithm for multi-word bitstrings and multiple patterns.
 * This implementation does not make note of which pattern is matched, only the index
 * of the match.
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
        this.B = new long[MultipleStringMatcher.ALPHABET_MAX][wc];
        this.pBegin = new long[wc];
        this.pEnd = new long[wc];
        this.alphabet = alphabet;

        // remember start- and endpoints of individual patterns
        int tmp = 0;
        for (int i = 0; i < patterns.length; i++) {
            int l = patterns[i].length;
            pBegin[tmp / w]         |= (1L << (tmp % w));
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
            // adapt shift-and for multiple machine word bitstrings, multiple patterns
            D = and(or(lShift(D), pBegin), B[text[i]]);

            // see if D matches a pattern endpoint
            int matches = bitCountAnd(D, pEnd);

            for (int j = 0; j < matches; j++)
                occurrences.add(new Occurrence(-1, i));
        }
        return occurrences;
    }

    private long[] or(long[] A, long[] B) {
        for (int i = 0; i < wc; i++)
            A[i] |= B[i];
        return A;
    }

    private long[] and(long[] A, long[] B) {
        for (int i = 0; i < wc; i++)
            A[i] &= B[i];
        return A;
    }

    private final long lastBit = 1L << (w-1);
    private long[] lShift(long[] A) {
        for (int i = wc - 1; i > 0; i--) {
            A[i] <<= 1;
            if ((A[i - 1] & lastBit) != 0)
                A[i] += 1;
        }
        A[0] <<= 1;
        return A;
    }

    private int bitCountAnd(long[] A, long[] B) {
        int count = 0;
        for (int i = 0; i < wc; i++)
            count += Long.bitCount(A[i] & B[i]);
        return count;
    }
}
