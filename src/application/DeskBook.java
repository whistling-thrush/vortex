package application;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DeskBook extends JFrame {
	
	private static final long serialVersionUID = -5620007751101260104L;
	
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private LoginScreen loginScreen;
	private Dashboard dashboard;
	private CreateBooking createBooking;
	private BookingHistory bookingHistory;
	private SignUp signUp;
	

	/**
	 * Launch the application.
	 */
	
	public static void initialiseFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeskBook frame = new DeskBook();
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
	public DeskBook() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DatabaseManager.sql_closeConnection();
			}
		});
		initComponents();
		setupFrame();
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
		
		
		//Setup the objects
		cardPanel.setLayout(cardLayout);
		cardPanel.add(loginScreen, "login");
		cardPanel.add(dashboard, "dash");
		cardPanel.add(createBooking, "create");
		cardPanel.add(bookingHistory, "history");
		cardPanel.add(signUp, "signup");
		
	}


	private void setupFrame() {
		setMinimumSize(loginScreen.dimension);
		setContentPane(cardPanel);
		cardLayout.show(cardPanel, "login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, loginScreen.dimension.width, loginScreen.dimension.height);
	}
	
	public void showDash() {
		cardLayout.show(cardPanel, "dash");
		setMinimumSize(dashboard.dimension);
		setSize(dashboard.dimension);
	}
	
	public void showSignup() {
		cardLayout.show(cardPanel, "signup");
		setMinimumSize(signUp.dimension);
		setSize(signUp.dimension);
	}
	
	public void showCreate() {
		cardLayout.show(cardPanel, "create");
	}
	
	public void showHistory() {
		cardLayout.show(cardPanel, "history");
	}
	
	public void showLogin() {
		setMinimumSize(loginScreen.dimension);
		setSize(loginScreen.dimension);
		cardLayout.show(cardPanel, "login");
	}
	
}
