package application;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookingHistory extends JPanel {

	private static final long serialVersionUID = -5347571249754630798L;
	
	//Component declarations
	private DeskBook deskBook;
	private JButton btnGoBack;

	/**
	 * Create the panel.
	 */
	public BookingHistory(DeskBook deskBook) {
		this.deskBook = deskBook;
		setupPanel();
	}
	
	private void setupPanel() {
		btnGoBack = new JButton();
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showDash();
			}
		});
		add(btnGoBack);
	}

}
