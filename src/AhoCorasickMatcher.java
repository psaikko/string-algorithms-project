/**
 * Created by Paul on 1/24/14.
 */
import java.util.HashMap;
import java.util.Map;

public class AhoCorasickMatcher implements MultipleStringMatcher {


    public AhoCorasickMatcher(String[] patterns) {

    }

    private class ACNode {
        public ACNode fail = null;
        public Map<Character, ACNode> children = new HashMap();

    }
}
