import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paul on 1/30/14.
 */
public class ShiftOrMatcher {
    long[] B;
    int m;
    char[] alphabet;

    public ShiftOrMatcher(String pattern, char[] alphabet) {
        this.B = new long[255];
        this.m = pattern.length();
        this.alphabet = alphabet;

        for (int i = 0; i < m; i++) {
            B[pattern.charAt(i)] += (1 << i);
        }
        debugPreprocessing();
    }

    private void debugPreprocessing() {
        for (char c : alphabet) {
            System.out.println(c + " " + toBitString(B[c]));
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
        long D = 0;
        int n = text.length();
        for (int i = 0; i < n; i++) {
            D = ((D << 1) + 1) & B[text.charAt(i)];

            //System.out.println(text.charAt(i) + " " + toBitString(D));

            if ((D & (1 << (m-1))) != 0)
                occurrences.add(new Occurrence(0, i));
        }
        return occurrences;
    }
}
