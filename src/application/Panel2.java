package application;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel2 extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JButton button;

	/**
	 * Create the panel.
	 */
	public Panel2() {
		setBackground(Color.red);
		button = new JButton();
		this.add(button);
	}

}
