package application;

import javax.swing.JButton;
import javax.swing.JPanel;

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
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Floorplan extends JPanel {
	
	private static final long serialVersionUID = 6043243388012766414L;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblChooseFloor;
	private JSpinner spnnrFloorSelect;
	private JPanel buttonPanel;
	private JButton btnConfirm;
	private JButton btnGoBack;
	
	//Variable declarations
	private Element deskSelected;
	private ArrayList<Element> desks;
	private boolean deskBookedBool;
	private boolean showCreateBooking = true;
	private int maxFloor;
	private int bookID;
	private JSVGCanvas svgCanvas;
	private String chosenDeskColor = "#e2e460";
	private String bookedDeskColor = "#e36e89";
	private String freeDeskColor = "#6eb8e3";
	
	public Dimension dimension;
	public int selectedDesk;
	public char selectedCluster;
	public int selectedFloor;

	/**
	 * Create the panel.
	 */
	public Floorplan(DeskBook deskBook) {
		this.deskBook = deskBook;
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
		svgCanvas.setURI("lib/Assets/Floorplan.svg");
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
		spnnrFloorSelect.setBounds(47, 67, 105, 26);
		buttonPanel.add(spnnrFloorSelect);
		
		btnConfirm = new JButton();
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectedDesk += (((int) spnnrFloorSelect.getValue()) - 1) * 48;
				if (showCreateBooking) {	
					deskBook.showCreate();
				} else { 
					deskBook.showChangeBooking(bookID); 
				}
			}
		});
		btnConfirm.setText("Confirm desk?");
		btnConfirm.setBounds(33, 211, 134, 29);
		buttonPanel.add(btnConfirm);
		
		btnGoBack = new JButton();
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskSelected = null;
				if (showCreateBooking) {	
					deskBook.showCreate();
				} else { 
					deskBook.showChangeBooking(bookID); 
				}
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
    		
    		if (!element.equals(deskSelected)) {
    			changeColourOfDesk(freeDeskColor, element);
    		}
    		
    		desks.add(element);	
    			
			deskBookedBool = checkIfBooked(element);
			
			if (deskBookedBool) {
				changeColourOfDesk(bookedDeskColor, element);
			} else {
				EventTarget target = (EventTarget) element;
				
				target.addEventListener("click", new EventListener() {
					public void handleEvent(Event evt) {
						if (evt.getType().equals("click")) {
							changeColourOfDesk(freeDeskColor, deskSelected);
							changeColourOfDesk(chosenDeskColor, element);
							deskSelected = element;
							selectedDesk = Integer.parseInt(element.getAttribute("id").replace("Desk", ""));
						}
					}
				}, false);
			}
    			
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

	private boolean checkIfBooked(Element element) {
		return false;
	}
}