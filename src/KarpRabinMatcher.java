import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Paul on 1/30/14.
 *
 * Implementation of the Karp-Rabin algorithm for multiple exact string matching.
 * The values for q and r are fairly unoptimized, q is a large prime.
 *
 * To deal with hash collisions or equal patterns, we store for every hash
 * a list of patterns that produce that value.
 */
public class KarpRabinMatcher implements MultipleStringMatcher {
    long q = 15485863;
    long r = 2;
    long s;
    int n;
    int m;

    Map<Long, List<Integer>> hps = new HashMap();
    char[][] patterns;

    // assume equal length patterns
    public KarpRabinMatcher(char[][] patterns) {
        this.patterns = patterns;
        m = patterns[0].length;
        s = mPow(r, m - 1);
        for (int j = 0; j < patterns.length; j++) {
            long hp = 0;
            for (int i = 0; i < m; i++)
                hp = mod((hp * r + patterns[j][i]), q);
            if (!hps.containsKey(hp)) {
                List<Integer> indices = new LinkedList<>();
                indices.add(j);
                hps.put(hp, indices);
            } else {
                hps.get(hp).add(j);
            }

        }
    }

    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        n = text.length;
        long ht = 0;
        for (int i = 0; i < m; i++)
            ht = mod((ht * r + text[i]), q);

        for (int i = 0; i < n-m; i++) {
            if (hps.containsKey(ht)) {
                for (Integer j : hps.get(ht)) {
                    if(eq(patterns[j], text, i))
                        occurrences.add(new Occurrence(j, i+m-1));
                }
            }
            ht = mod(((ht - text[i] * s)*r + text[i+m]), q);
        }
        if (hps.containsKey(ht)) {
            for (Integer j : hps.get(ht)) {
                if(eq(patterns[j], text, n-m))
                    occurrences.add(new Occurrence(j, n-1));
            }
        }

        return occurrences;
    }

    private long mod(long l, long q) {
        long m = l % q;
        return (m < 0 ? m + q : m);
    }

    private long mPow(long a, long b) {
        long pow = 1;
        while (--b >= 0) {
            pow = mod(pow * a, q);
        }
        return pow;
    }

    private boolean eq(char[] P, char[] T, int start) {
        for (int i = 0; i < P.length; i++)
            if (P[i] != T[i+start])
                return false;
        return true;
    }
}
