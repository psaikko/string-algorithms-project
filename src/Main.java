import java.util.List;

/**
 * Created by Paul on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        randomTest();
    }

    public static void simpleTest() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String text = "hishers";
        String[] patterns = {"he", "his", "she", "hers"};
        ShiftAndMatcher matcher = new ShiftAndMatcher(patterns, alphabet);
        List<Occurrence> occurrences = matcher.findOccurrences(text);
        for (Occurrence o : occurrences)
            System.out.println(o);
    }

    public static void randomTest() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        String text = StringTools.createText(100000000, alphabet);
        String[] patterns = StringTools.getRandomSubstrings(100, 100, text);

        /*System.out.println(text);
        for (String p : patterns)
            System.out.println(p);
          */
        Long startTime = System.nanoTime();
        ShiftAndMatcher matcher = new ShiftAndMatcher(patterns, alphabet);
        Long midTime = System.nanoTime();
        List<Occurrence> occurrences = matcher.findOccurrences(text);
        Long endTime = System.nanoTime();

        /*for (Occurrence o : occurrences)
            System.out.println(o);    */

        System.out.println("found: "+occurrences.size());
        System.out.println("preprocess: "+ ((midTime - startTime) / 1000000) + " ms");
        System.out.println("matching: "+ ((endTime - midTime) / 1000000) + " ms");
        System.out.println("total: "+ ((endTime - startTime) / 1000000) + " ms");
    }
}
