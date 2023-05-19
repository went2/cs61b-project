package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    public static void main(String[] args) {
        new NGramMap("./data/ngrams/very_short.csv", "");
    }

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;

    private final HashMap<String, TimeSeries> stringTS;
    private final TimeSeries wordCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {

        stringTS = new HashMap<>();
        In wordsIn = new In(wordsFilename);

        String currentWord = null;

        while(wordsIn.hasNextLine()) {
            String name = wordsIn.readString();
            int year = wordsIn.readInt();
            double times = wordsIn.readDouble();
            wordsIn.readInt();

            if (!name.equals(currentWord)) { // next new word
                currentWord = name;
                stringTS.put(currentWord, new TimeSeries());
            }

            stringTS.get(currentWord).put(year, times);
        }

        // store word counts
        wordCounts = new TimeSeries();
        In countsIn = new In(countsFilename);
        while(wordsIn.hasNextLine()) {
            String line = countsIn.readLine();
            String[] arr = line.split(",");
            int year = Integer.parseInt(arr[0]);
            double counts = Double.parseDouble(arr[1]);
            wordCounts.put(year, counts);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries ts = stringTS.get(word);
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        TimeSeries ts = stringTS.get(word);
        return copyTS(ts);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return copyTS(wordCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = stringTS.get(word);
        return new TimeSeries(ts, startYear, endYear).dividedBy(wordCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries ts = stringTS.get(word);
        return ts.dividedBy(wordCounts);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for(String word : words) {
            TimeSeries temp = weightHistory(word, startYear, endYear);
            ts = ts.plus(temp);
        }
        return ts;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for(String word : words) {
            TimeSeries temp = weightHistory(word);
            ts = ts.plus(temp);
        }
        return ts;
    }

    private TimeSeries copyTS(TimeSeries origin) {
        TimeSeries copy = new TimeSeries();
        Set<Map.Entry<Integer, Double>> entries = origin.entrySet();
        for(Map.Entry<Integer, Double> entry : entries) {
            int key = entry.getKey();
            double value = entry.getValue();
            copy.put(key, value);
        }
        return copy;
    }

    private HashMap<Integer, Integer> copyHashMap(HashMap<Integer, Integer> origin) {
        HashMap<Integer, Integer> copy = new HashMap<>();
        Set<Map.Entry<Integer, Integer>> entries = origin.entrySet();
        for(Map.Entry<Integer, Integer> entry : entries) {
            int key = entry.getKey();
            int value = entry.getValue();
            copy.put(key, value);
        }
        return copy;
    }
}
