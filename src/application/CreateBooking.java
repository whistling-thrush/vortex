package application;

import java.awt.Dimension;
import java.awt.Font;
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

public class CreateBooking extends JPanel {

	private static final long serialVersionUID = 7998788512651030012L;
	
	//Variable declarations
	public Dimension dimension = new Dimension(500, 600);
	protected long duration;
	protected LocalTime minTime;
	protected LocalTime maxTime;
	
	//Component declarations
	protected DeskBook deskBook;
	protected JLabel lblNewBooking;
	protected JLabel lblChooseDate;
	protected DatePickerSettings settingsDate;
	protected DatePicker datePicker;
	protected JLabel lblChooseTime;
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
	public CreateBooking(DeskBook deskBook) {
		this.deskBook = deskBook;
		setLayout(null);
		setSize(dimension);
		setupPanel();
	}
	
	private void setupPanel () {
		lblNewBooking = new JLabel("Create new booking");
		lblNewBooking.setSize(260, 70);
		lblNewBooking.setLocation(60, 59);
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
		
		lblChooseTime = new JLabel("Choose timing (start and end)");
		lblChooseTime.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblChooseTime.setBounds(60, 266, 205, 16);
		add(lblChooseTime);
		
		chkbxAllDay = new JCheckBox("All-day");
		chkbxAllDay.setHorizontalAlignment(SwingConstants.TRAILING);
		chkbxAllDay.setBounds(368, 263, 90, 23);
		add(chkbxAllDay);
		
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
		timePickerTo.setBounds(265, 294, 193, 30);
		add(timePickerTo);
		
		//Show floorplan button
		btnFloorplan = new JButton("Show floorplan");
		btnFloorplan.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskBook.showFloorplan(true);
			}
		});
		btnFloorplan.setBounds(60, 390, 174, 36);
		btnFloorplan.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		add(btnFloorplan);
		
		//Desk chosen label
		lblDeskChosen = new JLabel();
		lblDeskChosen.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDeskChosen.setBounds(253, 401, 205, 16);
		add(lblDeskChosen);
		
		//Go back button
		btnGoBack = new JButton("Go back");
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				deskBook.showDash();
			}
		});
		btnGoBack.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnGoBack.setBounds(60, 465, 174, 36);
		add(btnGoBack);
		
		btnCreate = new JButton("Create booking");
		mouseAdapterCreate = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				validateBooking();
				
				if (chkbxAllDay.isSelected()) {
					duration = Duration.between(minTime, maxTime).toMinutes();
					DatabaseManager.sql_createBooking(LoginScreen.currentEmployeeID,
							deskBook.getFloorplan().selectedDesk,
							datePicker.getText(),
							minTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(),
							maxTime.format(DateTimeFormatter.ofPattern("hh:mm a")).toString(),
							(int) duration,
							deskBook);
				} else {
					duration = Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes();
					DatabaseManager.sql_createBooking(
							LoginScreen.currentEmployeeID,
							deskBook.getFloorplan().selectedDesk,
							datePicker.getText(),
							timePickerFrom.getText(),
							timePickerTo.getText(),
							(int) duration,
							deskBook);
				}
				
				deskBook.showDash();
			}
		};
		btnCreate.addMouseListener(mouseAdapterCreate);
		btnCreate.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnCreate.setBounds(284, 465, 174, 36);
		add(btnCreate);
		
	}

	protected void validateBooking() {
		if (timePickerFrom.getTime().isAfter(timePickerTo.getTime())) {
			timePickerFrom.setText(null);
			timePickerTo.setText(null);
		}
	}
	
	public void resetCreateBooking() {
		 
	}
}
