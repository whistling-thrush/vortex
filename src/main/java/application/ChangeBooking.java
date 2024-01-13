package main.java.application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import javax.sound.midi.Soundbank;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeBooking extends CreateBooking {

	private static final long serialVersionUID = -671413112921264569L;
	
	//Variable declarations
	private int bookID = 0;
	
	public boolean goBackToAdmin = false;

	//Constructor method
	public ChangeBooking(Vortex vortex) {
		super(vortex);
		setupPanel();
	}

	//Called from Vortex - stores and alters the current booking ID that that needs to be changed
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
					booking = new Booking(bookID, LoginScreen.currentEmployeeID, vortex.getFloorplan().selectedDesk, datePicker.getText(), minTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(), maxTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(), (int) Duration.between(minTime, maxTime).toMinutes());
				} else {
					booking = new Booking(bookID, LoginScreen.currentEmployeeID, vortex.getFloorplan().selectedDesk, datePicker.getText(), timePickerFrom.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), timePickerTo.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), (int) Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes());
				}
				
				if (validateBooking(booking)) {
					DatabaseManager.sql_changeBooking(booking.getBookID(), booking.getDesk(), booking.getDate(), booking.getTimeStart(), booking.getTimeEnd(), booking.getDuration());
				} else {
					GlobalErrorBox.showError(vortex, "Error: Booking not valid for these timings (change timings)");
				}

				if (goBackToAdmin) {
					vortex.showAdmin();
				} else {
					vortex.showDash();
				}
			}
		});
	
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(goBackToAdmin);
				if (goBackToAdmin) {
					vortex.showAdmin();
				} else {
					vortex.showDash();
				}
			}
		});
		
		btnFloorplan.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showFloorplan(false);
				setupFloorplan();
			}
		});
	}

}