package repositories;

import Database.IDB;
import entities.FitnessClass;
import repositories.interfaces.IFitnessClassRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FitnessClassRepository implements IFitnessClassRepository {
    private final IDB db;

    public FitnessClassRepository(IDB db) {
        this.db = db;
    }

    @Override
    public FitnessClass getClassById(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, title, instructor_name, schedule_time, capacity FROM fitness_classes WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new FitnessClass(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("instructor_name"),
                        rs.getTimestamp("schedule_time").toLocalDateTime(),
                        rs.getInt("capacity")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return null;
    }

    @Override
    public int getClassCapacity(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT capacity FROM fitness_classes WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("capacity");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return 0; // if class isn't founded
    }

    @Override
    public List<FitnessClass> getAllClasses() {
        List<FitnessClass> classes = new ArrayList<>();
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM fitness_classes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                classes.add(new FitnessClass(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("instructor_name"),
                        rs.getTimestamp("schedule_time").toLocalDateTime(),
                        rs.getInt("capacity")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return classes;
    }
}
