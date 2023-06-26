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
			String query = new String(Files.readAllBytes(Paths.get("src/queries/test.sql")), StandardCharsets.UTF_8);
			System.out.println(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
