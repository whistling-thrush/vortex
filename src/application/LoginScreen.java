package application;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JPanel {
	
	private static final long serialVersionUID = -7247538218506533985L;
	
	private DeskBook deskBook;
	JButton btnShowDash;

	/**
	 * Create the panel.
	 */
	public LoginScreen(DeskBook deskBook) {
		this.deskBook = deskBook;
		setupPanel();
	}
	
	private void setupPanel() {
		setBackground(new Color(202, 202, 202));
		btnShowDash = new JButton();
		btnShowDash.setText("Show Dash");
		btnShowDash.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showDash();
			}
		});
		btnShowDash.setBounds(462, 285, 75, 29);
		this.add(btnShowDash);
	}
	
	

}
