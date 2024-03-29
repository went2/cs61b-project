package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        Map<Character, Integer> patternedMap = getFreMapThatMatchesPattern(pattern);
        Map<Character, Integer> processedFreqMap = filterSpecificKeys(patternedMap, guesses);

        return getMaxValueKey(processedFreqMap);
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

    private Map<Character, Integer> filterSpecificKeys(Map<Character,Integer> originalMap,List<Character> list) {
        Map<Character, Integer> processedMap = new TreeMap<>();
        Set<Character> keys = originalMap.keySet();
        list.forEach(keys::remove);
        for(char key : keys) {
            processedMap.put(key, originalMap.get(key));
        }
        return processedMap;
    }

    // get the matched words list
    private List<String> getWordsThatMatchesPattern(String pattern) {
        // construct reg pattern from string eg: "__a_" to "..a."
        String regPattern = pattern.replace('-', '.');

        List<String> matchedWords = new ArrayList<>();
        for(String word : words) {
            if(word.matches(regPattern)) {
                matchedWords.add(word);
            }
        }
        return matchedWords;
    }

    private Map<Character, Integer> getFrequencyMap(List<String> wordList) {
        Map<Character, Integer> resultMap = new TreeMap<>();
        for(String word : wordList) {
            int len = word.length();
            for(int i=0; i<len; i++) {
                char cha = word.charAt(i);
                resultMap.merge(cha, 1, Integer::sum);
            }
        }
        return resultMap;
    }

    private Map<Character, Integer> getFreMapThatMatchesPattern(String pattern) {

        List<String> wordList = getWordsThatMatchesPattern(pattern);

        return getFrequencyMap(wordList);
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));

        System.out.println(palfg.getFreMapThatMatchesPattern("-e--"));
    }
}