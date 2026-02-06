package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PostgresDB implements IDB {
    // A static field
    private static PostgresDB instance;
    private Connection connection;

    private String username = "postgres.dfngjfjxtcauwibocaga";
    private String password = loadPassword();

    //private constructor
    private PostgresDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Public static method
    public static synchronized PostgresDB getInstance() {
        if (instance == null) {
            instance = new PostgresDB();
        }
        return instance;
    }

    private static String loadPassword() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) { //
            props.load(input);
            String value = props.getProperty("DB_PASSWORD");
            if (value == null || value.isBlank()) throw new RuntimeException("DB_PASSWORD missing");
            return value;
        } catch (IOException e) {
            throw new RuntimeException("Config error", e);
        }
    }

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            String connectionUrl = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require"; //
            connection = DriverManager.getConnection(connectionUrl, username, password);
        }
        return connection;
    }
}