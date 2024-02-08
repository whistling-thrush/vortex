package main.java.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import org.jdesktop.swingx.JXPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsPage extends JXPanel {

	private static final long serialVersionUID = 17812638762893712L;
	
	private Dimension dimension = new Dimension(740, 420);
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;

	public StatisticsPage() {
		this.setSize(dimension);
		initializeComponents();
		setupLayout();
	}

    private void initializeComponents() {
        // Create a dataset
        dataset = new DefaultCategoryDataset();
        dataset.addValue(10, "Monday", "Desk 1");
        dataset.addValue(20, "Tuesday", "Desk 1");
        dataset.addValue(15, "Wednesday", "Desk 1");
        dataset.addValue(25, "Thursday", "Desk 1");
        dataset.addValue(30, "Friday", "Desk 1");

        // Create the chart
        chart = ChartFactory.createBarChart(
                "Desk Booking Frequency",  // Chart title
                "Day",                     // X-axis label
                "Bookings",                // Y-axis label
                dataset                    // Dataset
        );

        // Customize the chart (if needed)
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);

        // Create a chart panel to display the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 400));
    }

    private void setupLayout() {
        // Add the chart panel to this JPanel
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }

}