package Database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    // Метод, который должен вернуть активное соединение
    Connection getConnection() throws SQLException, ClassNotFoundException;
}
