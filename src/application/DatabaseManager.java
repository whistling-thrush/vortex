package application;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import javax.security.auth.callback.Callback;

public class DatabaseManager {
	
	public static Connection connection;
	
	public static void initialiseDBMS() throws IOException {
		String url = "jdbc:mysql://localhost:3306/desk_book";
		String username = "MyUsername";
		String password = "MyPassword";
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void closeConnection() {
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
			
			resultSet.close();
			statement.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public static void requestSignup(String name, String email, String pass) {
		try {
			String query = new String(Files.readAllBytes(Paths.get("src/queries/signup_validation.sql")), StandardCharsets.UTF_8);
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, pass);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				return;
			} else {
				String addInfo = new String(Files.readAllBytes(Paths.get("src/queries/add_info.sql")), StandardCharsets.UTF_8);
				PreparedStatement addInfoStatement = connection.prepareStatement(addInfo);
				addInfoStatement.setString(1, name);
				addInfoStatement.setString(2, email);
				addInfoStatement.setString(3, pass);
				addInfoStatement.execute();
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
