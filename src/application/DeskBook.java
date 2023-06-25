package application;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.sound.midi.Soundbank;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DeskBook extends JFrame {
	
	private static final long serialVersionUID = -5620007751101260104L;
	
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private LoginScreen loginScreen;
	private Dashboard dashboard;
	private CreateBooking createBooking;
	private BookingHistory bookingHistory;
	

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
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
		initComponents();
		setupFrame();
	}
	
	
	private void initComponents() {
		
		//Initialise the objects
		cardLayout = new CardLayout();
		cardPanel = new JPanel();
		loginScreen = new LoginScreen(this);
		dashboard = new Dashboard(this);
		
		//Setup the objects
		cardPanel.setLayout(cardLayout);
		cardPanel.add(loginScreen, "login");
		cardPanel.add(dashboard, "dash");
		
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
		setBounds(200, 200, dashboard.dimension.width, dashboard.dimension.height);
	}
	
}
