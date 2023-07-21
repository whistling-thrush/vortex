package application;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Floorplan extends JPanel {
	
	private static final long serialVersionUID = 6043243388012766414L;
	
	//Variable declarations
	private List<Rectangle> desks;
	public Dimension dimension;

	/**
	 * Create the panel.
	 */
	public Floorplan() {
		dimension = new Dimension(500, 400);
		setupPanel();
	}
	
	private void setupPanel() {
		setBackground(Color.WHITE);
		
		// Initialize the list of desks
		desks = new ArrayList<>();
		
		// Add some sample desks
		addDesk(50, 50, 100, 80);
		addDesk(200, 50, 100, 80);
		addDesk(50, 150, 100, 80);
		addDesk(200, 150, 100, 80);
	}

	private void addDesk(int x, int y, int width, int height) {
		desks.add(new Rectangle(x, y, width, height));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw the desks
		for (Rectangle desk : desks) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(desk.x, desk.y, desk.width, desk.height);
		}
	}
}