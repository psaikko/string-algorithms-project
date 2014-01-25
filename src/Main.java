import java.util.List;

/**
 * Created by Paul on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        //String text = "hishers";
        //String[] patterns = {"he","his","she","hers"};

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        String text = StringTools.createText(100000000, alphabet);
        String[] patterns = StringTools.getRandomSubstrings(1000, 1000, text);

        Long startTime = System.nanoTime();
        AhoCorasickMatcher matcher = new AhoCorasickMatcher(patterns, alphabet);
        Long midTime = System.nanoTime();
        List<Occurrence> occurrences = matcher.findOccurrences(text);
        Long endTime = System.nanoTime();

        /*for (Occurrence o : occurrences) {
            System.out.println(o);
        } */

        System.out.println(occurrences.size());
        System.out.println("preprocess: "+ ((midTime - startTime) / 1000000) + " ms");
        System.out.println("matching: "+ ((endTime - midTime) / 1000000) + " ms");
        System.out.println("total: "+ ((endTime - startTime) / 1000000) + " ms");
    }
}
