/**
 * Created by Paul on 1/24/14.
 */
public class Main {
    public static void main(String[] args) {
        String text = "hishers";
        String[] patterns = {"he","his","she","hers"};
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        AhoCorasickMatcher matcher = new AhoCorasickMatcher(patterns, alphabet);

        for (Occurrence o : matcher.findOccurrences(text)) {
            System.out.println(o);
        }
    }
}
