package Config.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/arabic_dict";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        String query = "INSERT INTO roots (id, root_letters) VALUES (?, ?)";

        try (Connection cnn = DBConnection.getConnection();
             PreparedStatement stmt = cnn.prepareStatement(query)) {

            stmt.setInt(1, 1);
            stmt.setString(2, "AHSAN");

            stmt.executeUpdate();
            System.out.println("Root inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
