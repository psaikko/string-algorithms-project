import java.util.List;

/**
 * Created by Paul on 1/30/14.
 */
public interface MultipleStringMatcher {
    public abstract List<Occurrence> findOccurrences(char[] text);
}
