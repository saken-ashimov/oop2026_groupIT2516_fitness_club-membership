package repositories;

import Database.IDB;
import repositories.interfaces.IGenericRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresRepository<T> implements IGenericRepository<T> {
    protected final IDB db;

    public PostgresRepository(IDB db) {
        this.db = db;
    }

    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        Connection con = null;
        try {
            con = db.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + getTableName());
            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return list;
    }

    @Override
    public T getById(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM " + getTableName() + " WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return null;
    }

    protected void close(Connection con) {
        try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}
