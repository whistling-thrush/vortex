package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DeskBook extends JFrame {
	
	private LoginScreen loginScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeskBook frame = new DeskBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DeskBook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginScreen = new LoginScreen();
		setupFrame();
		loginScreen.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	private void setupFrame() {
		setContentPane(loginScreen);
		setBounds(400, 250, 1000, 600);
	}

}
