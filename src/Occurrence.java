/**
 * Created by Paul on 1/24/14.
 *
 * A wrapper class for (pattern, index) matches.
 * index denotes the text index where a match ends.
 */
public class Occurrence {
    public int pattern;
    public int index;

    public Occurrence(int pattern, int index) {
        this.pattern = pattern;
        this.index = index;
    }

    @Override
    public String toString() {
        return String.format("pattern %d at index %d", pattern, index);
    }
}
