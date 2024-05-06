import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Rank vs Traversals graph
        try {
            String fileName = "input.txt"; // Default filename
            if (args.length > 0) {
                fileName = args[0]; // If argument provided, use it as filename
            }
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            PrintWriter pw = new PrintWriter(new FileWriter(fileName + "_output.txt"));

            int n = Integer.parseInt(br.readLine());
            UnionFind uf = new UnionFind(n);
            UnionFindWithPathCompression ufpc = new UnionFindWithPathCompression(n);
            int m = Integer.parseInt(br.readLine());
            for (int i = 0; i < m + (n - 1); i++) {
                String lineStr = br.readLine();
                if (lineStr == null) {
                    System.err.println("Error: unexpected end of file, please verify m/n values in input");
                    break;
                }
                String[] line = lineStr.split(" ");
                if (line[0].equals("U")) {
                    uf.union(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    ufpc.union(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                } else if (line[0].equals("F")) {
                    Element temp = uf.findWithMetrics(Integer.parseInt(line[1]));
                    temp = ufpc.findWithMetrics(Integer.parseInt(line[1]));
                    System.out.println(temp.value);
                    pw.println(temp.value);
                }
            }
            br.close();
            pw.close();
            System.out.println("Output file written as: " + fileName + "_output.txt");
            // Metrics for find without path compression
            ArrayList<Metric> normalMetrics = uf.metrics;
            // Metrics for find with path compression
            ArrayList<Metric> pcMetrics = ufpc.metrics;

            // Plot graphs for path compression and normal algorithm
            plotRankTraversalGraph(pcMetrics, "Path Compression");
            plotRankTraversalGraph(normalMetrics, "No Path Compression");
            plotRuntimeGraph(pcMetrics, "Path Compression");
            plotRuntimeGraph(normalMetrics, "No Path Compression");


        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input format: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static void plotRankTraversalGraph(ArrayList<Metric> metrics, String seriesName) {
        // Prepare data for plotting
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();

        for (Metric metric : metrics) {
            yData.add(metric.traversals);
            xData.add(metric.rank);
        }

        // Create Chart
        XYChart chart = new XYChartBuilder().width(600).height(500).title("Traversals vs Rank").xAxisTitle("Rank").yAxisTitle("Traversals").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(16);
        chart.addSeries(seriesName, xData, yData);

        new SwingWrapper(chart).displayChart();

    }

    public static void plotRuntimeGraph(ArrayList<Metric> metrics, String seriesName) {
        // Prepare data for plotting
        List<Integer> xData = new ArrayList<>();
        List<Long> yData = new ArrayList<>();

        for (Metric metric : metrics) {
            yData.add(metric.time);
            xData.add(metric.rank);
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(600).height(500).title("Runtime vs Rank").xAxisTitle("Rank").yAxisTitle("Runtime (in ns)").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(16);
        chart.addSeries(seriesName, xData, yData);

        new SwingWrapper(chart).displayChart();

    }

}