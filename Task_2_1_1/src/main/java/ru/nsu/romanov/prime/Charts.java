package ru.nsu.romanov.prime;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * class for making charts.
 */
public class Charts extends JFrame {

    /**
     * Constructor for charts.
     */
    public Charts() {
        initUi();
    }

    /**
     * initializes UI.
     */
    private void initUi() {

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory
                .createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * makes list of count time prime number.
     *
     * @param count count of prime number.
     * @return list of prime numbers.
     */
    private List<Integer> dataSet(int count) {
        final int prime = 20319251;
        return new ArrayList<>(Collections.nCopies(count, prime));
    }

    /**
     * creates data set for chart.
     *
     * @return filled datta set.
     */
    private XYDataset createDataset() {

        final List<Integer> countPrimeNum =
                Arrays.asList(1000, 10000, 100000, 1000000, 3000000);
        CompositeChecker compositeChecker = new CompositeChecker();

        var seriesSeq = new XYSeries("SEQ");
        var seriesParStream = new XYSeries("STREAM");
        var series1Thread = new XYSeries("1 THREAD");
        var series3Threads = new XYSeries("3 THREADS");
        var series5Threads = new XYSeries("5 THREADS");
        var series8Threads = new XYSeries("8 THREADS");
        var series10Threads = new XYSeries("10 THREADS");
        var series20Threads = new XYSeries("20 THREAD");
        var series100Threads = new XYSeries("100 THREADS");

        countPrimeNum.forEach(count -> {
            List<Integer> dataSet = dataSet(count);
            long res = 0;
            int countRepeats = 3;
            long startTime;
            long endTime;

            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeSeq(dataSet);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            seriesSeq.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeStream(dataSet);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            seriesParStream.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 1);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series1Thread.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 3);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series3Threads.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 5);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series5Threads.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 8);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series8Threads.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 10);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series10Threads.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 20);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series20Threads.add(count.doubleValue(), (double) res / countRepeats);

            res = 0;
            for (int i = 0; i < countRepeats; i++) {
                startTime = System.nanoTime();
                compositeChecker.hasCompositeThread(dataSet, 100);
                endTime = System.nanoTime();
                res += endTime - startTime;
            }
            series100Threads.add(count.doubleValue(), (double) res / countRepeats);
        });


        var dataset = new XYSeriesCollection();
        dataset.addSeries(series1Thread);
        dataset.addSeries(series3Threads);
        dataset.addSeries(series5Threads);
        dataset.addSeries(series8Threads);
        dataset.addSeries(series10Threads);
        dataset.addSeries(series20Threads);
        dataset.addSeries(series100Threads);
        dataset.addSeries(seriesSeq);
        dataset.addSeries(seriesParStream);

        return dataset;
    }

    /**
     * makes chart.
     *
     * @param dataset dataset to draw.
     * @return filled chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average performance",
                "Count elements",
                "performance",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        XYPlot plot = chart.getXYPlot();

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Performance",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }

    /**
     * main method for chart.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            var ex = new Charts();
            ex.setVisible(true);
        });
    }
}