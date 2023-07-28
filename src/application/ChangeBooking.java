package application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ChangeBooking extends CreateBooking {

	private static final long serialVersionUID = -671413112921264569L;
	
	//Variable declarations
	private int bookID = 0;

	public final void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public ChangeBooking(DeskBook deskBook) {
		super(deskBook);
		setupPanel();
	}
	
	private void setupPanel() {
		lblNewBooking.setText("Change booking");
		btnCreate.setText("Update booking");
		btnCreate.removeMouseListener(mouseAdapterCreate);
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				validateBooking();
					
				if (chkbxAllDay.isSelected()) {
					duration = Duration.between(minTime, maxTime).toMinutes();
					DatabaseManager.sql_changeBooking(
							bookID,
							deskBook.getFloorplan().selectedDesk,
							datePicker.getText(),
							minTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(),
							maxTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(),
							(int) duration);
				} else {
					duration = Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes();
					DatabaseManager.sql_changeBooking(
							bookID,
							deskBook.getFloorplan().selectedDesk,
							datePicker.getText(),
							timePickerFrom.getText(),
							timePickerTo.getText(),
							(int) duration);
				}
				
				deskBook.showDash();
			}
		});
	
		btnFloorplan.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskBook.showFloorplan(false);
			}
		});
	}

}
