package main.java.application;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.Map;

public class Admin extends JPanel {

	/*
	 * TODO:
	 * 1. Add ability to delete any booking
	 * 2. Add ability to delete any account
	 * 3. Add ability to view most booked desk
	 * 4. Add ability to view booking frequency on a weekly basis
	 */

	public Dimension dimension = new Dimension(800, 600);
	
	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private Vortex vortex;
	private JLabel lblWelcome;
	private JSeparator separator;
	private JScrollPane scrllPaneUpcomingBookings;
	private JXTable bookingStack;
	private DefaultTableModel model;
	
	//Variable declarations
	private Map<String, Object> objects;
	private ArrayList<Booking> bookings;
	
	public Admin(Vortex vortex) {
		this.vortex = vortex;
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
		scrllPaneUpcomingBookings.setBounds(30, 116, 740, 440);
		scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneUpcomingBookings);
		
		bookingStack = new JXTable();
        scrllPaneUpcomingBookings.setViewportView(bookingStack);
		
	}

	public void getBookings() {
		
		
		bookingStack.setModel(new DefaultTableModel() {
			
			private static final long serialVersionUID = 6471753810214881709L;

			@Override
			// Makes all cells un-changeable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		});
		bookingStack.setColumnControlVisible(true);
        bookingStack.setColumnModel(new DefaultTableColumnModelExt());

		model = (DefaultTableModel) bookingStack.getModel();
		objects = DatabaseManager.sql_getAllBookings();
		
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
		
		bookingStack.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
	}

	
	public void clearBookings() {
		bookingStack.removeAll();
	}

}
