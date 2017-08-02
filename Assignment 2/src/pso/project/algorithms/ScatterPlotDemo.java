package pso.project.algorithms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.util.ShapeUtilities;

public class ScatterPlotDemo extends ApplicationFrame {

	public ScatterPlotDemo(String s, HashMap<Integer, ArrayList<Integer>> particlePbest) {
		super(s);
		JPanel jpanel = createDemoPanel(particlePbest);
		jpanel.setPreferredSize(new Dimension(570, 360));
		add(jpanel);
	}

	public static JPanel createDemoPanel(HashMap<Integer, ArrayList<Integer>> particlePbest) {
		JFreeChart jfreechart = ChartFactory.createScatterPlot("PBest Value Plot","Number Of EPochs","Number of Changes",
				samplexydataset2(particlePbest), PlotOrientation.HORIZONTAL, true, true, false);

		Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
		XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
		xyPlot.setDomainCrosshairVisible(true);
		xyPlot.setRangeCrosshairVisible(true);
		XYItemRenderer renderer = xyPlot.getRenderer();
		renderer.setSeriesShape(0, cross);
		renderer.setSeriesPaint(0, Color.blue);
		return new ChartPanel(jfreechart);
	}

	private static XYDataset samplexydataset2(HashMap<Integer, ArrayList<Integer>> particlePbest) {
		int cols = 20;
		int rows = 20;
		double[][] values = new double[cols][rows];
		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		XYSeries series = new XYSeries("pBest Values");

		for (Map.Entry<Integer, ArrayList<Integer>> i : particlePbest.entrySet()) {
			for (Integer p : i.getValue()) {
				series.add(i.getKey(), p);
			} // (x,y)
		}
		xySeriesCollection.addSeries(series);
		return xySeriesCollection;
	}
}