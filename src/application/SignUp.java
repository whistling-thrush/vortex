package application;

import java.awt.Dimension;

import javax.swing.JPanel;

public class SignUp extends JPanel {
	
	public Dimension dimension = new Dimension(500, 600);
	
	private static final long serialVersionUID = 3743443270482748913L;

	//Component declarations
	private DeskBook deskBook;
	
	/**
	 * Create the panel.
	 */
	public SignUp(DeskBook deskBook) {
		this.deskBook = deskBook;
		setupPanel();
	}
	
	private void setupPanel() {
		setSize(dimension);
	}

}