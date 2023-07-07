package application;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class BookingHistory extends JPanel {

	private static final long serialVersionUID = -5347571249754630798L;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblBookingHistory;
	private JButton btnGoBack;

	/**
	 * Create the panel.
	 */
	public BookingHistory(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		setupPanel();
	}
	
	private void setupPanel() {
		
		lblBookingHistory = new JLabel("Booking history");
		lblBookingHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblBookingHistory.setBounds(58, 72, 264, 29);
		add(lblBookingHistory);
		
		btnGoBack = new JButton();
		btnGoBack.setText("Go back");
		btnGoBack.setBounds(58, 519, 75, 29);
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showDash();
			}
		});
		add(btnGoBack);
	}
}
