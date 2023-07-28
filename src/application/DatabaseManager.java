package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseManager {
	
	//Variable declarations
	public static Connection connection;
	
	public static void initialiseDBMS() {
		String url = "jdbc:mysql://localhost:3306/desk_book";
		String username = "MyUsername";
		String password = "MyPassword";
		
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void sql_closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean sql_loginSearch(String email, String pass) {
		boolean response = false;
		
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/login_validation.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, pass);
			ResultSet resultSet = statement.executeQuery();
			
			response = resultSet.next();
			LoginScreen.currentEmployeeID = resultSet.getInt("emp_id");
			LoginScreen.currentEmployeeName = (String) resultSet.getString("name");
			
			resultSet.close();
			statement.close();
		} catch (SQLException | IOException e) {
		}
		
		return response;
	}
	
	public static void sql_requestSignup(String name, String email, String pass) {
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/signup_validation.sql")), StandardCharsets.UTF_8);
			PreparedStatement validationStatement = connection.prepareStatement(query);
			validationStatement.setString(1, name);
			validationStatement.setString(2, email);
			validationStatement.setString(3, pass);
			ResultSet resultSet = validationStatement.executeQuery();
			
			if (resultSet.next()) {
				return;
			} else {
				String addInfo = new String(Files.readAllBytes(Paths.get("src/queries/add_info.sql")), StandardCharsets.UTF_8);
				PreparedStatement addInfoStatement = connection.prepareStatement(addInfo);
				addInfoStatement.setString(1, name);
				addInfoStatement.setString(2, email);
				addInfoStatement.setString(3, pass);
				addInfoStatement.execute();
				
				addInfoStatement.close();
			}
			
			validationStatement.close();
			resultSet.close();
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sql_createBooking(int empID, int desk, String date, String startTime, String endTime, int duration, DeskBook deskBook) {
		try {
			
			Date _date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Date today = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
			
			if (_date.after(today)) {
				String query = new String(Files.readAllBytes(Paths.get("src/queries/create_booking.sql")), StandardCharsets.UTF_8);
				PreparedStatement creationStatement = connection.prepareStatement(query);
				creationStatement.setInt(1, empID);
				creationStatement.setInt(2, desk);
				creationStatement.setString(3, date);
				creationStatement.setString(4, startTime);
				creationStatement.setString(5, endTime);
				creationStatement.setFloat(6, duration);
				creationStatement.execute();
				
				creationStatement.close();
			} else {
				GlobalErrorBox.showError(deskBook, "Error: Please ensure selected date is after current date.");				
			}
			
		} catch (ParseException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void sql_deleteBooking(int bookID) {
		
		try {
			
			String query = new String(Files.readAllBytes(Paths.get("src/queries/delete_booking.sql")), StandardCharsets.UTF_8);
			PreparedStatement deletionStatement = connection.prepareStatement(query);
			deletionStatement.setInt(1, bookID);
			deletionStatement.execute();
			
			deletionStatement.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sql_changeBooking (int bookID, int _deskNum, String _date, String _timeStart, String _timeEnd, int _duration) {
		
		try {
			
			String query = new String(Files.readAllBytes(Paths.get("src/queries/change_booking.sql")), StandardCharsets.UTF_8);
			PreparedStatement changeStatement = connection.prepareStatement(query);
			changeStatement.setInt(1, _deskNum);
			changeStatement.setString(2, _date);
			changeStatement.setString(3, _timeStart);
			changeStatement.setString(4, _timeEnd);
			changeStatement.setInt(5, _duration);
			changeStatement.setInt(6, bookID);
			changeStatement.execute();
			
			changeStatement.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static ArrayList<Booking> sql_upcomingBookings() {
		final int empID = LoginScreen.currentEmployeeID;
		ArrayList<Booking> upcomingBookings = new ArrayList<Booking>();
		
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/get_bookings.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, empID);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Date _date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date"));
				Date today = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
				
				if (_date.after(today)) {
					int bookID = resultSet.getInt("book_id");
					int desk = resultSet.getInt("desk_id");
					String date = resultSet.getString("date");
					String startTime = resultSet.getString("time_start");
					String endTime = resultSet.getString("time_end");
					int duration = resultSet.getInt("duration");
					
					upcomingBookings.add(new Booking(bookID, empID, desk, date, startTime, endTime, duration));
				}
				
			}
			
			resultSet.close();
			statement.close();
			
		} catch (IOException | SQLException | ParseException e) {
			e.printStackTrace();
		}
		
		return upcomingBookings;
	}

	public static ArrayList<Booking> sql_bookingHistory() {
		
		final int empID = LoginScreen.currentEmployeeID;
		
		ArrayList<Booking> bookingHistory = new ArrayList<Booking>();
		
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/get_bookings.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, empID);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				
				Date _date = new SimpleDateFormat("yyyy-MM-dd").parse(resultSet.getString("date"));
				Date today = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
				
				if (_date.before(today)) {
					int bookID = resultSet.getInt("book_id");
					int desk = resultSet.getInt("desk_id");
					String date = resultSet.getString("date");
					String startTime = resultSet.getString("time_start");
					String endTime = resultSet.getString("time_end");
					int duration = resultSet.getInt("duration");
					
					bookingHistory.add(new Booking(bookID, empID, desk, date, startTime, endTime, duration));
				}
				
			}
			
			resultSet.close();
			statement.close();
			
		} catch (IOException | SQLException | ParseException e) {
			e.printStackTrace();
		}
		
		return bookingHistory;
		
	}
}