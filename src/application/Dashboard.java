package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JScrollBar;

public class Dashboard extends JPanel {
	
	public Dimension dimension = new Dimension(800, 600);

	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblWelcome;

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
		setupPanel();
	}
	
	private void setupPanel() {
		setSize(dimension);
	}
}
