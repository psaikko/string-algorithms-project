import java.util.Random;

/**
 * Created by Paul on 1/25/14.
 */
public class StringTools {
    private static Random rand = new Random(1337);

    public static char[] createText(int length, char[] alphabet) {
        char[] string = new char[length];
        for (int i = 0; i < length; i++) {
            string[i] = alphabet[rand.nextInt(alphabet.length)];
        }
        return string;
    }

    public static char[][] getRandomSubstrings(int count, int patternLength, char[] text) {
        char[][] patterns = new char[count][patternLength];
        for (int i = 0; i < count; i++) {
            int patternStart = rand.nextInt(text.length - patternLength);
            System.arraycopy(text, patternStart, patterns[i], 0, patternLength);
        }
        return patterns;
    }
}
