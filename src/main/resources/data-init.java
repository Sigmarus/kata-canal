import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trying to establish database connection...");
        Connection connection = DriverManager.getConnection();
        System.out.println("Starting database initialisation...");
        Statement statement = connection.createStatement();
        //TODO init database, then parse data files and populate data
        connection.close();
    }
}