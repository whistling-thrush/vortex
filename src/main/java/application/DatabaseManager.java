package main.java.application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
	
	//Variable declarations
	public static Connection connection;
	
	public static void initialiseDBMS() {
		String url = "jdbc:mysql://localhost:3306/deskbook";
		String username = info.getUsername();
		String password = info.getPassword();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
			
	}

	private static Path pathToResource(String resourcePath) throws URISyntaxException, IOException {
		// Get the URL of the resource
		URI resourceUri = DatabaseManager.class.getClassLoader().getResource(resourcePath).toURI();
	
		// Convert the URL to a Path
		Path path;
		if ("jar".equals(resourceUri.getScheme())) {
			// If the resource is located within a JAR file
			Map<String, String> env = new HashMap<>(); // Provide an empty map
			FileSystem fileSystem = FileSystems.newFileSystem(resourceUri, env);
			path = fileSystem.getPath(resourceUri.getPath());
		} else {
			// If the resource is located outside a JAR file
			path = Paths.get(resourceUri);
		}
	
		return path;
	}
	

	public static Map<String, Object> sql_getAllBookings() {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		Map<String, Object> map = new HashMap<>();

		try {
			String query = new String((Files.readAllBytes(pathToResource("queries/get_all_bookings.sql"))), StandardCharsets.UTF_8);
			
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				
				int bookID = resultSet.getInt("book_id");
				int empID = resultSet.getInt("emp_id");
				int desk = resultSet.getInt("desk_id");
				String date = resultSet.getString("date");
				String startTime = resultSet.getString("time_start");
				String endTime = resultSet.getString("time_end");
				int duration = resultSet.getInt("duration");
				
				bookings.add(new Booking(bookID, empID, desk, date, startTime, endTime, duration));
				
				map.put("bookings", bookings);
				
				String[] colNames = {"Desk number", "Employee ID", "Date (yyyy-mm-dd)", "From", "To", "Duration"};
				
				map.put("colNames", colNames);
			}
				
			
			resultSet.close();
			statement.close();
			
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return map;
		
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
			String query = new String((Files.readAllBytes(pathToResource("queries/login_validation.sql"))), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, pass);
			ResultSet resultSet = statement.executeQuery();
			
			response = resultSet.next();
			LoginScreen.currentEmployeeID = resultSet.getInt("emp_id");
			LoginScreen.currentEmployeeName = (String) resultSet.getString("name");
			
			resultSet.close();
			statement.close();
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static void sql_requestSignup(String name, String email, String pass) {
		try {
			String query = new String((Files.readAllBytes(pathToResource("queries/signup_validation.sql"))), StandardCharsets.UTF_8);
			PreparedStatement validationStatement = connection.prepareStatement(query);
			validationStatement.setString(1, name);
			validationStatement.setString(2, email);
			validationStatement.setString(3, pass);
			ResultSet resultSet = validationStatement.executeQuery();
			
			if (resultSet.next()) {
				return;
			} else {
				String addInfo = new String((Files.readAllBytes(pathToResource("queries/add_info.sql"))), StandardCharsets.UTF_8);
				PreparedStatement addInfoStatement = connection.prepareStatement(addInfo);
				addInfoStatement.setString(1, name);
				addInfoStatement.setString(2, email);
				addInfoStatement.setString(3, pass);
				addInfoStatement.execute();
				
				addInfoStatement.close();
			}
			
			validationStatement.close();
			resultSet.close();
			
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void sql_createBooking(int empID, int desk, String date, String startTime, String endTime, int duration, Vortex vortex) {
		try {
			
			Date _date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Date today = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
			
			if (_date.after(today)) {
				String query = new String((Files.readAllBytes(pathToResource("queries/create_booking.sql"))), StandardCharsets.UTF_8);
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
				GlobalErrorBox.showError(vortex, "Error: Please ensure selected date is after current date.");				
			}
			
		} catch (ParseException | SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static void sql_deleteBooking(int bookID) {
		
		try {
			
			String query = new String((Files.readAllBytes(pathToResource("queries/delete_booking.sql"))), StandardCharsets.UTF_8);
			PreparedStatement deletionStatement = connection.prepareStatement(query);
			deletionStatement.setInt(1, bookID);
			deletionStatement.execute();
			
			deletionStatement.close();
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sql_changeBooking (int bookID, int _deskNum, String _date, String _timeStart, String _timeEnd, int _duration) {
		
		try {
			
			String query = new String((Files.readAllBytes(pathToResource("queries/change_booking.sql"))), StandardCharsets.UTF_8);
			PreparedStatement changeStatement = connection.prepareStatement(query);
			changeStatement.setInt(1, _deskNum);
			changeStatement.setString(2, _date);
			changeStatement.setString(3, _timeStart);
			changeStatement.setString(4, _timeEnd);
			changeStatement.setInt(5, _duration);
			changeStatement.setInt(6, bookID);
			changeStatement.execute();
			
			changeStatement.close();
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Map<String, Object> sql_upcomingBookings() {
		final int empID = LoginScreen.currentEmployeeID;
		ArrayList<Booking> upcomingBookings = new ArrayList<Booking>();
		Map<String, Object> map = new HashMap<>();
		
		try {
			String query = new String((Files.readAllBytes(pathToResource("queries/get_bookings.sql"))), StandardCharsets.UTF_8);
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

			map.put("bookings", upcomingBookings);
			
			String[] colNames = {"Desk number", "Date (yyyy-mm-dd)", "From", "To", "Duration"};
			
			map.put("colNames", colNames);
			
		} catch (IOException | SQLException | ParseException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return map;
	}

	public static Map<String, Object> sql_bookingHistory() {
		
		final int empID = LoginScreen.currentEmployeeID;
		
		Map<String, Object> map = new HashMap<>();
		ArrayList<Booking> bookingHistory = new ArrayList<Booking>();
		
		try {
			String query = new String((Files.readAllBytes(pathToResource("queries/get_bookings.sql"))), StandardCharsets.UTF_8);
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
			
		} catch (IOException | SQLException | ParseException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		map.put("bookings", bookingHistory);
		
		String[] colNames = {"Desk number", "Date (yyyy-mm-dd)", "From", "To", "Duration"};
		
		map.put("colNames", colNames);
		
		return map;
		
	}

	//Checks if a booking already exists
	public static boolean sql_checkBookingExistance(Booking booking) {
		boolean bookingExists = false;
		@SuppressWarnings("unchecked")
		ArrayList<Booking> bookings = (ArrayList<Booking>) DatabaseManager.sql_getAllBookings().get("bookings");

		if (bookings != null) {
			for (Booking existingBooking : bookings) {
				//Checks if it is on the same day and for the same desk
				if ((booking.getDate().equals(existingBooking.getDate())) && (booking.getDesk() == existingBooking.getDesk())) {
					LocalTime existingBookingStart = LocalTime.parse(existingBooking.getTimeStart());
					LocalTime existingBookingEnd = LocalTime.parse(existingBooking.getTimeEnd());
					LocalTime bookingStart = LocalTime.parse(booking.getTimeStart());
					
					//Checks if a booking exists at that time
					if ((bookingStart.compareTo(existingBookingEnd) < 0 && bookingStart.compareTo(existingBookingStart) > 0)) {
						bookingExists = true;
						break;
					}
				}
			}
		}
		
		return bookingExists;
	}

	public static Map<String, Object> sql_getAllEmployees() {
		Map<String, Object> map = new HashMap<>();

		try {
			String query = new String((Files.readAllBytes(pathToResource("queries/get_all_employees.sql"))), StandardCharsets.UTF_8);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			ArrayList<String[]> employees = new ArrayList<String[]>();

			while (resultSet.next()) {
				
				String[] row = new String[3];
				
				int empID = resultSet.getInt("emp_id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				
				row[0] = Integer.toString(empID);
				row[1] = name;
				row[2] = email;
				
				employees.add(row);
			}
				
			String[] colNames = {"Employee ID", "Name", "Email"};
			map.put("employees", employees);
			map.put("colNames", colNames);
			
			resultSet.close();
			statement.close();
			
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static void sql_deleteAccount (int empID) {
		
		try {
			
			String query = new String((Files.readAllBytes(pathToResource("queries/delete_account.sql"))), StandardCharsets.UTF_8);
			PreparedStatement deletionStatement = connection.prepareStatement(query);
			deletionStatement.setInt(1, empID);
			deletionStatement.execute();
			
			System.out.println("Emp ID " + empID + " deleted.");
			
			deletionStatement.close();
		} catch (SQLException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

}