package aoa.choosers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;

import static com.google.common.truth.Truth.assertThat;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // 处理边界
        if(wordLength < 0) {
            throw new IllegalArgumentException("wordLength should be bigger than 0");
        }

        // initiate wordPool and pattern
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if(wordPool.size() == 0) {
            throw new IllegalStateException("No word found, please try again.");
        }
        pattern = "-".repeat(wordLength);
    }

    // generate all patterns with specified char from current wordPool;
    // and update wordPool
    private Map<String, List<String>> generatePatternWordListMap(char cha) {
        Map<String, List<String>> newMap = new TreeMap<>();

        // 将当前 pattern 相关的 word 添加到 map 中
        for(String word : wordPool) {
            String currentPattern = generateCurrentPattern(cha, word);
            newMap.computeIfAbsent(currentPattern, k -> new ArrayList<>());
            newMap.get(currentPattern).add(word);
        }
//        System.out.println("char " + cha + " wordPool " + wordPool + " newMap " + newMap);

        return newMap;
    }

    // 从当前的 wordPool 中生成一个字母的所有 pattern
    private String generateCurrentPattern(char cha, String word) {

        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<word.length(); i++) {
            if(cha == word.charAt(i)) {
                strBuilder.append(cha);
            } else if(pattern.charAt(i) != '-') {
                strBuilder.append(pattern.charAt(i)); // 将当前 pattern 加入
            } else {
                strBuilder.append('-');
            }
        }
        return strBuilder.toString();
    }


    // return the number of the occurrences of the guessed letter
    @Override
    public int makeGuess(char letter) {
        Map<String, List<String>> allPatternMap = generatePatternWordListMap(letter);

        Map<String, List<String>> currentPattern = getCurrentPatternMap(allPatternMap);

        Map.Entry<String, List<String>> firstEntry = currentPattern.entrySet().iterator().next();
        pattern = firstEntry.getKey();
        wordPool = firstEntry.getValue();

        return numOfCharInStr(pattern, letter);
    }

    // 获取最大 size 的 wordPool
    private Map<String, List<String>> getCurrentPatternMap(Map<String, List<String>> allPatterns) {
        int max = 0;
        Map<String, List<String>> newMap = new TreeMap<>();
        for(Map.Entry<String, List<String>> entry : allPatterns.entrySet()) {
            int len = entry.getValue().size();
            if(len > max) {
                max = len;
                newMap.clear();
                newMap.put(entry.getKey(), entry.getValue());
            } else if(len == max) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }

        return newMap;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    // return any word from current wordPool
    // if there is only one word exists, return that word.
    @Override
    public String getWord() {
        int length = wordPool.size();
        if(length == 1) {
            return wordPool.get(0);
        }
        int randomIndex = StdRandom.uniform(length);
        return wordPool.get(randomIndex);
    }


    // 返回一个字母在一个字符串中出现的次数
    private int numOfCharInStr(String str, char cha) {
        // 返回字母在 pattern 中出现的次数
        int occurs = 0;
        for(int i=0; i<str.length(); i++) {
            if(cha == str.charAt(i)) {
                occurs++;
            }
        }
        return occurs;
    }

}
