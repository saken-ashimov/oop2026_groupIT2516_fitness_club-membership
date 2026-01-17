package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private String username = "postgres.dfngjfjxtcauwibocaga"; // твой логин (обычно postgres)
    private String password = "SEHCNI9_CBBreggiN";     // ТВОЙ ПАРОЛЬ

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:5432/postgres?sslmode=require";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(connectionUrl, username, password);

            return connection;
        } catch (Exception e) {
            System.out.println("Failed to connect to database!");
            e.printStackTrace();
            throw e; //
        }
    }
}
