package application;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class BookingHistory extends JPanel {

	private static final long serialVersionUID = -5347571249754630798L;
	
	public Dimension dimension = new Dimension(800, 600);
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblBookingHistory;
	private JButton btnGoBack;
	private JScrollPane scrllPaneBookingHistory;
	private JPanel bookingStack;
	private JPanel bookingDetails;
	
	//Variable declarations
	private ArrayList<Booking> bookings;

	//Constructor method
	public BookingHistory(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		setupPanel();
	}
	
	private void setupPanel() {
		
		//Panel title
		lblBookingHistory = new JLabel("Booking history");
		lblBookingHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblBookingHistory.setBounds(58, 72, 264, 29);
		add(lblBookingHistory);
		
		//Scroll Pane for viewing bookings
		scrllPaneBookingHistory = new JScrollPane();
		scrllPaneBookingHistory.setBounds(30, 144, 740, 360);
		scrllPaneBookingHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneBookingHistory);
		bookingStack = new JPanel(); //Panel on the scroll pane
        bookingStack.setLayout(new BoxLayout(bookingStack, BoxLayout.Y_AXIS));
        scrllPaneBookingHistory.setViewportView(bookingStack);
		
		//Button to go back to the dash
		btnGoBack = new JButton();
		btnGoBack.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnGoBack.setText("Go back");
		btnGoBack.setBounds(30, 535, 134, 29);
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskBook.showDash();
			}
		});
		add(btnGoBack);
	}
	
	//Creates a new panel for every past booking
	private JPanel createBookingPanel(Booking booking) {
		
		bookingDetails = new JPanel();
		bookingDetails.setPreferredSize(new Dimension(720, 50));
		bookingDetails.setMaximumSize(new Dimension(720, 50));
		bookingDetails.setBackground(new Color(230, 230, 230));
		bookingDetails.setLayout(new BorderLayout());
		
		//Text of booking card
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
		
		return bookingDetails;
		
	}

	//Called from DeskBook - gets booking history
	public void getBookingHistory() {
		
		bookings = DatabaseManager.sql_bookingHistory();
		
		for (int i = 0; i < bookings.size(); i++) {
			
			bookingDetails = createBookingPanel(bookings.get(i));
			bookingDetails.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
			bookingStack.add(bookingDetails); //Adds the booking card to bookingStack
            
			//Creates vertical space for all cards except the last one
			if (i < bookings.size() - 1) {
				bookingStack.add(Box.createRigidArea(new Dimension(0, 20)));
			}
		}
	}
	
	//Called from DeskBook - removes all booking cards
	public void clearBookings() {
		bookingStack.removeAll();
	}
}
