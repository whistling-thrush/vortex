package application;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreen extends JPanel {
	
	private static final long serialVersionUID = -7247538218506533985L;
	
	//Variable declarations
	public Dimension dimension = new Dimension(500, 600);
	public static int currentEmployeeID;
	public static String currentEmployeeName;
	
	//Component declarations
	private DeskBook deskBook;
	private Dashboard dashboard;
	private JLabel lblValidEmailPass;
	private JLabel lblLogin;
	private JLabel lblEmail;
	private JFormattedTextField frmtdFieldEmail;
	private JLabel lblValidEmail;
	private JLabel lblPass;
	private JPasswordField fieldPass;
	private JLabel lblValidPass;
	private JCheckBox chkBxShowPass;
	private JSeparator separator;
	private JButton btnForgotPass;
	private JButton btnSignUp;
	private JButton btnLogin;

	/**
	 * Create the panel.
	 */
	public LoginScreen(DeskBook deskBook, Dashboard dashboard) {
		this.deskBook = deskBook;
		this.dashboard = dashboard;
		setSize(dimension);
		setupPanel();
	}
	
	private void setupPanel() {
		setLayout(null);
		
		lblValidEmailPass = new JLabel("Incorrect email and/or password ");
		lblValidEmailPass.setForeground(new Color(255, 0, 0));
		lblValidEmailPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblValidEmailPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblValidEmailPass.setBounds(132, 41, 236, 23);
		add(lblValidEmailPass);
		lblValidEmailPass.setVisible(false);
		
		lblLogin = new JLabel("Log in.");
		lblLogin.setSize(129, 70);
		lblLogin.setLocation(60, 59);
		lblLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblLogin.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblLogin);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEmail.setSize(68, 16);
		lblEmail.setLocation(60, 141);
		add(lblEmail);
		
		frmtdFieldEmail = new JFormattedTextField();
		frmtdFieldEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		frmtdFieldEmail.setSize(387, 42);
		frmtdFieldEmail.setLocation(60, 165);
		frmtdFieldEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (frmtdFieldEmail.getText().equals("Enter email address")) {
					frmtdFieldEmail.setText("");
					frmtdFieldEmail.setForeground(new Color(0,0,0));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (frmtdFieldEmail.getText().equals("")) {
					frmtdFieldEmail.setText("Enter email address");
					frmtdFieldEmail.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		frmtdFieldEmail.setForeground(Color.LIGHT_GRAY);
		frmtdFieldEmail.setSelectionColor(new Color(165, 205, 225));
		frmtdFieldEmail.setText("Enter email address");
		frmtdFieldEmail.setToolTipText("Enter your email");
		add(frmtdFieldEmail);
		
		lblValidEmail = new JLabel("Enter valid email address!");
		lblValidEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblValidEmail.setSize(200, 16);
		lblValidEmail.setLocation(247, 143);
		lblValidEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValidEmail.setForeground(Color.RED);
		add(lblValidEmail);
		lblValidEmail.setVisible(false);
		
		lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPass.setSize(74, 16);
		lblPass.setLocation(60, 241);
		add(lblPass);
		
		fieldPass = new JPasswordField();
		fieldPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		fieldPass.setSize(387, 42);
		fieldPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginRequested();
				}
			}
		});
		fieldPass.setLocation(60, 263);
		fieldPass.setToolTipText("Enter your password");
		add(fieldPass);
		
		lblValidPass = new JLabel("Enter valid password!");
		lblValidPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblValidPass.setSize(200, 16);
		lblValidPass.setLocation(247, 241);
		lblValidPass.setForeground(Color.RED);
		lblValidPass.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblValidPass);
		lblValidPass.setVisible(false);
		
		chkBxShowPass = new JCheckBox("Show password");
		chkBxShowPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		chkBxShowPass.setSize(148, 23);
		chkBxShowPass.setLocation(60, 329);
		chkBxShowPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkBxShowPass.isSelected()) {
					fieldPass.setEchoChar((char) 0);
				} else {
					fieldPass.setEchoChar((char) '●');
				}
			}
		});
		chkBxShowPass.setToolTipText("Click to toggle show password");
		add(chkBxShowPass);
		
		separator = new JSeparator();
		separator.setSize(387, 15);
		separator.setLocation(60, 379);
		add(separator);
		
		btnForgotPass = new JButton("Forgot password?");
		btnForgotPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnForgotPass.setSize(174, 23);
		btnForgotPass.setLocation(44, 498);
		btnForgotPass.setHorizontalAlignment(SwingConstants.LEADING);
		btnForgotPass.setBorderPainted(false);
		btnForgotPass.setBackground(SystemColor.window);
		add(btnForgotPass);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSignUp.setSize(104, 29);
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showSignup();
			}
		});
		btnSignUp.setHorizontalAlignment(SwingConstants.TRAILING);
		btnSignUp.setLocation(357, 495);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setBackground(SystemColor.window);
		add(btnSignUp);
		
		btnLogin = new JButton("Log in");
		btnLogin.setSize(387, 42);
		btnLogin.setLocation(60, 420);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginRequested();
			}
		});
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginRequested();
				}
			}
		});
		btnLogin.setToolTipText("Click to log in to your account");
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		add(btnLogin);
	}
	
	private void loginRequested() {
		
		lblValidEmail.setVisible(false);
		lblValidPass.setVisible(false);
		lblValidEmailPass.setVisible(false);
		
		String pass = new String(fieldPass.getPassword());
		String email = new String(frmtdFieldEmail.getText());
		
		if (email.isBlank() || email.isEmpty() || email.equals("Enter email address")) {
			lblValidEmail.setVisible(true);
		}
		
		if (pass.isBlank() || pass.isEmpty()) {
			lblValidPass.setVisible(true);
		}
		
		if (DatabaseManager.sql_loginSearch(email, pass)) {
			lblValidEmail.setVisible(false);
			lblValidPass.setVisible(false);
			lblValidEmailPass.setVisible(false);
			deskBook.showDash();
			dashboard.changeWelcomeText(currentEmployeeName);
		} else {
			lblValidEmailPass.setVisible(true);
		}
	}

	public void reset() {
		fieldPass.setText("");
		frmtdFieldEmail.setText("Enter email address");
		frmtdFieldEmail.setForeground(Color.LIGHT_GRAY);
	}
	
	public void invalidEmail() {
		
	}
	
	public void invalidPass() {
		
	}
}
