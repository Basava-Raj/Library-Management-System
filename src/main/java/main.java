import java.sql.Connection;
import java.sql.SQLException;

import com.lib.db.DatabaseConnection;

public class main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
