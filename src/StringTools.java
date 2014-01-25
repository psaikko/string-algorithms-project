import java.util.Random;

/**
 * Created by Paul on 1/25/14.
 */
public class StringTools {
    private static Random rand = new Random(1337);
    public static String createText(int length, char[] alphabet) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(Character.valueOf(alphabet[rand.nextInt(alphabet.length)]));
        }
        return builder.toString();
    }

    public static String[] getRandomSubstrings(int count, int patternLength, String text) {
        String[] patterns = new String[count];
        for (int i = 0; i < count; i++) {
            int patternStart = rand.nextInt(text.length() - patternLength);
            patterns[i] = text.substring(patternStart, patternStart + patternLength);
        }
        return patterns;
    }
}
