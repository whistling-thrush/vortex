package application;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseManager {
	
	public static void initialiseDBMS() throws IOException {
		String url = "jdbc:mysql://localhost:3306/desk_book";
		String username = "MyUsername";
		String password = "MyPassword";
		
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			
			Statement statement = connection.createStatement();
			
			//Use correct database
			String correctDatabase = "USE desk_book";
			statement.execute(correctDatabase);
			
			String query = new String(Files.readAllBytes(Paths.get("src/queries/test.sql")), StandardCharsets.UTF_8);
			ResultSet resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				String empName = resultSet.getString("name");
				String email = resultSet.getString("email");
				System.out.println(empName + " " + email);
			}
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
