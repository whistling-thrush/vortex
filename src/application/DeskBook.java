package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class DeskBook extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static LoginScreen loginScreen;
	private static Panel2 panel2;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public DeskBook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginScreen = new LoginScreen();
		panel2 = new Panel2();
		setupFrame();
		loginScreen.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	private void setupFrame() {
		setContentPane(loginScreen);
		setBounds(300, 200, 1000, 600);
	}
	
	public void changePanel() {
		remove(loginScreen);
		setContentPane(panel2);
	}

}
