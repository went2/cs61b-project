import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> charMap = new TreeMap<>();
        for(int i=0; i<26; i++) {
            charMap.put((char)('a' + i), i + 1);
        }
        return charMap;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> map1 = new TreeMap<>();
        if(!nums.isEmpty()) {
            for(int num : nums) {
                map1.put(num, num * num);
            }
        }
        return map1;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> map1 = new TreeMap<>();
        if(!words.isEmpty()) {
            for(String word : words) {
                if(map1.get(word) == null) {
                    map1.put(word, 1);
                } else {
                    map1.put(word, map1.get(word) + 1);
                }
            }
        }
        return map1;
    }
}