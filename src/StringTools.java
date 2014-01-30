import java.util.Random;

/**
 * Created by Paul on 1/25/14.
 */
public class StringTools {
    private static Random rand = new Random();

    public static String createText(int length, char[] alphabet) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            Character c = Character.valueOf(alphabet[rand.nextInt(alphabet.length)]);
            builder.append(c);
            if (((int)c.charValue()) > 255)
                System.out.println("derp: "+c);
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
