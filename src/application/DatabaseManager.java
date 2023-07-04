package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

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
	
	public static void sql_createBooking(int empID, int desk, String date, String startTime, String endTime, int duration) {
		try {
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
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Booking> sql_bookingList() {
		final int empID = LoginScreen.currentEmployeeID;
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/upcoming_bookings.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, empID);
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				int bookID = resultSet.getInt("book_id");
				int desk = resultSet.getInt("desk");
				String date = resultSet.getString("date");
				String startTime = resultSet.getString("time_start");
				String endTime = resultSet.getString("time_end");
				int duration = resultSet.getInt("duration");
				
				bookings.add(new Booking(bookID, empID, desk, date, startTime, endTime, duration));
			}
			
			resultSet.close();
			statement.close();
			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		return bookings;
	}
}
