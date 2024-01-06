package main.java.application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import java.awt.Color;

public class CreateBooking extends JPanel {

	private static final long serialVersionUID = 7998788512651030012L;
	
	//Variable declarations
	public Dimension dimension = new Dimension(500, 600);
	protected long duration;
	protected LocalTime minTime;
	protected LocalTime maxTime;
	
	//Component declarations
	protected Vortex vortex;
	protected JLabel lblNewBooking;
	protected JLabel lblChooseDate;
	protected DatePickerSettings settingsDate;
	protected DatePicker datePicker;
	protected JLabel lblChooseTimeFrom;
	protected JLabel lblChooseTimeTo;
	protected TimePickerSettings settingsTime;
	protected TimePicker timePickerFrom;
	protected TimePicker timePickerTo;
	protected JButton btnFloorplan;
	protected JLabel lblDeskChosen;
	protected JButton btnGoBack;
	protected JCheckBox chkbxAllDay;
	protected JButton btnCreate;
	protected MouseAdapter mouseAdapterCreate;

	/**
	 * Create the panel.
	 */
	public CreateBooking(Vortex vortex) {
		this.vortex = vortex;
		setLayout(null);
		setSize(new Dimension(500, 550));
		setupPanel();
	}
	
	private void setupPanel () {
		lblNewBooking = new JLabel("Create new booking");
		lblNewBooking.setSize(260, 70);
		lblNewBooking.setLocation(120, 59);
		lblNewBooking.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		lblNewBooking.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblNewBooking);
		
		lblChooseDate = new JLabel("Choose date");
		lblChooseDate.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblChooseDate.setBounds(60, 163, 115, 16);
		add(lblChooseDate);
		
		settingsDate = new DatePickerSettings();
		settingsDate.setFormatForDatesCommonEra("yyyy-MM-dd");
		datePicker = new DatePicker(settingsDate);
		datePicker.setBounds(60, 191, 398, 30);
		add(datePicker);
		
		lblChooseTimeFrom = new JLabel("From:");
		lblChooseTimeFrom.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblChooseTimeFrom.setBounds(60, 266, 48, 16);
		add(lblChooseTimeFrom);
		
		settingsTime = new TimePickerSettings();
		settingsTime.setInitialTimeToNow();
		settingsTime.setAllowEmptyTimes(false);
		minTime = LocalTime.of(7, 0);
		maxTime = LocalTime.of(22, 0);
		settingsTime.generatePotentialMenuTimes(TimeIncrement.ThirtyMinutes, minTime, maxTime);
		settingsTime.setFormatForDisplayTime("hh:mm a");
		settingsTime.setFormatForMenuTimes("hh:mm a");
		
		timePickerFrom = new TimePicker(settingsTime);
		timePickerFrom.setBounds(60, 294, 193, 30);
		add(timePickerFrom);
		
		timePickerTo = new TimePicker(settingsTime);
		timePickerTo.setBounds(60, 373, 193, 30);
		add(timePickerTo);
		
		chkbxAllDay = new JCheckBox("All-day");
		chkbxAllDay.setHorizontalAlignment(SwingConstants.TRAILING);
		chkbxAllDay.setBounds(368, 263, 90, 23);
		chkbxAllDay.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				timePickerFrom.setEnabled(e.getStateChange() != ItemEvent.SELECTED);
				timePickerTo.setEnabled(e.getStateChange() != ItemEvent.SELECTED);
			}
		});
		add(chkbxAllDay);
		
		//Show floorplan button
		btnFloorplan = new JButton("Show floorplan");
		btnFloorplan.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showFloorplan(true);
				setupFloorplan();
			}
		});
		btnFloorplan.setBounds(60, 482, 174, 36);
		btnFloorplan.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		add(btnFloorplan);
		
		//Desk chosen label
		lblDeskChosen = new JLabel();
		lblDeskChosen.setBackground(new Color(255, 255, 255));
		lblDeskChosen.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDeskChosen.setBounds(305, 340, 174, 36);
		add(lblDeskChosen);
		
		//Go back button
		btnGoBack = new JButton("<");
		btnGoBack.setBorderPainted(false);
		btnGoBack.setBackground(SystemColor.window);
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vortex.showDash();
			}
		});
		btnGoBack.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnGoBack.setBounds(6, 80, 107, 36);
		add(btnGoBack);
		
		btnCreate = new JButton("Create booking");
		mouseAdapterCreate = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Booking booking;
				if (chkbxAllDay.isSelected()) {
					booking = new Booking(0, LoginScreen.currentEmployeeID, vortex.getFloorplan().selectedDesk, datePicker.getText(), minTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(), maxTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(), (int) Duration.between(minTime, maxTime).toMinutes());
				} else {
					timePickerFrom.getText();
					booking = new Booking(0, LoginScreen.currentEmployeeID, vortex.getFloorplan().selectedDesk, datePicker.getText(), timePickerFrom.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), timePickerTo.getTime().format(DateTimeFormatter.ofPattern("HH:mm")), (int) Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes());
				}
				
				if (validateBooking(booking)) {
					DatabaseManager.sql_createBooking(booking.getEmpID(), booking.getDesk(), booking.getDate(), booking.getTimeStart(), booking.getTimeEnd(), booking.getDuration(), vortex);
				} else {
					GlobalErrorBox.showError(vortex, "Error: Booking not valid for these timings (change timings)");
				}
				
				Vortex.bookings.add(booking);
				
				vortex.showDash();
			}
		};
		btnCreate.addMouseListener(mouseAdapterCreate);
		btnCreate.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnCreate.setBounds(284, 482, 174, 36);
		add(btnCreate);
		
		lblChooseTimeTo = new JLabel("To:");
		lblChooseTimeTo.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblChooseTimeTo.setBounds(60, 345, 48, 16);
		add(lblChooseTimeTo);
		
	}

	protected boolean validateBooking(Booking booking) {
		boolean flag = true;
		
		//Validates booking and checks if the booking passed in the function already exists
		if (DatabaseManager.sql_checkBookingExistance(booking)) {
			flag = false;
		}

		return flag;
	}

	public void setupFloorplan() {
		boolean chkbxAllDaySelected = chkbxAllDay.isSelected();
		String date = datePicker.getText();
		String timeStart, timeEnd;
		if (chkbxAllDaySelected) {
			timeStart = minTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString();
			timeEnd = maxTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString();
		} else {
			timeStart = timePickerFrom.getTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString();
			timeEnd = timePickerTo.getTime().format(DateTimeFormatter.ofPattern("HH:mm")).toString();
		}

		vortex.getFloorplan().blockBookedDesks(chkbxAllDaySelected, date, timeStart, timeEnd);
	}
	
	//Resets the Create Booking Panel
	public void resetCreateBooking() {
		 
	}
}
