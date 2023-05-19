package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static int MIN_YEAR = 1400;
    private static int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        this.putAll(ts);
        MAX_YEAR = endYear;
        MIN_YEAR = startYear;
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        Set<Integer> keys = this.keySet();
        return new ArrayList<>(keys);
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        Collection<Double> values = this.values();
        return new ArrayList<>(values);
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTs = new TimeSeries();
        newTs.putAll(this);

        Set<Map.Entry<Integer, Double>> entries = ts.entrySet();
        for(Map.Entry<Integer, Double> entry : entries) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            if(newTs.get(key) != null) {
                Double newValue = value + newTs.get(key);
                newTs.put(key, newValue);
            } else {
                newTs.put(key, value);
            }
        }

        return newTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTs = new TimeSeries();
        newTs.putAll(this);

        Set<Map.Entry<Integer, Double>> entries = newTs.entrySet();
        for(Map.Entry<Integer, Double> entry : entries) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            if(ts.get(key) == null) {
                throw new IllegalArgumentException("Missing year " + key + " in target TimeSeries");
            } else {
                double quotient = value / ts.get(key);
                newTs.put(key, quotient);
            }
        }

        return newTs;
    }
}
