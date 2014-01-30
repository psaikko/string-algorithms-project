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

    private void printTrie(Node root) {
        Deque<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node v = queue.removeFirst();
            System.out.print(String.format("id: %d, fail: %d, patterns: [ ", v.id, v.fail.id));
            for (Integer i : v.patterns)
                System.out.print(i + " ");
            System.out.println("]");
            for (Character c : v.children.keySet()) {
                Node child = v.children.get(c);
                System.out.println(String.format("  \"%s\", id: %d", c, child.id));
                queue.addLast(child);
            }
        }
    }

    public List<Occurrence> findOccurrences(char[] text) {
        List<Occurrence> occurrences = new LinkedList();
        Node v = patternTrieRoot;
        for (int j = 0; j < text.length; j++) {
            while (!v.children.containsKey(text[j])) {
                v = v.fail;
            }
            v = v.children.get(text[j]);
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
            while (j < p.length && v.children.containsKey(p[j])) {
                v = v.children.get(p[j]);
                j++;
            }
            while (j < p.length) {
                Node u = new Node();
                v.children.put(p[j], u);
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
            fallback.children.put(alphabet[i], root);
        }
        root.fail = fallback;
        Deque<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node u = queue.removeFirst();
            for (Character c : u.children.keySet()) {
                Node v = u.children.get(c);
                Node w = u.fail;
                while (!w.children.containsKey(c)) {
                    w = w.fail;
                }
                v.fail = w.children.get(c);
                v.patterns.addAll(v.fail.patterns);
                queue.addLast(v);
            }
        }
    }

    private static int nodecount = 0;

    private class Node {
        public int id = nodecount++;
        public Node fail = null;
        public Map<Character, Node> children = new HashMap();
        public Set<Integer> patterns = new HashSet();
    }
}
