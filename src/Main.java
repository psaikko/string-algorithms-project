import sun.net.www.content.image.jpeg;

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
        int alphabet_size = 2;
        char[] alphabet = new char[alphabet_size];
        for (char i = 0; i < alphabet_size; i++) alphabet[i] = i;
        char[] text = StringTools.createText(1000000, alphabet);
        char[][] patterns = StringTools.getRandomSubstrings(10, 10, text);

        for (int i = 0; i < patterns.length; i++) {
            for (int j = 0; j < patterns[0].length; j++)
                System.out.print((int)patterns[i][j]+" ");
            System.out.println();
        }

        MultipleStringMatcher matcher;
        List<Occurrence> occurrences;

        startTime = System.nanoTime();
        matcher = new ShiftAndMatcher(patterns, alphabet);
        midTime = System.nanoTime();
        occurrences = matcher.findOccurrences(text);
        endTime = System.nanoTime();
        printStats("Shift-And", startTime, midTime, endTime, occurrences.size());
                /*
        int j = 10;
        for (Occurrence o : occurrences) {
            if (j-- < 0) break;
            System.out.print(o.index + " ");
            for (int i = 9; i >= 0; i--)
                System.out.print((int)text[o.index - i] + " ");
            System.out.println();
        }
                  */
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
