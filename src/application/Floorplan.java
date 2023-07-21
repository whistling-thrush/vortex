package application;

import javax.swing.JPanel;
import javax.xml.transform.Source;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Floorplan extends JPanel {
	
	private static final long serialVersionUID = 6043243388012766414L;
	
	//Variable declarations
	private List<Rectangle> desks;
	private Rectangle selectedDesk;
	private Rectangle clickedDesk;
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
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				deskHover(e.getX(), e.getY());
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskClick(e.getX(), e.getY());
			}
		});

	}

	private void addDesk(int x, int y, int width, int height) {
		desks.add(new Rectangle(x, y, width, height));
	}
	
	private void deskClick(int x, int y) {
		for (Rectangle desk : desks) {
			if (desk.contains(x, y)) {
				clickedDesk = desk;
				repaint();
				break;
			}
		}
	}
	
	private void deskHover(int x, int y) {
		for (Rectangle desk : desks) {
			if (desk.contains(x, y)) {
				selectedDesk = desk;
				repaint();
				break;
			} else {
				selectedDesk = null;
				repaint();
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draw the desks
		for (Rectangle desk : desks) {
			
			if (desk == clickedDesk && desk != selectedDesk) {
				g.setColor(Color.blue);
			} else if (desk == selectedDesk) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}
			
			g.fillRect(desk.x, desk.y, desk.width, desk.height);
		}
	}
}