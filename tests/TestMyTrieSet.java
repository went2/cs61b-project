import org.junit.jupiter.api.Test;

import java.util.List;
import static com.google.common.truth.Truth.assertThat;
public class TestMyTrieSet {

    @Test
    public void testTrieInsert() {
        MyTrieSet trie = new MyTrieSet();
        trie.insert("a");
        trie.insert("a");
        trie.insert("awls");
        trie.insert("sad");
        trie.insert("same");
        trie.insert("sap");

        List<String> expect = List.of("a", "awls", "sad","same","sap");

        assertThat(trie.collect()).containsExactly("a", "awls", "sad","same","sap");
    }

    @Test
    public void testLongestPrefixOf() {
        MyTrieSet trie = new MyTrieSet();
        trie.insert("tries");
        trie.insert("cr");
        trie.insert("crysteal");

        String prefix = trie.longestPrefixOf("cryst");
        assertThat(prefix).isEqualTo("cryst");
    }
}
