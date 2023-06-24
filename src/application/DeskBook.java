package application;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DeskBook extends JFrame {
	
	private static final long serialVersionUID = -5620007751101260104L;
	
	private CardLayout cardLayout;
	private JPanel cardPanel;
	private LoginScreen loginScreen;
	private Dashboard dashboard;
	

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
		initComponents();
		setupFrame();
	}
	
	
	private void initComponents() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		loginScreen = new LoginScreen(this);
		dashboard = new Dashboard(this);
		
		cardPanel.add(loginScreen, "login");
		cardPanel.add(dashboard, "dash");
		
	}


	private void setupFrame() {
		setTitle("Desk Book");
		setResizable(false);
		setContentPane(cardPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 1000, 600);
	}
	
	public void showLogin() {
		cardLayout.show(cardPanel, "login");
	}
	
	public void showDash() {
		cardLayout.show(cardPanel, "dash");
	}
	

}
