package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	

	/**
	 * Create the panel.
	 */
	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		
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
		btnCreateBooking.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnCreateBooking.setBounds(188, 94, 145, 29);
		add(btnCreateBooking);
		
		btnSeeHistory = new JButton("Booking history");
		btnSeeHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showHistory();
			}
		});
		btnSeeHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnSeeHistory.setBounds(467, 94, 145, 29);
		add(btnSeeHistory);
		
		scrllPaneUpcomingBookings = new JScrollPane();
		scrllPaneUpcomingBookings.setBounds(30, 169, 740, 387);
		add(scrllPaneUpcomingBookings);
		setupPanel();
	}
	
	private void setupPanel() {
		setSize(dimension);
	}
}
