import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Paul on 1/30/14.
 */
public class KarpRabinMatcher implements MultipleStringMatcher {
    long q = 15485863;
    long r = 2;
    long s;
    int n;
    int m;

    Map<Long, Integer> hps = new HashMap();
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
            hps.put(hp, j);
        }
    }

    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        n = text.length;
        long ht = 0;
        for (int i = 0; i < m; i++)
            ht = mod((ht * r + text[i]), q);

        for (int i = 0; i < n-m; i++) {
            if (hps.containsKey(ht) && eq(patterns[hps.get(ht)], text, i))
                occurrences.add(new Occurrence(hps.get(ht), i));
            ht = mod(((ht - text[i] * s)*r + text[i+m]), q);
        }
        if (hps.containsKey(ht) && eq(patterns[hps.get(ht)], text, n-m))
            occurrences.add(new Occurrence(hps.get(ht), n-m));
        System.out.println(failures);

        return occurrences;
    }

    private long mod(long l, long q) {
        long m = l % q;
        return (m < 0 ? m + q : m);
    }

    private long mPow(long a, long b) {
        if (b == 0) return 1;
        return mod(a* mPow(a, b - 1),q);
    }

    int failures = 0;

    private boolean eq(char[] P, char[] T, int start) {
        failures++;
        for (int i = 0; i < P.length; i++)
            if (P[i] != T[i+start])
                return false;
        return true;
    }
}
