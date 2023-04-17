package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character, Integer> resultMap = new TreeMap<>();
        for(String str : words) {
            int len = str.length();
            for(int i=0; i<len; i++) {
                char cha = str.charAt(i);
                resultMap.merge(cha, 1, Integer::sum);
            }
        }
        return resultMap;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // create map contained entries except keys in guesses list
        // guesses list is already alphabet sorted
        Map<Character, Integer> freqMap = this.getFrequencyMap();
        Map<Character, Integer> processedFreqMap = new TreeMap<>();
        Set<Character> keys = freqMap.keySet();
        guesses.forEach(keys::remove);
        for(char key : keys) {
            processedFreqMap.put(key, freqMap.get(key));
        }

        if(processedFreqMap.isEmpty()) {
            return '?';
        }

        // find key associated with max value in processedFreqMap
        Map.Entry<Character, Integer> maxEntry = null;
        for (Map.Entry<Character, Integer> entry : processedFreqMap.entrySet()) {
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0 ) {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
