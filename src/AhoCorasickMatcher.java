/**
 * Created by Paul on 1/24/14.
 */
import java.util.*;

public class AhoCorasickMatcher implements MultipleStringMatcher {
    private Node patternTrieRoot = null;
    private char[] alphabet;

    public AhoCorasickMatcher(char[][] patterns, char[] alphabet) {
        this.alphabet = alphabet;
        this.patternTrieRoot = constructAutomaton(patterns);
    }

    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        Node v = patternTrieRoot;
        for (int j = 0; j < text.length; j++) {
            while (v.children[text[j]] == null) {
                v = v.fail;
            }
            v = v.children[text[j]];
            for (Integer i : v.patterns) {
                occurrences.add(new Occurrence(i, j));
            }
        }
        return occurrences;
    }

    private Node constructAutomaton(char[][] patterns) {
        Node root = new Node();
        for (int i = 0; i < patterns.length; i++) {
            Node v = root;
            char[] p = patterns[i];
            int j = 0;
            while (j < p.length && v.children[p[j]] != null) {
                v = v.children[p[j]];
                j++;
            }
            while (j < p.length) {
                Node u = new Node();
                v.children[p[j]] = u;
                v = u;
                j++;
            }
            v.patterns.add(i);
        }
        computeFail(root);
        return root;
    }

    private void computeFail(Node root) {
        Node fallback = new Node();
        for (int i = 0; i < alphabet.length; i++) {
            fallback.children[alphabet[i]] = root;
        }
        root.fail = fallback;
        Deque<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node u = queue.removeFirst();
            for (char c = 0; c < 256; c++) {
                Node v = u.children[c];
                    if (v != null) {
                    Node w = u.fail;
                    while (w.children[c] == null) {
                        w = w.fail;
                    }
                    v.fail = w.children[c];
                    v.patterns.addAll(v.fail.patterns);
                    queue.addLast(v);
                }
            }
        }
    }

    private static int nodecount = 0;

    private class Node {
        public int id = nodecount++;
        public Node fail = null;
        public Node[] children = new Node[256];
        public Set<Integer> patterns = new HashSet();
    }

    private void printTrie(Node root) {
        Deque<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node v = queue.removeFirst();
            System.out.print(String.format("id: %d, fail: %d, patterns: [ ", v.id, v.fail.id));
            for (Integer i : v.patterns)
                System.out.print(i + " ");
            System.out.println("]");
            for (char c = 0; c < 256; c++) {
                Node child = v.children[c];
                if (child != null) {
                    System.out.println(String.format("  \"%s\", id: %d", c, child.id));
                    queue.addLast(child);
                }
            }
        }
    }
}
