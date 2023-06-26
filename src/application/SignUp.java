package application;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignUp extends JPanel {
	
	public Dimension dimension = new Dimension(500, 600);
	
	private static final long serialVersionUID = 3743443270482748913L;

	//Component declarations
	private DeskBook deskBook;
	private JLabel lblSignup;
	private JLabel lblName;
	private JFormattedTextField frmtdFieldName;
	private JLabel lblEmail;
	private JFormattedTextField frmtdFieldEmail;
	private JLabel lblPass;
	private JPasswordField fieldPass;
	private JCheckBox chkBxShowPass;
	private JButton btnSignup;
	
	/**
	 * Create the panel.
	 */
	public SignUp(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		setupPanel();
	}
	
	private void setupPanel() {
		setSize(dimension);
		
		lblSignup = new JLabel("Sign up.");
		lblSignup.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblSignup.setSize(129, 70);
		lblSignup.setLocation(60, 35);
		add(lblSignup);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblName.setBounds(60, 141, 63, 24);
		add(lblName);
		
		frmtdFieldName = new JFormattedTextField();
		frmtdFieldName.setToolTipText("Enter your name");
		frmtdFieldName.setText("Enter name");
		frmtdFieldName.setSelectionColor(new Color(165, 205, 225));
		frmtdFieldName.setForeground(Color.LIGHT_GRAY);
		frmtdFieldName.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		frmtdFieldName.setBounds(60, 166, 387, 42);
		frmtdFieldName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (frmtdFieldName.getText().equals("Enter name")) {
					frmtdFieldName.setText("");
					frmtdFieldName.setForeground(new Color(0,0,0));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (frmtdFieldName.getText().equals("")) {
					frmtdFieldName.setText("Enter name");
					frmtdFieldName.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		frmtdFieldName.setForeground(Color.LIGHT_GRAY);
		frmtdFieldName.setSelectionColor(new Color(165, 205, 225));
		add(frmtdFieldName);
		
		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEmail.setSize(63, 24);
		lblEmail.setLocation(60, 231);
		this.add(lblEmail);
		
		frmtdFieldEmail = new JFormattedTextField();
		frmtdFieldEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		frmtdFieldEmail.setSize(387, 42);
		frmtdFieldEmail.setLocation(60, 253);
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
		
		lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPass.setSize(74, 16);
		lblPass.setLocation(60, 329);
		this.add(lblPass);
		
		fieldPass = new JPasswordField();
		fieldPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		fieldPass.setSize(387, 42);
		fieldPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signupRequested();
				}
			}
		});
		fieldPass.setLocation(60, 351);
		fieldPass.setToolTipText("Enter your password");
		this.add(fieldPass);
		
		chkBxShowPass = new JCheckBox("Show password");
		chkBxShowPass.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		chkBxShowPass.setSize(148, 23);
		chkBxShowPass.setLocation(60, 420);
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
		this.add(chkBxShowPass);
		
		btnSignup = new JButton("Sign up");
		btnSignup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				signupRequested();
			}
		});
		btnSignup.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnSignup.setBounds(159, 488, 181, 45);
		this.add(btnSignup);
		
	}
	
	private void signupRequested() {
		DatabaseManager.requestSignup(frmtdFieldName.getText(), frmtdFieldEmail.getText(), new String(fieldPass.getPassword()));
		deskBook.showLogin();
	}
}