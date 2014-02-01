import java.util.List;
import java.util.Scanner;

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
        char[] text = StringTools.createText(10000000, alphabet);
        char[][] patterns = StringTools.getRandomSubstrings(100, 100, text);
        MultipleStringMatcher matcher;
        List<Occurrence> occurrences;

        startTime = System.nanoTime();
        matcher = new ShiftAndMatcher(patterns, alphabet);
        midTime = System.nanoTime();
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        printStats("Shift-And", startTime, midTime, endTime, occurrences.size());

        startTime = System.nanoTime();
        matcher = new AhoCorasickMatcher(patterns, alphabet);
        midTime = System.nanoTime();
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        printStats("Aho-Corasick", startTime, midTime, endTime, occurrences.size());

        startTime = System.nanoTime();
        matcher = new KarpRabinMatcher(patterns);
        midTime = System.nanoTime();
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        printStats("Karp-Rabin",startTime, midTime, endTime, occurrences.size());

    }

    public static void printStats(String name, long start, long mid, long end, int count) {
        System.out.println(name+"\n");
        System.out.println("preprocess: "+ ((mid - start) / 1000000) + " ms");
        System.out.println("matching: "+ ((end - mid) / 1000000) + " ms");
        System.out.println("total: "+ ((end - start) / 1000000) + " ms");
        System.out.println("found: "+count);
        System.out.println();
    }
}
