package main.java.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsPage extends JXPanel {

	private static final long serialVersionUID = 17812638762893712L;
	
	// Variable declarations
	private Dimension dimension = new Dimension(740, 420);
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private ArrayList<String> deskList;
	private JSpinner spnnrDesk;
	private JLabel lblDeskNumber;
	private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	public StatisticsPage() {
		this.setSize(dimension);
		initializeComponents();
		setupLayout();
	}

    private void initializeComponents() {
    	
    	// Create the label
    	lblDeskNumber = new JLabel("Desk number: ");
    	lblDeskNumber.setPreferredSize(new Dimension(150, 30));
    	
        // Create a list of desks
        deskList = new ArrayList<>();
        for (int i = 1; i <= 192; i++) {
            deskList.add("Desk " + i);
        }

        // Create a spinner for desk selection
        SpinnerListModel spinnerModel = new SpinnerListModel(deskList);
        spnnrDesk = new JSpinner(spinnerModel);
        spnnrDesk.setPreferredSize(new Dimension(150, 30));
        spnnrDesk.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateChart((String) spnnrDesk.getValue());
            }
        });
    	
        // Create a dataset
        dataset = new DefaultCategoryDataset();
        updateChart("Desk 1");

        // Create the chart
        chart = ChartFactory.createBarChart(
                "Booking Frequency",  // Chart title
                "Day",                // X-axis label
                "Bookings",           // Y-axis label
                dataset               // Dataset
        );

        // Customize the chart
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);

        // Create a chart panel to display the chart
        chartPanel = new ChartPanel(chart);
        chartPanel.setSize(new Dimension(400, 400));
    }
    
    private void updateChart (String deskValue) {
    	
    	int mon = 0, tue = 0, wed = 0, thu = 0, fri = 0, sat = 0, sun = 0;
    	
    	@SuppressWarnings("unchecked")
		ArrayList<Booking> bookings = (ArrayList<Booking>) DatabaseManager.sql_getAllBookings().get("bookings");
    	
    	for (Booking booking : bookings) {
    		try {
    			if (new String("Desk " + booking.getDesk()).equals(deskValue)) {
	    			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getDate());
	    			Calendar calendar = Calendar.getInstance();
	    	        calendar.setTime(date);
	    	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	    	        
	    	        String day = daysOfWeek[dayOfWeek - 1];
	    	        
	    	        switch (day) {
					case "Monday":
						mon++;
						break;
						
					case "Tuesday":
						tue++;
						break;
						
					case "Wednesday":
						wed++;
						break;
						
					case "Thursday":
						thu++;
						break;
						
					case "Friday":
						fri++;
						break;
						
					case "Saturday":
						sat++;
						break;
						
					case "Sunday":
						sun++;
						break;
	
					default:
						break;
					}
    			}
    			
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
    	
    	dataset.clear();
        dataset.addValue(mon, "Monday", deskValue);
        dataset.addValue(tue, "Tuesday", deskValue);
        dataset.addValue(wed, "Wednesday", deskValue);
        dataset.addValue(thu, "Thursday", deskValue);
        dataset.addValue(fri, "Friday", deskValue);
        dataset.addValue(sat, "Saturday", deskValue);
        dataset.addValue(sun, "Sunday", deskValue);
    	
    }

    private void setupLayout() {
        // Add the chart panel to this JPanel
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spinnerPanel.add(lblDeskNumber);
        spinnerPanel.add(spnnrDesk);
        add(spinnerPanel, BorderLayout.NORTH);
    }

}