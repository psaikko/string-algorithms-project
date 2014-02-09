import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    public static char[] fromFile(String filename, int length) throws Exception {
        BufferedReader br;
        try {
            File dataFile = new File(filename);
            FileReader fr = new FileReader(dataFile);
            br = new BufferedReader(fr);
        } catch (Exception e) {
            throw new Exception("could not open data file \""+filename+"\"");
        }
        char[] text = new char[length];
        int result = br.read(text, 0 ,length);
        br.close();
        if (result != length)
            throw new Exception("data file \"+filename\" too short for text of length "+length);
        // deal with strange characters
        for (int i = 0; i < length; i++)
            text[i] %= 256;
        return text;
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
