package main.java.application;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Map;

public class Dashboard extends JPanel {
	
	public Dimension dimension = new Dimension(800, 600);

	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private Vortex vortex;
	private JLabel lblWelcome;
	private JSeparator separator;
	private JButton btnCreateBooking;
	private JButton btnSeeHistory;
	private JScrollPane scrllPaneUpcomingBookings;
	private JLabel lblUpcomingBookings;
	private JTable bookingStack;
	private DefaultTableModel model;
	private JPanel bookingDetails;
	private JButton btnLogOut;
	
	//Variable declarations
	private Map<String, Object> objects;

	public Dashboard(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setSize(new Dimension(800, 600));
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
			public void mousePressed(MouseEvent e) {
				vortex.showCreate();
			}
		});
		btnCreateBooking.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnCreateBooking.setBounds(170, 94, 145, 29);
		add(btnCreateBooking);
		
		btnSeeHistory = new JButton("Booking history");
		btnSeeHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showHistory();
			}
		});
		btnSeeHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSeeHistory.setBounds(485, 94, 145, 29);
		add(btnSeeHistory);
		
		scrllPaneUpcomingBookings = new JScrollPane();
		scrllPaneUpcomingBookings.setBounds(30, 179, 740, 404);
		scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneUpcomingBookings);
		
		bookingStack = new JTable();
		// Create a ListSelectionListener to clear selection when clicking outside a row
        bookingStack.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//				if (bookingStack.getSelectedRow() == -1) {
//					bookingStack.clearSelection();
//                }
////				// !e.getValueIsAdjusting() && 
            }
        });
//        bookingStack.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent e) {
//        		if (e.)
//        	}
//		});
        scrllPaneUpcomingBookings.setViewportView(bookingStack);
		
        lblUpcomingBookings = new JLabel();
        lblUpcomingBookings.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUpcomingBookings.setText("Upcoming Bookings");
        lblUpcomingBookings.setBounds(308, 146, 166, 21);
        lblUpcomingBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblUpcomingBookings.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblUpcomingBookings);
        
		btnLogOut = new JButton("Log out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				logout();
			}
		});
		btnLogOut.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnLogOut.setBounds(30, 29, 83, 29);
		add(btnLogOut);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!bookingStack.contains(e.getPoint())) {
					bookingStack.clearSelection();
				}
			}
		});
	}
	
	
	private void logout() {
		vortex.showLogin();
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
				vortex.showDash();
				vortex.showDash();
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
		vortex.showChangeBooking(bookID);
	}
	
	public void changeWelcomeText (String name) {
		lblWelcome.setText("Welcome, " + name + "!");
	}

	public void getUpcomingBookings() {

		model = (DefaultTableModel) bookingStack.getModel();
		objects = DatabaseManager.sql_upcomingBookings();
		
		String[] colNames = (String[]) objects.get("colNames");
		@SuppressWarnings("unchecked")
		ArrayList<Booking> bookings = (ArrayList<Booking>) objects.get("bookings");
		
		model.setColumnIdentifiers(colNames);
		
		for (Booking booking : bookings) {
			String deskID = String.valueOf(booking.getDesk());
			String date = booking.getDate();
			String timeStart = booking.getTimeStart();
			String timeEnd = booking.getTimeEnd();
			String duration = String.valueOf(booking.getDuration());
			
			String[] row = {deskID, date, timeStart, timeEnd, duration};
			
			model.addRow(row);
		}
	}


	public void clearBookings() {
		bookingStack.setModel(new DefaultTableModel() {
			private static final long serialVersionUID = -4794154724274374730L;

			@Override
			// Makes all cells un-changeable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
	}
}