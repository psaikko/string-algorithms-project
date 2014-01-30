import java.util.List;

/**
 * Created by Paul on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        randomTest();
    }

    public static void randomTest() {
        Long startTime, midTime, endTime;
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] text = StringTools.createText(100000000, alphabet);
        char[][] patterns = StringTools.getRandomSubstrings(50, 50, text);

        System.out.println("Shift-And\n");
        startTime = System.nanoTime();
        MultipleStringMatcher matcher = new ShiftAndMatcher(patterns, alphabet);
        midTime = System.nanoTime();
        System.out.println("preprocess: "+ ((midTime - startTime) / 1000000) + " ms");
        List<Occurrence> occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        System.out.println("matching: "+ ((endTime - midTime) / 1000000) + " ms");
        System.out.println("total: "+ ((endTime - startTime) / 1000000) + " ms");
        System.out.println("found: "+occurrences.size());

        System.out.println("\n");

        System.out.println("Aho-Corasick\n");
        startTime = System.nanoTime();
        matcher = new AhoCorasickMatcher(patterns, alphabet);
        midTime = System.nanoTime();
        System.out.println("preprocess: "+ ((midTime - startTime) / 1000000) + " ms");
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        System.out.println("matching: "+ ((endTime - midTime) / 1000000) + " ms");
        System.out.println("total: "+ ((endTime - startTime) / 1000000) + " ms");
        System.out.println("found: "+occurrences.size());

        System.out.println("\n");

        System.out.println("Karp-Rabin\n");
        startTime = System.nanoTime();
        matcher = new KarpRabinMatcher(patterns);
        midTime = System.nanoTime();
        System.out.println("preprocess: "+ ((midTime - startTime) / 1000000) + " ms");
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        System.out.println("matching: "+ ((endTime - midTime) / 1000000) + " ms");
        System.out.println("total: "+ ((endTime - startTime) / 1000000) + " ms");
        System.out.println("found: "+occurrences.size());
    }
}
