package main.java.application;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.time.LocalTime;
import java.util.ArrayList;

public class Floorplan extends JPanel {
	
	private static final long serialVersionUID = 6043243388012766414L;
	
	//Component declarations
	private Vortex vortex;
	private CreateBooking createBooking;
	private ChangeBooking changeBooking;
	private JLabel lblChooseFloor;
	private JSpinner spnnrFloorSelect;
	private JPanel buttonPanel;
	private JButton btnConfirm;
	private JButton btnGoBack;
	
	//Variable declarations
	private Element deskSelectedElement;
	private ArrayList<Element> desks;
	private boolean showCreateBooking = true;
	private int maxFloor;
	private int bookID;
	private JSVGCanvas svgCanvas;
	private String chosenDeskColor = "#e2e460";
	private String bookedDeskColor = "#e36e89";
	private String freeDeskColor = "#6eb8e3";
	
	public Dimension dimension;
	public int selectedDesk;

	/**
	 * Create the panel.
	 */
	public Floorplan(Vortex vortex, CreateBooking createBooking, ChangeBooking changeBooking) {
		this.createBooking = createBooking;
		this.changeBooking = changeBooking;
		this.vortex = vortex;
		dimension = new Dimension(965, 1020);
		setupPanel();
	}
	
	private void setupPanel() {
		
		maxFloor = 4;
		setLayout(new BorderLayout());
		initButtonPanel();
		
		// Initialize the list of desks
		desks = new ArrayList<>();
	
		svgCanvas = new JSVGCanvas();
		svgCanvas.setURI("src/main/resources/assets/Floorplan.svg");
		svgCanvas.setSize(dimension);
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);

	}
	
	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, dimension.height));
		add(buttonPanel, BorderLayout.EAST);
		buttonPanel.setLayout(null);
		
		lblChooseFloor = new JLabel("Choose floor");
		lblChooseFloor.setBounds(52, 39, 96, 29);
		buttonPanel.add(lblChooseFloor);
		
		SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(1, 1, maxFloor, 1);
		spnnrFloorSelect = new JSpinner(spinnerNumberModel);
		spnnrFloorSelect.setEditor(new JSpinner.DefaultEditor(spnnrFloorSelect));
		spnnrFloorSelect.setBounds(47, 67, 105, 26);
		spnnrFloorSelect.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						resetFloorplan();
						if (showCreateBooking) {	
							createBooking.setupFloorplan();
						} else {
							changeBooking.setupFloorplan();
						}
					}
				});
			}
		});
		buttonPanel.add(spnnrFloorSelect);
		
		btnConfirm = new JButton();
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						selectedDesk += (((int) spnnrFloorSelect.getValue()) - 1) * 48;
						if (showCreateBooking) {	
							vortex.showCreate();
						} else { 
							vortex.showChangeBooking(bookID); 
						}
					}
				});
			}
		});
		btnConfirm.setText("Confirm desk");
		btnConfirm.setBounds(33, 211, 134, 29);
		buttonPanel.add(btnConfirm);
		
		btnGoBack = new JButton();
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						deskSelectedElement = null;
						if (showCreateBooking) {	
							vortex.showCreate();
						} else { 
							vortex.showChangeBooking(bookID); 
						}
					}
				});
			}
		});
		btnGoBack.setText("Go back");
		btnGoBack.setBounds(66, 252, 75, 29);
		buttonPanel.add(btnGoBack);
		
	}

	
	public void addFloorplan(boolean showCreateBooking, int bookID) {
		this.showCreateBooking = showCreateBooking;
		this.bookID = bookID;
		traverse(svgCanvas.getSVGDocument().getDocumentElement(), "Desk");
		add(svgCanvas, BorderLayout.WEST);
	}
	
	
    // Method to extract elements with names containing "Desk"
	private void traverse(Element element, String substring) {
    	
    	if (element.getAttribute("id").startsWith(substring)) {
    		
    		if (!element.equals(deskSelectedElement)) {
    			changeColourOfDesk(freeDeskColor, element);
    		}
    		
    		desks.add(element);	
			
			EventTarget target = (EventTarget) element;
			
			target.addEventListener("click", new EventListener() {
				public void handleEvent(Event evt) {
					
					NodeList children = element.getChildNodes();
					String colour = "";
					int i = 0;
					while ((colour != bookedDeskColor || colour != freeDeskColor || colour != chosenDeskColor) && i < children.getLength()) {
						Node child = children.item(i);
						try {
							Element childElement = (Element) child;
							colour = childElement.getAttribute("fill");
						} catch (Exception e) {
						}
						i++;
					}

					if (evt.getType().equals("click") && !colour.equals(bookedDeskColor)) {
						changeColourOfDesk(freeDeskColor, deskSelectedElement);
						changeColourOfDesk(chosenDeskColor, element);
						deskSelectedElement = element;
						selectedDesk = Integer.parseInt(element.getAttribute("id").replace("Desk", ""));
					}
				}
			}, false);
    			
    	} else {

    		NodeList children = element.getChildNodes();
    		
    		for (int i = 0; i < children.getLength(); i++) {
    			Node child = children.item(i);
    			try {
    				Element childElement = (Element) child;
    				traverse(childElement, substring);
    			} catch (Exception e) {
    			}
    		}
    	}	
 	
    }
	
	private void changeColourOfDesk (String colour, Element desk) {
		
		if (desk == null) {
			return;
		} else {
			
			NodeList deskChildren = desk.getChildNodes();
			
			for (int i = 0; i < deskChildren.getLength(); i++) {
				try {
					Element deskChild = (Element) deskChildren.item(i);
					
					if (deskChild.getAttribute("id").startsWith("Table") ||
							deskChild.getAttribute("id").startsWith("Seat") || 
							(deskChild.getAttribute("fill") != null &&
							deskChild.getAttribute("fill-rule").equals("evenodd"))) {
						deskChild.setAttribute("fill", colour);
					} else if (deskChild.getLocalName() == "mask") {
						continue;
					} else {
						changeColourOfDesk(colour, deskChild);
					}
				} catch (Exception e) { 
				}
				
			}
		}
	}

	public void resetFloorplan() {
		selectedDesk = 0;
		for (Element desk : desks) {
			changeColourOfDesk(freeDeskColor, desk);
		}
	}

	public void blockBookedDesks(boolean chkbxAllDay, String date, String timeStart, String timeEnd) {
		final ArrayList<Booking> bookings = Vortex.bookings;		

		for (Booking booking : bookings) {
			//Checks if it is on the same day and for the same desk
			if (date.equals(booking.getDate())) {
				LocalTime bookingStart = LocalTime.parse(timeStart);
				LocalTime bookingEnd = LocalTime.parse(timeEnd);
				LocalTime existingBookingStart = LocalTime.parse(booking.getTimeStart());
				LocalTime existingBookingEnd = LocalTime.parse(booking.getTimeEnd());
				
				//Checks if a booking exists at that time
				if ((bookingStart.compareTo(existingBookingEnd) < 0 && bookingStart.compareTo(existingBookingStart) >= 0) ||
				(bookingEnd.compareTo(existingBookingEnd) <= 0 && bookingEnd.compareTo(existingBookingStart) > 0) ||
				(bookingStart.compareTo(existingBookingStart) <= 0 && bookingEnd.compareTo(existingBookingEnd) >= 0)) {
					if (booking.getFloor() == (int)spnnrFloorSelect.getValue()) {
						changeColourOfDesk(bookedDeskColor, desks.get(48 - desk2Position(booking.getDesk())));
					}
				}
			}
		}
	}
	
	private int desk2Position(int deskNum) {
		while(deskNum > 48) { deskNum -= 48;}
		return deskNum;
	}
}