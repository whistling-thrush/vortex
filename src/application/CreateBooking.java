package application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.time.Duration;
import java.time.LocalTime;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

public class CreateBooking extends JPanel {

	private static final long serialVersionUID = 7998788512651030012L;
	
	//Variable declarations
	public Dimension dimension = new Dimension(500, 600);
	private long duration;
	private LocalTime minTime;
	private LocalTime maxTime;
	
	//Component declarations
	private DeskBook deskBook;
	private JLabel lblNewBooking;
	private JLabel lblChooseDate;
	private DatePickerSettings settingsDate;
	private DatePicker datePicker;
	private JLabel lblChooseTime;
	private TimePickerSettings settingsTime;
	private TimePicker timePickerFrom;
	private TimePicker timePickerTo;
	private JLabel lblChooseDesk;
	private SpinnerNumberModel model;
	private JSpinner deskPicker;
	private JButton btnCreate;
	private JButton btnGoBack;
	private JCheckBox chkbxAllDay;

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
		lblNewBooking.setSize(261, 70);
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
		
		lblChooseDesk = new JLabel("Choose desk number (from 1 to 50)");
		lblChooseDesk.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblChooseDesk.setBounds(60, 367, 261, 16);
		add(lblChooseDesk);
		
		model = new SpinnerNumberModel(1, 1, 50, 1);
		deskPicker = new JSpinner(model);
		deskPicker.setBounds(60, 395, 109, 30);
		add(deskPicker);
		
		btnCreate = new JButton("Create booking");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateBooking();
				duration = chkbxAllDay.isSelected() ? Duration.between(minTime, maxTime).toMinutes() : Duration.between(timePickerFrom.getTime(), timePickerTo.getTime()).toMinutes();
				DatabaseManager.sql_createBooking(LoginScreen.currentEmployeeID,
						(int) deskPicker.getValue(),
						datePicker.getText(),
						timePickerFrom.getText(),
						timePickerTo.getText(),
						(int) duration);
			}
		});
		btnCreate.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnCreate.setBounds(284, 465, 174, 36);
		add(btnCreate);
		
		btnGoBack = new JButton("Go back");
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deskBook.showDash();
			}
		});
		btnGoBack.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnGoBack.setBounds(60, 465, 174, 36);
		add(btnGoBack);
	}

	private void validateBooking() {
		if (timePickerFrom.getTime().isAfter(timePickerTo.getTime())) {
			timePickerFrom.setText(null);
			timePickerTo.setText(null);
		}
	}

}
