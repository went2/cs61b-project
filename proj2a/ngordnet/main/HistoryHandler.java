package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for(String word : words) {
            TimeSeries ts = map.weightHistory(word, startYear, endYear);
            lts.add(ts);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(words, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
