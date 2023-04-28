package timing;

import edu.princeton.cs.algs4.Stopwatch;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Experiments {

    private static void printTimingTable(TimingData data) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < data.getNs().size(); i += 1) {
            int N = data.getNs().get(i);
            double time = data.getTimes().get(i);
            int opCount = data.getOpCounts().get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    /** Computes the nth Fibonacci number using a slow naive recursive strategy.*/
    private static int fib(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static TimingData exampleFibonacciExperiment() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        // We're computing each fibonacci number 100 times to get a more stable number
        int ops = 100;

        for (int N = 10; N < 31; N++) {
            Ns.add(N);
            opCounts.add(ops);
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < ops; j++) {
                int fib = fib(N);
            }
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }

    public static TimingData timeAListConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        for(int N = 1000; N <= 128000; N *= 2) {
            Ns.add(N);
            opCounts.add(N);
            AList<Integer> list = new AList<>();

            Stopwatch sw = new Stopwatch();
            for(int i=0; i<N; i++) {
                list.addLast(i);
            }
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }


    public static TimingData timeSLListGetLast() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();


        int ops = 10000;
        for(int N=1000; N<=128000; N*=2) {
            // construct the SLList of size N
//            SLList<Integer> list = new SLList<>();
            List<Integer> list = new LinkedList<>();
            for(int i=0; i<N; i+=1) {
                list.add(i);
//                list.addLast(i);
            }
            // start test
            Ns.add(N);
            opCounts.add(ops);
            Stopwatch sw = new Stopwatch();
            for(int i=0; i<ops; i++) {
                list.get(list.size() - 1);
            }
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }

    public static void main(String[] args) {
        // TimingData td = exampleFibonacciExperiment();
//        TimingData td = timeAListConstruction();
        TimingData td = timeSLListGetLast();
        // Modify this line to make the chart title make sense
        String title = "SLList GetLast Test";

        // Convert "times" (in seconds) and "opCounts" to nanoseconds / op
        List<Double> timesUsPerOp = new ArrayList<>();
        for (int i = 0; i < td.getTimes().size(); i++) {
            timesUsPerOp.add(td.getTimes().get(i) / td.getOpCounts().get(i) * 1e6);
        }

        printTimingTable(td);

        XYChart chart = QuickChart.getChart(title, "N", "time (us per op)", "Time", td.getNs(), timesUsPerOp);
        new SwingWrapper<>(chart).displayChart();
    }
}
