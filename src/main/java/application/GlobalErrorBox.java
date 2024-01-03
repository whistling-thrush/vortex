package main.java.application;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GlobalErrorBox extends JDialog {
	
    private static final long serialVersionUID = -8654124532333345806L;

	public GlobalErrorBox(DeskBook deskBook, String message) {
        super(deskBook, "Error", true);
        initComponents(message);
    }

    private void initComponents(String message) {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnOk);
        
        contentPane.add(messageLabel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    public static void showError(DeskBook deskBook, String message) {
        GlobalErrorBox errorBox = new GlobalErrorBox(deskBook, message);
        errorBox.setVisible(true);
    }
}