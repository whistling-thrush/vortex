package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseManager {
	
	public static Connection connection;
	
	public static void initialiseDBMS() throws IOException {
		String url = "jdbc:mysql://localhost:3306/desk_book";
		String username = "MyUsername";
		String password = "MyPassword";
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			
			String query = new String(Files.readAllBytes(Paths.get("src/queries/login_validation.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, "noelanidijkstra@hotmail.ca");
			statement.setString(2, "wbC34Rn39SO5e");
			ResultSet resultSet = statement.executeQuery();
			
			System.out.println(resultSet.next());
			
			resultSet.close();
			statement.close();
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
			ResultSet resultSet = statement.executeQuery(query);
			
			response = resultSet.next();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
}
