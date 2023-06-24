package application;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JPanel {
	
	public DeskBook frame;
	
	
	private static final long serialVersionUID = 1L;
	JButton button;

	/**
	 * Create the panel.
	 */
	public LoginScreen() {
		frame = (DeskBook) SwingUtilities.getAncestorOfClass(DeskBook.class, this);
		setupPanel();
	}
	
	private void setupPanel() {
		setBackground(new Color(202, 202, 202));
		setLayout(null);
		button = new JButton();
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.changePanel();
				
			}
		});
		button.setBounds(238, 165, 75, 29);
		this.add(button);
	}

}
