package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        if(wordLength < 0) {
            throw new IllegalArgumentException("wordLength should be bigger than 0");
        }

        List<String> wordList = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        int numWords = wordList.size();
        if(numWords == 0) {
            throw new IllegalStateException("no length " + wordLength + " word found. Please try again.");
        }

        int randomlyChosenWordIndex = StdRandom.uniform(numWords);
        chosenWord = wordList.get(randomlyChosenWordIndex);
        // initiate pattern
        pattern = "-".repeat(wordLength);
    }

    // return the number of the occurrences of the guessed letter
    @Override
    public int makeGuess(char letter) {
        int occurs = 0;

        // { 0: 'o', 2: 'o' }
        // key: index of char, value: the char revealed
        Map<Integer, Character> revealedChar = new TreeMap<>();
        for(int i=0; i<chosenWord.length(); i++) {
            if(letter == chosenWord.charAt(i)) {
                occurs++;
                revealedChar.put(i, letter);
            }
        }
        // update pattern
        if(!revealedChar.isEmpty()) {
            for(Map.Entry<Integer, Character> entry : revealedChar.entrySet()) {
                pattern = replaceChar(pattern, entry.getKey(), entry.getValue());
            }
        }
        return occurs;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }

    private String replaceChar(String str, int index, char targetChar) {
        StringBuilder strB = new StringBuilder(str);
        strB.setCharAt(index, targetChar);
        return strB.toString();
    }

}
