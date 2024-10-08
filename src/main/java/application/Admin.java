package main.java.application;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import java.util.ArrayList;
import java.util.Map;

public class Admin extends JPanel {

	public Dimension dimension = new Dimension(800, 600);
	
	private static final long serialVersionUID = 1936925262291800888L;
	
	//Component declarations
	private JPopupMenu contextMenu;
	private Vortex vortex;
	private JLabel lblWelcome;
	private CardLayout cardLayout;
	private JXPanel cardPanel;
	private JSeparator separator;
	private JScrollPane scrllPaneBookings;
	private JScrollPane scrllPaneEmployees;
	private JScrollPane scrllPaneStatistics;
	private JXTable tblBooking;
	private JXTable tblEmployee;
	private DefaultTableModel model;
	private StatisticsPage pnlStatistics;
	private JButton btnHamburgerPanel;
	private JButton btnLogOut;
	private JButton btnBookings;
	private JButton btnEmployees;
	private JButton btnStatistics;
	
	//Variable declarations
	private Map<String, Object> objects;
	private ArrayList<Booking> bookings;
	private ArrayList<String[]> employees;
	private enum AdminViews {bookings, employees, statistics};
	private AdminViews currentView;
	
	public Admin(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setSize(new Dimension(800, 600));
		setupPanel();
        setupBookingView();
        setupEmployeeView();
        setupStatisticsView();
	}

	private void setupPanel() {
		
		currentView = AdminViews.bookings;
				
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
		
        // Path to the PNG file for the hamburger menu
        String pngFilePath = "src/main/resources/assets/bars-solid.png";
        
		btnHamburgerPanel.setIcon(new ImageIcon(pngFilePath));
        btnHamburgerPanel.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Show the side panel when the button is clicked
        		showSidePanel(vortex, btnHamburgerPanel);
        	}
        });
        
        cardLayout = new CardLayout();
        cardPanel = new JXPanel();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBounds(30, 124, 740, 420);
        add(cardPanel);
		
	}
	
	private class TableMouseListener extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			if (SwingUtilities.isRightMouseButton(e)) {
				Point point = e.getComponent().getLocationOnScreen();
				
				if (currentView.equals(AdminViews.bookings)) {
					int row = tblBooking.rowAtPoint(e.getPoint());
					
					if (row != -1 && tblBooking.isRowSelected(row)) {
						showContextMenu(e, point);
					}
				} else if(currentView.equals(AdminViews.employees)) {
					int row = tblEmployee.rowAtPoint(e.getPoint());
					
					if (row != -1 && tblEmployee.isRowSelected(row)) {
						showContextMenu(e, point);
					}
				}
			}
		}
	}
	
	private void setupEmployeeView() {
		
		scrllPaneEmployees = new JScrollPane();
		scrllPaneEmployees.setBounds(0, 0, 740, 420);
		scrllPaneEmployees.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		cardPanel.add(scrllPaneEmployees, "employees");
		
		tblEmployee = new JXTable();
		scrllPaneEmployees.setViewportView(tblEmployee);
		
		// Add mouse listener to deselect selected rows when clicked outside
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int rows = tblEmployee.getRowCount();
				if (!tblEmployee.contains(getMousePosition())) {
					tblEmployee.removeRowSelectionInterval(0, rows - 1);
				}
			}
		});
		
		// Add mouse listener for right-click events
		tblEmployee.addMouseListener(new TableMouseListener());
	}
	
	private void setupBookingView() {
		
		scrllPaneBookings = new JScrollPane();
		scrllPaneBookings.setBounds(0, 0, 740, 420);
		scrllPaneBookings.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		cardPanel.add(scrllPaneBookings, "bookings");
		
		tblBooking = new JXTable();
		scrllPaneBookings.setViewportView(tblBooking);
		
		// Add mouse listener to deselect selected rows when clicked outside
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int rows = tblBooking.getRowCount();
				if (!tblBooking.contains(getMousePosition())) {
					tblBooking.removeRowSelectionInterval(0, rows - 1);
				}
			}
		});
		
		// Add mouse listener for right-click events
		tblBooking.addMouseListener(new TableMouseListener());
	}
	
	private void setupStatisticsView() {
		
		pnlStatistics = new StatisticsPage();
		scrllPaneStatistics = new JScrollPane();
		scrllPaneStatistics.setBounds(0, 0, 740, 420);
		scrllPaneStatistics.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrllPaneStatistics.setViewportView(pnlStatistics);
		cardPanel.add(scrllPaneStatistics, "statistics");
		
	}
	
	// Deletes selected rows from panel and database
	private void deleteSelectedRows() {
		
		if (currentView.equals(AdminViews.bookings)) {
			int[] selectedRows = tblBooking.getSelectedRows();
			model = (DefaultTableModel) tblBooking.getModel();
			
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				model.removeRow(selectedRows[i]);
				DatabaseManager.sql_deleteBooking(bookings.get(selectedRows[i]).getBookID());
			}
		} else if (currentView.equals(AdminViews.employees)) {
			int[] selectedRows = tblEmployee.getSelectedRows();
			model = (DefaultTableModel) tblEmployee.getModel();
			
			for (int i = selectedRows.length - 1; i >= 0; i--) {
				model.removeRow(selectedRows[i]);
				DatabaseManager.sql_deleteAccount(Integer.valueOf(employees.get(selectedRows[i])[0]));
			}
		}
	}
	
	// Opens the ChangeBooking panel for the selected row
	private void changeBooking() {
		int index = tblBooking.getSelectedRows().length;
		if (index == 1) {
			vortex.showChangeBooking(bookings.get(tblBooking.getSelectedRow()).getBookID(), true);
		}
	}
	
    private void showContextMenu(MouseEvent e, Point point) {
    	
		contextMenu = new JPopupMenu();
		
		if (currentView.equals(AdminViews.employees)) {
			
			JMenuItem deleteMenuItem = new JMenuItem("Delete Account");
			contextMenu.add(deleteMenuItem);
			
			deleteMenuItem.addActionListener(ActionEvent -> deleteSelectedRows());
			
		} else if (currentView.equals(AdminViews.bookings)) {
			
			JMenuItem changeMenuItem = new JMenuItem("Change Booking");
			JMenuItem deleteMenuItem = new JMenuItem("Delete Booking");
			
			contextMenu.add(changeMenuItem);
			contextMenu.add(deleteMenuItem);
			
			deleteMenuItem.addActionListener(ActionEvent -> deleteSelectedRows());
			changeMenuItem.addActionListener(ActionEvent -> changeBooking());
			
		}
		
    	contextMenu.show(this, e.getX(), e.getY() + point.y - vortex.getY());
    	
    }
	
	private void showSidePanel(JFrame parentFrame, Component parentComponent) {
		
        // Create a JDialog for the side panel
        JDialog sidePanelDialog = new JDialog(parentFrame, "Menu", Dialog.ModalityType.APPLICATION_MODAL);
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
		
		if (currentView.equals(AdminViews.bookings)) {
			
			//Employee button
			btnEmployees = new JButton("View Employee data");
			btnEmployees.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "employees");
					currentView = AdminViews.employees;
					
					sidePanelDialog.dispose();
				}
			});
			btnEmployees.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnEmployees.setBounds(30, 29, 83, 29);
			sidePanelContent.add(btnEmployees);
			
			// Statistics button
			btnStatistics = new JButton("View Statistics");
			btnStatistics.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "statistics");
					currentView = AdminViews.statistics;
					sidePanelDialog.dispose();
				}
			});
			btnStatistics.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnStatistics.setBounds(30, 29, 83, 29);
			sidePanelContent.add(btnStatistics);
			
		}
		
		if (currentView.equals(AdminViews.employees)) {
			
			// Booking button
			btnBookings = new JButton("View Booking data");
			btnBookings.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "bookings");
					currentView = AdminViews.bookings;
					sidePanelDialog.dispose();
				}
			});
			btnBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnBookings.setBounds(30, 29, 83, 29);
			sidePanelContent.add(btnBookings);
			
			// Statistics button
			btnStatistics = new JButton("View Statistics");
			btnStatistics.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "statistics");
					currentView = AdminViews.statistics;
					sidePanelDialog.dispose();
				}
			});
			btnStatistics.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnStatistics.setBounds(1, 2, 83, 29);
			sidePanelContent.add(btnStatistics);
		}
		
		if (currentView.equals(AdminViews.statistics)) {
			
			// Booking button
			btnBookings = new JButton("View Booking data");
			btnBookings.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "bookings");
					currentView = AdminViews.bookings;
					sidePanelDialog.dispose();
				}
			});
			btnBookings.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnBookings.setBounds(30, 29, 83, 29);
			sidePanelContent.add(btnBookings);
			
			// Employee button
			btnEmployees = new JButton("View Employee data");
			btnEmployees.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cardLayout.show(cardPanel, "employees");
					currentView = AdminViews.employees;
					
					sidePanelDialog.dispose();
				}
			});
			btnEmployees.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			btnEmployees.setBounds(30, 29, 83, 29);
			sidePanelContent.add(btnEmployees);
			
		}

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
	private void getBookings() {
		
		tblBooking.setModel(new DefaultTableModel() {
			
			private static final long serialVersionUID = 6471753810214881709L;

			@Override
			// Makes all cells un-changeable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		});
		tblBooking.setColumnControlVisible(true);
        tblBooking.setColumnModel(new DefaultTableColumnModelExt());

		model = (DefaultTableModel) tblBooking.getModel();
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
		
		tblBooking.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
	}
	
	@SuppressWarnings("unchecked")
	private void getEmployees() {
		tblEmployee.setModel(new DefaultTableModel() {
			
			private static final long serialVersionUID = 942783462783647142L;

			@Override
			// Makes all cells un-changeable
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		});
		tblEmployee.setColumnControlVisible(true);
        tblEmployee.setColumnModel(new DefaultTableColumnModelExt());

		model = (DefaultTableModel) tblEmployee.getModel();
		objects = DatabaseManager.sql_getAllEmployees();
		
		String[] colNames = (String[]) objects.get("colNames");
		employees = (ArrayList<String[]>) objects.get("employees");
		
		model.setColumnIdentifiers(colNames);
		
		if (employees != null) {
			for (String[] employee : employees) {
				model.addRow(employee);
			}
		}
		
		tblEmployee.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	}
	
	public void getData() {
		getBookings();
		getEmployees();
	}
	
	public void clearBookings() {
		tblBooking.removeAll();
	}

}
