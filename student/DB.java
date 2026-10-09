package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String username = "sa";
    private static final String password = "123";
    private static final String database = "Filmovi";
    private static final int port = 1433;
    private static final String server = "localhost";
    private static final String connectionUrl = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";encrypt=false;trustServerCertificate=true";

    private static DB db = null;

    private Connection connection;
    private DB(){
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            System.out.println("SQL Greska: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DB getInstance() {
        if (db == null)
            db = new DB();
        return db;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(connectionUrl, username, password);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Greska: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}
