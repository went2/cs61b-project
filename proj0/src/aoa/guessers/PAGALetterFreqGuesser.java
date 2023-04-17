package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {

        List<Character> list = getListFromString(pattern);
        List<Character> wrongCharList = new ArrayList<>(List.copyOf(guesses));
        wrongCharList.removeAll(list);

        List<String> wordList = getMatchedWordList(pattern);
        List<String> newWordList = removeEleContainsChar(wordList, wrongCharList);
        System.out.println(wordList);


        Map<Character, Integer> freqMap = getFrequencyMap(newWordList);
        Map<Character, Integer> newMap = removeSpecificKeys(freqMap, guesses);
        System.out.println(newMap);
        System.out.println(getMaxValueKey(newMap));

        return getMaxValueKey(newMap);
    }

    // transform "--a-" to ['a']
    private List<Character> getListFromString(String str) {
        List<Character> list = new ArrayList<>();
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if(c != '-') {
                list.add(c);
            }
        }
        return list;
    }

    private List<String> removeEleContainsChar(List<String> wordList, List<Character> charList) {
        if(charList.size() == 0) {
            return wordList;
        }

        List<String> newList = new ArrayList<>();
        for(String word : wordList) {
            for(char c : charList) {
                if(word.indexOf(c) == -1) {
                    newList.add(word);
                }
            }
        }
        return newList;
    }

    private List<String> getMatchedWordList(String pattern) {
        // construct reg pattern from string eg: "--a-" to "..a."
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

    private Map<Character, Integer> removeSpecificKeys(Map<Character,Integer> originalMap,List<Character> list) {
        Map<Character, Integer> newMap = new TreeMap<>();
        Set<Character> keys = originalMap.keySet();
        list.forEach(keys::remove);
        for(char key : keys) {
            newMap.put(key, originalMap.get(key));
        }
        return newMap;
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
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/sorted_scrabble.txt");
        pagalfg.getGuess("sc--nc-", List.of('s', 'n', 'c'));
    }
}
