import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Paul on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 5) throw new Exception();
            final String dataSet = args[0];

            final int textLength;
            if ((textLength = Integer.parseInt(args[1])) < 1)
                throw new Exception("textLength: not a positive integer");

            final int patternCount;
            if ((patternCount = Integer.parseInt(args[2])) < 1)
                throw new Exception("patternCount: not a positive integer");

            final int patternLength;
            if ((patternLength = Integer.parseInt(args[3])) < 1)
                throw new Exception("patternLength: not a positive integer");

            final String algorithm = args[4];

            char[] text;
            char[] alphabet = null;
            if (dataSet.startsWith("Random")) {
                int alphabetSize = Integer.parseInt(dataSet.substring(6, dataSet.length()));
                alphabet = new char[alphabetSize];
                for (char i = 0; i < alphabetSize; i++) alphabet[i] = i;
                text = StringTools.createText(textLength, alphabet);
            } else {
                alphabet = new char[256];
                for (char i = 0; i < 256; i++) alphabet[i] = i;
                text = StringTools.fromFile(dataSet, textLength);
            }

            char[][] patterns = StringTools.getRandomSubstrings(patternCount, patternLength, text);

            MultipleStringMatcher matcher;
            long startTime, endTime;
            switch(algorithm) {
                case "AhoCorasick":
                    startTime = System.nanoTime();
                    matcher = new AhoCorasickMatcher(patterns, alphabet);
                    endTime = System.nanoTime();
                    break;
                case "KarpRabin":
                    startTime = System.nanoTime();
                    matcher = new KarpRabinMatcher(patterns);
                    endTime = System.nanoTime();
                    break;
                case "ShiftAnd":
                    startTime = System.nanoTime();
                    matcher = new ShiftAndMatcher(patterns, alphabet);
                    endTime = System.nanoTime();
                    break;
                default:
                    throw new Exception("algorithm: not one of {AhoCorasick,KarpRabin,ShiftAnd}");
            }

            System.out.println("Preprocessing: "+(endTime - startTime));

            List<Occurrence> occurrences;
            startTime = System.nanoTime();
            occurrences = matcher.findOccurrences(text);
            endTime = System.nanoTime();

            System.out.println("Searching: "+(endTime - startTime));
            System.out.println("Matches: "+occurrences.size());
        } catch(Exception e) {
            System.out.println("Exception: "+e.getMessage());
            e.printStackTrace();
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("Help:");
        System.out.println("StringProcessingProject <dataSet> <textLength> <patternCount> <patternLength> <algorithm>");
        System.out.println("dataSet:");
        System.out.println("\tPath to a plaintext file or");
        System.out.println("\tRandom<X> where 2 <= X <= 256 is the alphabet size");
        System.out.println("textLength: text length to use, must fit in memory");
        System.out.println("patternCount: number of patterns");
        System.out.println("patternLength: length of patterns");
        System.out.println("algorithm: one of");
        System.out.println("\tShiftAnd");
        System.out.println("\tAhoCorasick");
        System.out.println("\tKarpRabin");
        System.out.println();
        System.out.println("Example: StringProcessingProject Random64 1000000 32 8 ShiftAnd");
    }
}
