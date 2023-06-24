package application;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JPanel {
	
	private static final long serialVersionUID = -7247538218506533985L;
	
	private DeskBook deskBook;
	
	

	/**
	 * Create the panel.
	 */
	public LoginScreen(DeskBook deskBook) {
		this.deskBook = deskBook;
		this.setBounds(0, 0, 500, 600);
		setupPanel();
	}
	
	private void setupPanel() {
		SpringLayout sl_loginPanelLeft = new SpringLayout();
		this.setLayout(sl_loginPanelLeft);
		
		JLabel lblLogin = new JLabel("Log in.");
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, lblLogin, 105, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, lblLogin, 189, SpringLayout.WEST, this);
		lblLogin.setLocation(185, 35);
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, lblLogin, 35, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, lblLogin, 60, SpringLayout.WEST, this);
		lblLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblLogin.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblLogin);
		
		JLabel lblEmail = new JLabel("Email");
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, lblEmail, 60, SpringLayout.WEST, this);
		lblEmail.setLocation(233, 115);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblEmail);
		
		JFormattedTextField frmtdFieldEmail = new JFormattedTextField();
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, frmtdFieldEmail, 141, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, frmtdFieldEmail, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, frmtdFieldEmail, -53, SpringLayout.EAST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, lblEmail, -6, SpringLayout.NORTH, frmtdFieldEmail);
		frmtdFieldEmail.setLocation(56, 145);
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
		this.add(frmtdFieldEmail);
		
		JLabel lblValidEmail = new JLabel("Enter valid email address!");
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, lblValidEmail, 0, SpringLayout.NORTH, lblEmail);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, lblValidEmail, 153, SpringLayout.EAST, lblEmail);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, lblValidEmail, 0, SpringLayout.EAST, frmtdFieldEmail);
		lblValidEmail.setLocation(150, 119);
		lblValidEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValidEmail.setForeground(Color.RED);
		this.add(lblValidEmail);
		lblValidEmail.setVisible(false);
		
		JLabel lblPass = new JLabel("Password");
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, lblPass, 217, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, lblPass, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, frmtdFieldEmail, -34, SpringLayout.NORTH, lblPass);
		lblPass.setLocation(220, 197);
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblPass);
		
		JPasswordField fieldPass = new JPasswordField();
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, fieldPass, 239, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, fieldPass, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, fieldPass, -53, SpringLayout.EAST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, lblPass, -6, SpringLayout.NORTH, fieldPass);
		fieldPass.setLocation(56, 227);
		fieldPass.setToolTipText("Enter your password");
		this.add(fieldPass);
		
		JLabel lblValidPass = new JLabel("Enter valid password!");
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, lblValidPass, 0, SpringLayout.NORTH, lblPass);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, lblValidPass, 247, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, lblValidPass, 0, SpringLayout.EAST, frmtdFieldEmail);
		lblValidPass.setLocation(150, 201);
		lblValidPass.setForeground(Color.RED);
		lblValidPass.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(lblValidPass);
		lblValidPass.setVisible(false);
		
		JCheckBox chkBxShowPass = new JCheckBox("Show password");
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, chkBxShowPass, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, fieldPass, -24, SpringLayout.NORTH, chkBxShowPass);
		chkBxShowPass.setLocation(185, 287);
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
		chkBxShowPass.setHorizontalAlignment(SwingConstants.CENTER);
		chkBxShowPass.setToolTipText("Click to toggle show password");
		this.add(chkBxShowPass);
		
		JSeparator separator = new JSeparator();
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, separator, 355, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, separator, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, separator, -53, SpringLayout.EAST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, chkBxShowPass, -27, SpringLayout.NORTH, separator);
		separator.setLocation(56, 331);
		this.add(separator);
		
		JButton btnForgotPass = new JButton("Forgot password?");
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, btnForgotPass, 474, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, btnForgotPass, 44, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, btnForgotPass, 497, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, btnForgotPass, 198, SpringLayout.WEST, this);
		btnForgotPass.setLocation(173, 447);
		btnForgotPass.setHorizontalAlignment(SwingConstants.LEADING);
		btnForgotPass.setBorderPainted(false);
		btnForgotPass.setBackground(SystemColor.window);
		this.add(btnForgotPass);
		
		JButton btnSignUp = new JButton("Sign up");
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, btnSignUp, 471, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, btnSignUp, -39, SpringLayout.EAST, this);
		btnSignUp.setHorizontalAlignment(SwingConstants.TRAILING);
		btnSignUp.setLocation(204, 446);
		btnSignUp.setBorderPainted(false);
		btnSignUp.setBackground(SystemColor.window);
		this.add(btnSignUp);
		
		JButton btnLogin = new JButton("Log in");
		sl_loginPanelLeft.putConstraint(SpringLayout.WEST, btnLogin, 60, SpringLayout.WEST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.EAST, btnLogin, -53, SpringLayout.EAST, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, separator, -26, SpringLayout.NORTH, btnLogin);
		sl_loginPanelLeft.putConstraint(SpringLayout.NORTH, btnLogin, 396, SpringLayout.NORTH, this);
		sl_loginPanelLeft.putConstraint(SpringLayout.SOUTH, btnLogin, -33, SpringLayout.NORTH, btnSignUp);
		btnLogin.setLocation(56, 358);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				

				if (new String(fieldPass.getPassword()).equals("pass") && frmtdFieldEmail.getText().equals("email")) {
					deskBook.showDash();
				}
				
				if (frmtdFieldEmail.getText().isBlank() || frmtdFieldEmail.getText().isEmpty() || frmtdFieldEmail.getText().equals("Enter email address")) {
					lblValidEmail.setVisible(true);
				} else {
					lblValidEmail.setVisible(false);
				}
				
				if (fieldPass.getPassword().length == 0) {
					lblValidPass.setVisible(true);
				} else {
					lblValidPass.setVisible(false);
				}
			}
		});
		btnLogin.setToolTipText("Click to log in to your account");
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		this.add(btnLogin);
	}
	
}
