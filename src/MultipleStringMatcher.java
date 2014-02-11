import java.util.List;

/**
 * Created by Paul on 1/30/14.
 *
 * Interface for multiple exact string matching algorithm implementations
 */
public interface MultipleStringMatcher {
    public static int ALPHABET_MAX = 256;
    public abstract List<Occurrence> findOccurrences(char[] text);
}
