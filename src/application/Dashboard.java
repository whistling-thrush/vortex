package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1936925262291800888L;
	
	private DeskBook deskBook;

	/**
	 * Create the panel.
	 */
	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setupPanel();
	}
	
	private void setupPanel() {
		JButton btnNewButton = new JButton("Show Login Screen");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showLogin();
			}
		});
		add(btnNewButton);
	}

}
