import java.util.LinkedList;
import java.util.List;

/**
 * Created by Paul on 2/11/14.
 *
 * Brute force solution used to verify correctness of other algorithms
 */
public class SimpleMatcher implements MultipleStringMatcher {

    char[][] patterns;

    public SimpleMatcher(char[][] patterns) {
        this.patterns = patterns;
    }

    @Override
    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        for (int i = 0; i < text.length; i++)
            for (int j = 0; j < patterns.length; j++) {
                for (int k = 0; k < patterns[j].length && k+i < text.length; k++) {
                    if (text[i+k] != patterns[j][k])
                        break;
                    else if (k == patterns[j].length - 1)
                        occurrences.add(new Occurrence(j, i+patterns[j].length-1));
                }

            }
        return occurrences;
    }
}
