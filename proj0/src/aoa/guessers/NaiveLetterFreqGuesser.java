package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        Map<Character, Integer> patternedMap = getFreMapThatMatchesPattern(pattern);
        Map<Character, Integer> processedFreqMap = filterSpecificKeys(patternedMap, guesses);

        char result = getMaxValueKey(processedFreqMap);
        if(result == '?') return getGuess(guesses);
        return result;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // freqMap is already alphabet sorted
        Map<Character, Integer> freqMap = this.getFrequencyMap();
        Map<Character, Integer> processedFreqMap = filterSpecificKeys(freqMap, guesses);

        return getMaxValueKey(processedFreqMap);
    }

    // similar with getFrequencyMap, but based on pattern matched words list
    private Map<Character, Integer> getFreMapThatMatchesPattern(String pattern) {
        // construct reg pattern from string eg: "__a_" to "..a."
        String regPattern = pattern.replace('-', '.');
        // System.out.println("regPattern: " + regPattern);
        // get the matched words list
        List<String> resultWords = new ArrayList<>();
        for(String word : words) {
            if(word.matches(regPattern)) {
                resultWords.add(word);
            }
        }
        Map<Character, Integer> resultMap = new TreeMap<>();
        for(String str : resultWords) {
            int len = str.length();
            for(int i=0; i<len; i++) {
                char cha = str.charAt(i);
                resultMap.merge(cha, 1, Integer::sum);
            }
        }

        if(resultMap.isEmpty()) {
            return getFrequencyMap();
        }
        return resultMap;
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


    /* exclude the specified keys in a map */
    private Map<Character, Integer> filterSpecificKeys(Map<Character,Integer> originalMap,List<Character> list) {
        Map<Character, Integer> processedMap = new TreeMap<>();
        Set<Character> keys = originalMap.keySet();
        list.forEach(keys::remove);
        for(char key : keys) {
            processedMap.put(key, originalMap.get(key));
        }
        return processedMap;
    }

    private char getMaxValueKey(Map<Character,Integer> freqMap) {
        if(freqMap.isEmpty()) {
            return '?';
        }
        // find key associated with max value in a Map
        Map.Entry<Character, Integer> maxEntry = null;
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
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
//        System.out.println("guess: " + nlfg.getGuess(guesses));
        System.out.println("patterned guess :" + nlfg.getGuess("a___", guesses));
    }
}
