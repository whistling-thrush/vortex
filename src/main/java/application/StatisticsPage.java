package main.java.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsPage extends JPanel {

	private static final long serialVersionUID = 17812638762893712L;
	
	// Component declarations
	private Dimension dimension = new Dimension(740, 420);
	private DefaultCategoryDataset datasetFrequency;
	private DefaultCategoryDataset datasetTop;
	private JFreeChart chartFrequency;
	private ChartPanel chartPanelFrequency;
	private JFreeChart chartTop;
	private ChartPanel chartPanelTop;
	private ArrayList<String> deskList;
	private ArrayList<String> parameterList;
	private JSpinner spnnrDeskFrequency;
	private JSpinner spnnrDeskTop;
	private JLabel lblDeskNumber;
	private JLabel lblParameter;
	
	// Variable declarations
	private final String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	public StatisticsPage() {
		setSize(dimension);
		initializeComponents();
		setupLayout();
	}

    private void initializeComponents() {
    	
    	// Create the labels
    	lblDeskNumber = new JLabel("Desk number: ");
    	lblDeskNumber.setPreferredSize(new Dimension(150, 30));
    	
    	lblParameter = new JLabel("Choose parameter: ");
    	lblParameter.setPreferredSize(new Dimension(150, 30));
    	
        // Create the lists
        deskList = new ArrayList<>();
        for (int i = 1; i <= 192; i++) {
            deskList.add("Desk " + i);
        }
        
        parameterList = new ArrayList<String>(Arrays.asList("Desk", "Employee", "Day"));

        // Create the spinners
        SpinnerListModel spinnerModelFrequency = new SpinnerListModel(deskList);
        spnnrDeskFrequency = new JSpinner(spinnerModelFrequency);
        spnnrDeskFrequency.setPreferredSize(new Dimension(150, 30));
        spnnrDeskFrequency.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateFrequencyChart((String) spnnrDeskFrequency.getValue());
            }
        });
        
        SpinnerListModel spinnerModelTop = new SpinnerListModel(parameterList);
        spnnrDeskTop = new JSpinner(spinnerModelTop);
        spnnrDeskTop.setPreferredSize(new Dimension(150, 30));
        spnnrDeskTop.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateTopChart((String) spnnrDeskTop.getValue());
            }
        });
    	
        // Create the datasets
        datasetFrequency = new DefaultCategoryDataset();
        datasetTop = new DefaultCategoryDataset();

        // Create the charts
        chartFrequency = ChartFactory.createBarChart(
                "Weekly Booking Frequency", // Chart title
                "Day",                		// X-axis label
                "Bookings",           		// Y-axis label
                datasetFrequency            // Dataset
        );
        
        chartTop = ChartFactory.createBarChart(
        		"Title",			// Chart title
        		"X-axis",			// X-axis label
        		"Y-axis",			// Y-axis label
        		datasetTop			// Dataset
		);

        // Customize the charts
        chartFrequency.setBackgroundPaint(Color.WHITE);
        chartFrequency.getTitle().setPaint(Color.BLACK);
        
        chartTop.setBackgroundPaint(Color.WHITE);
        chartTop.getTitle().setPaint(Color.BLACK);

        // Create chart panels to display the charts
        chartPanelFrequency = new ChartPanel(chartFrequency);
        chartPanelFrequency.setSize(new Dimension(400, 400));
        
        chartPanelTop = new ChartPanel(chartTop);
        chartPanelTop.setSize(new Dimension(400, 400));
        
        // Initialise the charts
        updateTopChart("Desk");
        updateFrequencyChart("Desk 1");
    }
    
    @SuppressWarnings("unchecked")
	private void updateTopChart (String parameterValue) {
    	
    	datasetTop.clear();
    	
		ArrayList<Booking> bookings = (ArrayList<Booking>) DatabaseManager.sql_getAllBookings().get("bookings");
    	ArrayList<String[]> employees = (ArrayList<String[]>) DatabaseManager.sql_getAllEmployees().get("employees");
    	
    	if (parameterValue.equals("Desk")) {
    		
    		// Change chart values
    		CategoryPlot plot = (CategoryPlot) chartTop.getPlot();
	        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
	        
	        xAxis.setLabel("Desk Number");
	        yAxis.setLabel("Frequency");
	        
	        chartTop.setTitle("Top 10 Desks booked");
    		
    		// 0 - desk number; 1 - number of times desk has been booked
    		int[][] desksBooked = new int[192][2];
			
    		for (int i = 0; i < bookings.size(); i++) {
    			desksBooked[bookings.get(i).getDesk() - 1][1]++;
    			desksBooked[bookings.get(i).getDesk() - 1][0] = bookings.get(i).getDesk();
    		}
    		
    		Arrays.sort(desksBooked, Comparator.comparingInt((int[] a) -> a[1]).reversed());
    		
    		for (int i = 0; i < 10; i++) {
    			if (desksBooked[i][1] == 0) {
    				break;
    			} else {
    				datasetTop.addValue(desksBooked[i][1], new String("Desk " + desksBooked[i][0]), parameterValue);
    			}
    		}
    		
		} else if (parameterValue.equals("Employee")) {
			
			// Change chart values
			CategoryPlot plot = chartTop.getCategoryPlot();
	        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
	        
	        xAxis.setLabel("Employee name");
	        yAxis.setLabel("Desks booked");
	        
	        chartTop.setTitle("Top 10 Desk bookers");
			
			// 0 - employee number; 1 - desks booked by employee
			int[][] employeeBooked = new int[employees.size()][2];
			
			for (int i = 0; i < employeeBooked[0].length; i++) {
				employeeBooked[bookings.get(i).getEmpID() - 1][1]++;
				employeeBooked[bookings.get(i).getEmpID() - 1][0] = bookings.get(i).getEmpID();
			}
			
			Arrays.sort(employeeBooked, Comparator.comparingInt((int[] a) -> a[1]).reversed());
			
			for (int i = 0; i < 10; i++) {
    			if (employeeBooked[i][1] == 0) {
    				break;
    			} else {
    				
    				for (String[] employee : employees) {
    					if (employee[0].equals(Integer.toString(employeeBooked[i][0]))) {
    						datasetTop.addValue(employeeBooked[i][1], employee[1], parameterValue);
    					}
    				}
    				
    			}
    		}
			
		} else if (parameterValue.equals("Day")) {
			
			// Change chart values
			CategoryPlot plot = chartTop.getCategoryPlot();
	        CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
	        
	        xAxis.setLabel("Day");
	        yAxis.setLabel("Desks booked");
	        
	        chartTop.setTitle("Most frequent days");
			
			int mon = 0, tue = 0, wed = 0, thu = 0, fri = 0, sat = 0, sun = 0;
			
			
			for (Booking booking : bookings) {
				try {
					
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
	    	        
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
	        datasetTop.addValue(mon, "Monday", parameterValue);
	        datasetTop.addValue(tue, "Tuesday", parameterValue);
	        datasetTop.addValue(wed, "Wednesday", parameterValue);
	        datasetTop.addValue(thu, "Thursday", parameterValue);
	        datasetTop.addValue(fri, "Friday", parameterValue);
	        datasetTop.addValue(sat, "Saturday", parameterValue);
	        datasetTop.addValue(sun, "Sunday", parameterValue);
		}
    }
    
    private void updateFrequencyChart (String deskValue) {
    	
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
    	
    	
    	datasetFrequency.clear();
        datasetFrequency.addValue(mon, "Monday", deskValue);
        datasetFrequency.addValue(tue, "Tuesday", deskValue);
        datasetFrequency.addValue(wed, "Wednesday", deskValue);
        datasetFrequency.addValue(thu, "Thursday", deskValue);
        datasetFrequency.addValue(fri, "Friday", deskValue);
        datasetFrequency.addValue(sat, "Saturday", deskValue);
        datasetFrequency.addValue(sun, "Sunday", deskValue);
    	
    }

    private void setupLayout() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Frequency Chart
        JPanel panelFrequency = new JPanel(new BorderLayout());
        JPanel spinnerPanelFrequency = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spinnerPanelFrequency.add(lblDeskNumber);
        spinnerPanelFrequency.add(spnnrDeskFrequency);
        panelFrequency.add(spinnerPanelFrequency, BorderLayout.NORTH);
        panelFrequency.add(chartPanelFrequency, BorderLayout.CENTER);
        panelFrequency.setPreferredSize(new Dimension(400, 400));
        add(panelFrequency);

        // "Top" Chart
        JPanel panelTop = new JPanel(new BorderLayout());
        JPanel spinnerPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spinnerPanelTop.add(lblParameter);
        spinnerPanelTop.add(spnnrDeskTop);
        panelTop.add(spinnerPanelTop, BorderLayout.NORTH);
        panelTop.add(chartPanelTop, BorderLayout.CENTER);
        panelTop.setPreferredSize(new Dimension(400, 400));
        add(panelTop);
        
    }

}