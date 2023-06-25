package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JScrollBar;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblWelcome;
	private JScrollBar scrollBar;

	/**
	 * Create the panel.
	 */
	public Dashboard(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		
		lblWelcome = new JLabel("Welcome, ...!");
		lblWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(38, 6, 424, 63);
		add(lblWelcome);
		
		scrollBar = new JScrollBar();
		scrollBar.setBounds(430, 99, 64, 445);
		add(scrollBar);
		setupPanel();
	}
	
	private void setupPanel() {
		
		setBounds(0, 0, 500, 600);
		
	}
}
