package main.java.application;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Vortex extends JFrame {
	
	private static final long serialVersionUID = -5620007751101260104L;
	public static ArrayList<Booking> bookings;
	
	
	//Component declarations
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private LoginScreen loginScreen;
	private Dashboard dashboard;
	private CreateBooking createBooking;
	private BookingHistory bookingHistory;
	private SignUp signUp;
	private Floorplan floorplan;
	private ChangeBooking changeBooking;
	
	//Variable declarations
	private int bookID;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		//Initialise database manager
		DatabaseManager.initialiseDBMS();
		
		//Initialise the frame
		Vortex.initialiseFrame();
		
	}
	
	public static void initialiseFrame() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Vortex frame = new Vortex();
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public Vortex() {
		try {
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					DatabaseManager.sql_closeConnection();
				}
			});
			initComponents();
			setupFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void initComponents() {

		//Initialise the objects
		cardLayout = new CardLayout();
		cardPanel = new JPanel();
		dashboard = new Dashboard(this);
		loginScreen = new LoginScreen(this, dashboard);
		createBooking = new CreateBooking(this);
		bookingHistory = new BookingHistory(this);
		signUp = new SignUp(this);
		changeBooking = new ChangeBooking(this);
		floorplan = new Floorplan(this, createBooking, changeBooking);
		
		
		//Setup the objects
		cardPanel.setLayout(cardLayout);
		cardPanel.add(loginScreen, "login");
		cardPanel.add(dashboard, "dash");
		cardPanel.add(createBooking, "create");
		cardPanel.add(bookingHistory, "history");
		cardPanel.add(signUp, "signup");
		cardPanel.add(floorplan, "floorplan");
		cardPanel.add(changeBooking, "changeBooking");
		
	}


	private void setupFrame() {
		setPreferredSize(loginScreen.dimension);
		setContentPane(cardPanel);
		cardLayout.show(cardPanel, "login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, loginScreen.dimension.width, loginScreen.dimension.height);
	}
	
	public void showDash() {
		cardLayout.show(cardPanel, "dash");
		setMinimumSize(dashboard.dimension);
		setSize(dashboard.dimension);
		dashboard.clearBookings();
		dashboard.getUpcomingBookings();
	}
	
	public void showSignup() {
		cardLayout.show(cardPanel, "signup");
		setMinimumSize(signUp.dimension);
		setSize(signUp.dimension);
	}
	
	public void showCreate() {
		cardLayout.show(cardPanel, "create");
		setMinimumSize(createBooking.dimension);
		setSize(createBooking.dimension);
	}
	
	public void showHistory() {
		cardLayout.show(cardPanel, "history");
		setMinimumSize(bookingHistory.dimension);
		setSize(bookingHistory.dimension);
		bookingHistory.clearBookings();
		bookingHistory.getBookingHistory();
	}
	
	public void showLogin() {
		setMinimumSize(loginScreen.dimension);
		setSize(loginScreen.dimension);
		loginScreen.reset();
		cardLayout.show(cardPanel, "login");
	}
	
	public void showFloorplan(boolean showCreateBooking) {
		setMinimumSize(floorplan.dimension);
		setSize(floorplan.dimension);
		floorplan.resetFloorplan();
		floorplan.addFloorplan(showCreateBooking, bookID);
		cardLayout.show(cardPanel, "floorplan");
	}
	
	public Floorplan getFloorplan() {
		return floorplan;
	}
	
	public void showChangeBooking(int bookID) {
		this.bookID = bookID;
		setMinimumSize(changeBooking.dimension);
		setSize(changeBooking.dimension);
		changeBooking.setBookID(bookID);
		cardLayout.show(cardPanel, "changeBooking");
	}
	
}
