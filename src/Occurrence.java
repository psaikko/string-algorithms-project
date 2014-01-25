/**
 * Created by Paul on 1/24/14.
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
