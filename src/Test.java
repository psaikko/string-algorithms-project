import java.util.List;

/**
 * Created by Paul on 2/11/14.
 *
 * Some rudimentary tests for the implemented algorithms.
 * Algorithms are compared to the naïve brute-force implementation in SimpleMatcher.java
 * since it is easy to see that it produces the correct result.
 */
public class Test {
    public static void runAll() {
        boolean success = singlePatternTest() &&
                          longPatternsTest() &&
                          shortPatternsTest() &&
                          manyPatternsTest() &&
                          smallAlphabetTest() &&
                          largeAlphabetTest();
        System.out.println();
        if (success) {
            System.out.println("All tests passed");
        } else {
            System.out.println("Some tests failed");
        }
    }

    private static boolean manyPatternsTest() {
        return testAlgorithms("many patterns", 64,
                100000, 100, 100);
    }

    private static boolean smallAlphabetTest() {
        return testAlgorithms("small alphabet", 3,
                100000, 100, 100);
    }

    private static boolean largeAlphabetTest() {
        return testAlgorithms("large alphabet", MultipleStringMatcher.ALPHABET_MAX,
                100000, 100, 100);
    }

    public static boolean singlePatternTest() {
        return testAlgorithms("single pattern", 64,
                100000, 1, 10000);
    }

    public static boolean longPatternsTest() {
        return testAlgorithms("long patterns", 64,
                100000, 10, 1000);
    }

    public static boolean shortPatternsTest() {
        return testAlgorithms("short patterns", 64,
                100000, 10, 4);
    }

    private static boolean testAlgorithms(String test, int alphabetSize, int textLength,
                                          int patternCount, int patternLength) {
        char[] text;
        char[][] patterns;
        List<Occurrence> occurrences;
        char[] alphabet = new char[alphabetSize];
        for (char c = 0; c < alphabetSize; c++) alphabet[c] = c;

        text = StringTools.createText(textLength, alphabet);
        patterns = StringTools.getRandomSubstrings(patternCount, patternLength, text);
        occurrences = new SimpleMatcher(patterns).findOccurrences(text);
        boolean krResult = checkOccurrences(occurrences, new KarpRabinMatcher(patterns).findOccurrences(text), text.length);
        boolean acResult = checkOccurrences(occurrences, new AhoCorasickMatcher(patterns, alphabet).findOccurrences(text), text.length);
        boolean saResult = checkOccurrences(occurrences, new ShiftAndMatcher(patterns, alphabet).findOccurrences(text), text.length);

        printResult(test, krResult, acResult, saResult);

        return krResult && acResult && saResult;
    }

    private static void printResult(String test, boolean kr, boolean ac, boolean sa) {
        System.out.println("Test: "+test);
        System.out.println("\tKarp-Rabin: "+(kr ? "PASS" : "FAIL"));
        System.out.println("\tAho-Corasick: "+(ac ? "PASS" : "FAIL"));
        System.out.println("\tShift-And: "+(sa ? "PASS" : "FAIL"));
    }

    private static boolean checkOccurrences(List<Occurrence> a, List<Occurrence> b, int len) {
        int[] counts = new int[len+1];
        for (Occurrence o : a)
            counts[o.index]++;
        for (Occurrence o : b)
            counts[o.index]--;
        for (int i = 0; i < len; i++)
            if (counts[i] != 0)
                return false;
        return true;
    }
}
