package main.java.application;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;

import java.util.ArrayList;
import java.util.Map;

public class Dashboard extends JPanel {
	
	public Dimension dimension = new Dimension(800, 650);

	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private Vortex vortex;
	private JLabel lblWelcome;
	private JSeparator separator;
	private JButton btnCreateBooking;
	private JButton btnSeeHistory;
	private JButton btnHamburgerPanel;
	private JScrollPane scrllPaneUpcomingBookings;
	private JLabel lblUpcomingBookings;
	private JXTable bookingStack;
	private JButton btnLogOut;
	
	//Variable declarations
	private DefaultTableModel model;
	private Map<String, Object> objects;

	public Dashboard(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setupPanel();
	}
	
	private void setupPanel() {
		
		lblWelcome = new JLabel("Welcome, ...!");
		lblWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(188, 6, 424, 63);
		add(lblWelcome);
		
		separator = new JSeparator();
		separator.setBounds(99, 83, 602, 12);
		add(separator);
		
		btnHamburgerPanel = new JButton();
		btnHamburgerPanel.setBorderPainted(false);
		btnHamburgerPanel.setBackground(SystemColor.window);
		btnHamburgerPanel.setBounds(30, 18, 40, 40);
		add(btnHamburgerPanel);
		
        // Path to the PNG file
        String pngFilePath = "src/main/resources/assets/bars-solid.png";
        
		btnHamburgerPanel.setIcon(new ImageIcon(pngFilePath));
        btnHamburgerPanel.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Show the side panel when the button is clicked
        		showSidePanel(vortex, btnHamburgerPanel);
        	}
        });
		
		scrllPaneUpcomingBookings = new JScrollPane();
		scrllPaneUpcomingBookings.setBounds(30, 144, 740, 467);
		scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrllPaneUpcomingBookings);
		
		bookingStack = new JXTable();
        scrllPaneUpcomingBookings.setViewportView(bookingStack);
		
        lblUpcomingBookings = new JLabel();
        lblUpcomingBookings.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUpcomingBookings.setText("Upcoming Bookings");
        lblUpcomingBookings.setBounds(317, 109, 166, 21);
        lblUpcomingBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        lblUpcomingBookings.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblUpcomingBookings);
        
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!bookingStack.contains(e.getPoint())) {
					bookingStack.clearSelection();
				}
			}
		});
		
	}

    private void showSidePanel(JFrame parentFrame, Component parentComponent) {
        // Create a JDialog for the side panel
        JDialog sidePanelDialog = new JDialog(parentFrame, "Options", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel sidePanelContent = new JPanel();
        sidePanelDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        sidePanelDialog.getContentPane().setLayout(new BorderLayout());
        
        btnCreateBooking = new JButton("New booking");
		btnCreateBooking.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showCreate();
				sidePanelDialog.dispose();
			}
		});
		btnCreateBooking.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnCreateBooking.setBounds(170, 94, 145, 29);
		sidePanelContent.add(btnCreateBooking);
		
		btnSeeHistory = new JButton("Booking history");
		btnSeeHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showHistory();
				sidePanelDialog.dispose();
			}
		});
		btnSeeHistory.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnSeeHistory.setBounds(485, 94, 145, 29);
		sidePanelContent.add(btnSeeHistory);
        
        btnLogOut = new JButton("Log out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				logout();
				sidePanelDialog.dispose();
			}
		});
		btnLogOut.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnLogOut.setBounds(30, 29, 83, 29);
		sidePanelContent.add(btnLogOut);

        sidePanelDialog.getContentPane().add(sidePanelContent, BorderLayout.CENTER);

        // Set the side panel size and location
        sidePanelDialog.setSize(200, 300);
        sidePanelDialog.setLocationRelativeTo(parentComponent);

        // Show the side panel
        sidePanelDialog.setVisible(true);
    }
	
	private void logout() {
		vortex.showLogin();
	}
	
	public void changeWelcomeText (String name) {
		lblWelcome.setText("Welcome, " + name + "!");
	}

	public void getUpcomingBookings() {

		bookingStack.setModel(new DefaultTableModel() {
			
			private static final long serialVersionUID = 6471753810214881709L;

			@Override
			// Makes all cells un-changeable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		});
		bookingStack.setColumnControlVisible(true);
        bookingStack.setColumnModel(new DefaultTableColumnModelExt());

		model = (DefaultTableModel) bookingStack.getModel();
		objects = DatabaseManager.sql_upcomingBookings();
		
		String[] colNames = (String[]) objects.get("colNames");
		@SuppressWarnings("unchecked")
		ArrayList<Booking> bookings = (ArrayList<Booking>) objects.get("bookings");
		
		model.setColumnIdentifiers(colNames);
		
		for (Booking booking : bookings) {
			String deskID = String.valueOf(booking.getDesk());
			String date = booking.getDate();
			String timeStart = booking.getTimeStart();
			String timeEnd = booking.getTimeEnd();
			String duration = String.valueOf(booking.getDuration());
			
			String[] row = {deskID, date, timeStart, timeEnd, duration};
			
			model.addRow(row);
		}
		
		bookingStack.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

	}
}