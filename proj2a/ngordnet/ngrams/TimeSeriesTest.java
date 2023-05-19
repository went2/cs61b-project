package ngordnet.ngrams;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testFromSpec2() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 100.0);
        catPopulation.put(1992, 200.0);
        catPopulation.put(1993, 300.0);
        catPopulation.put(1994, 400.0);
        catPopulation.put(1995, 500.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 50.0);
        dogPopulation.put(1993, 150.0);
        dogPopulation.put(1995, 250.0);

        TimeSeries newTs = dogPopulation.dividedBy(catPopulation);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1993, 1995));
        assertThat(newTs.years()).isEqualTo(expectedYears);

        List<Double> expectedNumbers = new ArrayList<>
                (Arrays.asList(0.5, 0.5, 0.5));
        assertThat(newTs.data()).isEqualTo(expectedNumbers);

    }
} 