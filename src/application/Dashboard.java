package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1936925262291800888L;
	
	private DeskBook deskBook;

	/**
	 * Create the panel.
	 */
	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome, ...!");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(198, 22, 424, 63);
		add(lblNewLabel);
		setupPanel();
	}
	
	private void setupPanel() {
		
		setBounds(0, 0, 820, 446);
		
	}
}
