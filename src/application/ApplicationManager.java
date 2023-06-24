package application;

import java.awt.EventQueue;

public class ApplicationManager {
	
	public DeskBook deskBook;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeskBook frame = new DeskBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		deskBook = new DeskBook();
		deskBook.setVisible(true);
	}

}
