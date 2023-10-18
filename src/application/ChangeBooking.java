package application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ChangeBooking extends CreateBooking {

	private static final long serialVersionUID = -671413112921264569L;
	
	//Variable declarations
	private int bookID = 0;

	//Constructor method
	public ChangeBooking(DeskBook deskBook) {
		super(deskBook);
		setupPanel();
	}

	//Called from DeskBook - stores and alters the current booking ID that that needs to be changed
	public final void setBookID(int bookID) {
		this.bookID = bookID;
	}
	
	private void setupPanel() {
		lblNewBooking.setText("Change booking");
		btnCreate.setText("Update booking");
		btnCreate.removeMouseListener(mouseAdapterCreate);
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Booking booking;
				if (chkbxAllDay.isSelected()) {
					booking = new Booking(bookID, LoginScreen.currentEmployeeID, deskBook.getFloorplan().selectedDesk, datePicker.getText(), minTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(), maxTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(), (int) Duration.between(minTime, maxTime).toMinutes());
				} else {
					booking = new Booking(bookID, LoginScreen.currentEmployeeID, deskBook.getFloorplan().selectedDesk, datePicker.getText(), timePickerFrom.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), timePickerTo.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), (int) Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes());
				}
				
				if (validateBooking(booking)) {
					DatabaseManager.sql_createBooking(booking.getEmpID(), booking.getDesk(), booking.getDate(), booking.getTimeStart(), booking.getTimeEnd(), booking.getDuration(), deskBook);
				} else {
					GlobalErrorBox.showError(deskBook, "Error: Booking not valid for these timings (change timings)");
				}

				deskBook.showDash();
			}
		});
	
		btnFloorplan.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskBook.showFloorplan(false);
				setupFloorplan();
			}
		});
	}

}