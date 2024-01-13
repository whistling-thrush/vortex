package main.java.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import java.util.ArrayList;
import java.util.Map;

public class Admin extends JPanel {

	/*
	 * TODO:
	 * 1. Add ability to delete any booking √
	 * 2. Add ability to change any booking
	 * 3. Add ability to delete any account
	 * 4. Add ability to view most booked desk
	 * 5. Add ability to view booking frequency on a weekly basis
	 */

	public Dimension dimension = new Dimension(800, 600);
	
	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private JPopupMenu contextMenu;
	private Vortex vortex;
	private JLabel lblWelcome;
	private JLayeredPane layeredPane;
	private JSeparator separator;
	private JScrollPane scrllPaneUpcomingBookings;
	private JXTable bookingStack;
	private DefaultTableModel model;
	private JButton btnHamburgerPanel;
	private JButton btnLogOut;
	
	//Variable declarations
	private Map<String, Object> objects;
	private ArrayList<Booking> bookings;
	
	public Admin(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setSize(new Dimension(800, 600));
		setupPanel();
	}

	private void setupPanel() {
		
				
		lblWelcome = new JLabel("Welcome, Admin!");
		lblWelcome.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(188, 6, 424, 63);
		add(lblWelcome);
		
		separator = new JSeparator();
		separator.setBounds(99, 70, 602, 12);
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
        
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(30, 124, 740, 420);
        add(layeredPane);
        
        scrllPaneUpcomingBookings = new JScrollPane();
        scrllPaneUpcomingBookings.setBounds(0, 0, 740, 420);
        scrllPaneUpcomingBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        layeredPane.add(scrllPaneUpcomingBookings);
        
        bookingStack = new JXTable();
        scrllPaneUpcomingBookings.setViewportView(bookingStack);
        
        contextMenu = new JPopupMenu();
        JMenuItem changeMenuItem = new JMenuItem("Change Booking");
        JMenuItem deleteMenuItem = new JMenuItem("Delete Booking");
        contextMenu.add(changeMenuItem);
        contextMenu.add(deleteMenuItem);
        
        // Add mouse listener to deselect selected rows when clicked outside
        addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		int rows = bookingStack.getRowCount();
        		if (!bookingStack.contains(getMousePosition())) {
        			bookingStack.removeRowSelectionInterval(0, rows - 1);
        		}
        	}
        });
        
        // Add mouse listener for right-click events
        bookingStack.addMouseListener(new BookingMouseListener());
        deleteMenuItem.addActionListener(ActionEvent -> deleteSelectedRows());
        changeMenuItem.addActionListener(ActionEvent -> changeBooking());
		
	}
	
	// Deletes selected rows from panel and database
	private void deleteSelectedRows() {
		int[] selectedRows = bookingStack.getSelectedRows();
		
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			model.removeRow(selectedRows[i]);
			DatabaseManager.sql_deleteBooking(bookings.get(selectedRows[i]).getBookID());
		}
	}
	
	// Opens the ChangeBooking panel for the selected row
	private void changeBooking() {
		int index = bookingStack.getSelectedRows().length;
		if (index == 1) {
			vortex.showChangeBooking(bookings.get(bookingStack.getSelectedRow()).getBookID(), true);
		}
	}
	
	private class BookingMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
        	
            if (SwingUtilities.isRightMouseButton(e)) {
            	Point point = e.getComponent().getLocationOnScreen();
            	int row = bookingStack.rowAtPoint(e.getPoint());
            	
            	if (row != -1 && bookingStack.isRowSelected(row)) {
            		showContextMenu(e, point);
        		}
            }
        }
    }

    private void showContextMenu(MouseEvent e, Point point) {
    	contextMenu.show(this, e.getX(), e.getY() + point.y - vortex.getY());
    }
	
	private void showSidePanel(JFrame parentFrame, Component parentComponent) {
        // Create a JDialog for the side panel
        JDialog sidePanelDialog = new JDialog(parentFrame, "Options", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel sidePanelContent = new JPanel();
        sidePanelDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        sidePanelDialog.getContentPane().setLayout(new BorderLayout());
        
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

	@SuppressWarnings("unchecked")
	public void getBookings() {
		
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
		objects = DatabaseManager.sql_getAllBookings();
		
		String[] colNames = (String[]) objects.get("colNames");
		bookings = (ArrayList<Booking>) objects.get("bookings");
		
		model.setColumnIdentifiers(colNames);
		
		if (bookings != null) {
			for (Booking booking : bookings) {
				String deskID = String.valueOf(booking.getDesk());
				String empID = String.valueOf(booking.getEmpID());
				String date = booking.getDate();
				String timeStart = booking.getTimeStart();
				String timeEnd = booking.getTimeEnd();
				String duration = String.valueOf(booking.getDuration());
				
				String[] row = {deskID, empID, date, timeStart, timeEnd, duration};
				
				model.addRow(row);
			}
		}
		
		bookingStack.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
	}

	
	public void clearBookings() {
		bookingStack.removeAll();
	}
}
