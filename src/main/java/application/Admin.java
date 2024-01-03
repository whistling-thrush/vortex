package main.java.application;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class Admin extends JPanel {

	public Dimension dimension = new Dimension(800, 600);
	
	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblWelcome;
	private JSeparator separator;
	private JButton btnCreateBooking;
	private JButton btnSeeHistory;
	private JScrollPane scrllPaneUpcomingBookings;
	private JLabel lblUpcomingBookings;
	private JPanel bookingStack;
	private JPanel bookingDetails;
	private JButton btnLogOut;
	
	//Variable declarations
	private Map<String, Object> bookings;
	
	public Admin() {
		setLayout(null);
		setSize(new Dimension(800, 600));
		setupPanel();
	}

	private void setupPanel() {
		
		lblWelcome = new JLabel("Welcome, Admin!");
		lblWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(188, 6, 424, 63);
		add(lblWelcome);
		
		separator = new JSeparator();
		separator.setBounds(99, 70, 602, 12);
		add(separator);
		
		scrllPaneUpcomingBookings = new JScrollPane();
		scrllPaneUpcomingBookings.setBounds(30, 144, 740, 360);
		scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		bookingStack = new JPanel();
		bookingStack.setLayout(new BoxLayout(bookingStack, BoxLayout.Y_AXIS));
		scrllPaneUpcomingBookings.setViewportView(bookingStack);
		add(scrllPaneUpcomingBookings);
		
		lblUpcomingBookings = new JLabel("Upcoming bookings");
		lblUpcomingBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblUpcomingBookings.setHorizontalAlignment(SwingConstants.CENTER);
		scrllPaneUpcomingBookings.setColumnHeaderView(lblUpcomingBookings);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				logout();
			}
		});
		btnLogOut.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnLogOut.setBounds(635, 532, 135, 29);
		add(btnLogOut);
	}
	
	
	private void logout() {
		deskBook.showLogin();
	}
	
	
	private JPanel createBookingPanel(Booking booking) {
		bookingDetails = new JPanel();
		bookingDetails.setPreferredSize(new Dimension(720, 50));
		bookingDetails.setMaximumSize(new Dimension(720, 50));
		bookingDetails.setBackground(new Color(230, 230, 230));
		bookingDetails.setLayout(new BorderLayout());
		
		JLabel lblDetails = new JLabel();
		lblDetails.setBounds(50, 20, 0, 0);
		lblDetails.setText("Desk: " 
				+ booking.getDesk() 
				+ "     From: " 
				+ booking.getTimeStart() 
				+ "     To: " 
				+ booking.getTimeEnd()
				+ "     On: "
				+ booking.getDate());
		bookingDetails.add(lblDetails);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		
		JButton btnCancelBooking = new JButton();
		btnCancelBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				cancelBooking(booking.getBookID());
				//Very jugadu fix for now :)
				deskBook.showDash();
				deskBook.showDash();
			}
		});
		btnCancelBooking.setText("Cancel booking");
		btnCancelBooking.setSize(new Dimension(30, 5));
		buttonPanel.add(btnCancelBooking, BorderLayout.EAST);
		
		JButton btnChangeBooking = new JButton();
		btnChangeBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				changeBooking(booking.getBookID());
			}
		});
		btnChangeBooking.setText("Change booking");
		btnChangeBooking.setSize(new Dimension(30, 5));
		buttonPanel.add(btnChangeBooking, BorderLayout.WEST);
		
		bookingDetails.add(buttonPanel, BorderLayout.EAST);
		
		return bookingDetails;
	}
	
	private void cancelBooking(int bookID) {
		DatabaseManager.sql_deleteBooking(bookID);
	}
	
	private void changeBooking(int bookID) {
		deskBook.showChangeBooking(bookID);
	}
	
	public void changeWelcomeText (String name) {
		lblWelcome.setText("Welcome, " + name + "!");
	}

	public void getUpcomingBookings() {
		
		bookings = DatabaseManager.sql_upcomingBookings();
		
	}

	
	public void clearBookings() {
		bookingStack.removeAll();
	}

}
