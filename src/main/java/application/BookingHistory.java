package main.java.application;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

public class BookingHistory extends JPanel {

	private static final long serialVersionUID = -5347571249754630798L;
	
	public Dimension dimension = new Dimension(800, 600);
	
	//Component declarations
	private Vortex vortex;
	private JLabel lblBookingHistory;
	private JButton btnGoBack;
	private JScrollPane scrllPaneBookingHistory;
	private JXTable bookingStack;
	
	//Variable declarations
	private Map<String, Object> objects;
	private DefaultTableModel model;
	private ArrayList<Booking> bookings;

	//Constructor method
	public BookingHistory(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setupPanel();
	}
	
	private void setupPanel() {
		
		//Panel title
		lblBookingHistory = new JLabel("Booking history");
		lblBookingHistory.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookingHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblBookingHistory.setBounds(274, 65, 264, 29);
		add(lblBookingHistory);
		
		//Scroll Pane for viewing bookings
		scrllPaneBookingHistory = new JScrollPane();
		scrllPaneBookingHistory.setBounds(36, 144, 740, 360);
		scrllPaneBookingHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneBookingHistory);

		bookingStack = new JXTable();
        scrllPaneBookingHistory.setViewportView(bookingStack);
		
		//Button to go back to the dash
		btnGoBack = new JButton();
		btnGoBack.setBorderPainted(false);
		btnGoBack.setBackground(SystemColor.window);
		btnGoBack.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnGoBack.setText("<");
		btnGoBack.setBounds(29, 65, 134, 29);
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showDash();
			}
		});
		add(btnGoBack);
	}
	
	@SuppressWarnings("unchecked")
	public void getBookingHistory() {
		
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
		objects = DatabaseManager.sql_bookingHistory();
		
		String[] colNames = (String[]) objects.get("colNames");
		bookings = (ArrayList<Booking>) objects.get("bookings");
		
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
	
	//Called from Vortex - removes all booking cards
	public void clearBookings() {
		bookingStack.removeAll();
	}
}
