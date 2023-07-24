package application;

import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

public class Floorplan extends JPanel {
	
	private static final long serialVersionUID = 6043243388012766414L;
	
	//Variable declarations
	private ArrayList<Element> desks;
	private JSVGCanvas svgCanvas;
	private String chosenDeskColor = "#e2e460";
	private String bookedDeskColor = "#e36e89";
	private String freeDeskColor = "#6eb8e3";
	
	public Dimension dimension;

	/**
	 * Create the panel.
	 */
	public Floorplan() {
		dimension = new Dimension(765, 1020);
		setupPanel();
	}
	
	private void setupPanel() {
		setBackground(Color.WHITE);
		
		// Initialize the list of desks
		desks = new ArrayList<>();
	
		svgCanvas = new JSVGCanvas();
		svgCanvas.setURI("lib/Assets/Floorplan.svg");
		svgCanvas.setSize(dimension);
		svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);

	}

	
	public void addFloorplan() {

		traverse(svgCanvas.getSVGDocument().getDocumentElement(), "Desk");
		add(svgCanvas);
	}
	
	
    // Method to extract elements with names containing "Desk"
	private void traverse(Element element, String substring) {
    	
    	if (element.getAttribute("id").startsWith(substring)) {
    			desks.add(element);
    			
    			EventTarget target = (EventTarget) element;
    			
                target.addEventListener("click", new EventListener() {
                    public void handleEvent(Event evt) {
                        if(evt.getType().equals("click")){
                        	changeColourOfDesk(chosenDeskColor, element);
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
		
		//Change colour of table
		
		
		//Change colour of seat
		
		
		//Change colour of support
		
	}
}