package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1936925262291800888L;
	
	private DeskBook deskBook;

	/**
	 * Create the panel.
	 */
	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(116, 110, 218, 79);
		add(btnNewButton);
		setupPanel();
	}
	
	private void setupPanel() {
		
	}

}
