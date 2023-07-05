package application;

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

public class Dashboard extends JPanel {
	
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
	private ArrayList<Booking> bookings;
	private JButton btnLogOut;
	

	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		setSize(dimension);
		setupPanel();
	}
	
	private void setupPanel() {
		
		lblWelcome = new JLabel("Welcome, ...!");
		lblWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(188, 6, 424, 63);
		add(lblWelcome);
		
		separator = new JSeparator();
		separator.setBounds(99, 70, 602, 12);
		add(separator);
		
		btnCreateBooking = new JButton("New booking");
		btnCreateBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showCreate();
			}
		});
		btnCreateBooking.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnCreateBooking.setBounds(170, 94, 145, 29);
		add(btnCreateBooking);
		
		btnSeeHistory = new JButton("Booking history");
		btnSeeHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showHistory();
			}
		});
		btnSeeHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSeeHistory.setBounds(485, 94, 145, 29);
		add(btnSeeHistory);
		
		scrllPaneUpcomingBookings = new JScrollPane();
		scrllPaneUpcomingBookings.setBounds(30, 144, 740, 360);
		scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneUpcomingBookings);
		
		lblUpcomingBookings = new JLabel("Upcoming bookings");
		lblUpcomingBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblUpcomingBookings.setHorizontalAlignment(SwingConstants.CENTER);
		scrllPaneUpcomingBookings.setColumnHeaderView(lblUpcomingBookings);
		bookingStack = new JPanel();
        bookingStack.setLayout(new BoxLayout(bookingStack, BoxLayout.Y_AXIS));
        scrllPaneUpcomingBookings.setViewportView(bookingStack);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		bookingDetails.setBackground(new Color(230, 230, 230));
		bookingDetails.setLayout(new BorderLayout());
		
		JLabel lblDetails = new JLabel();
		lblDetails.setBounds(50, 20, 0, 0);
		lblDetails.setText("Desk: " + booking.getDesk() + "     From: " + booking.getTimeStart() + "   To: " + booking.getTimeEnd());
		bookingDetails.add(lblDetails);
		
		return bookingDetails;
	}
	
	public void changeWelcomeText (String name) {
		lblWelcome.setText("Welcome, " + name + "!");
	}

	public void getUpcomingBookings() {
		
		bookings = DatabaseManager.sql_upcomingBookings();
		
		for (int i = 0; i < bookings.size(); i++) {
			
			bookingDetails = createBookingPanel(bookings.get(i));
			bookingDetails.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
			bookingStack.add(bookingDetails);
            
			if (i < bookings.size() - 1) {
				bookingStack.add(Box.createRigidArea(new Dimension(0, 20)));
			}
		}
	}
	
	public void clearBookings() {
		bookingStack.removeAll();
	}

}