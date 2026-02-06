package repositories;

import Database.IDB;
import entities.FitnessClass;
import repositories.interfaces.IFitnessClassRepository;

import java.sql.*;
import java.util.List;

public class FitnessClassRepository extends PostgresRepository<FitnessClass> implements IFitnessClassRepository {
    public FitnessClassRepository(IDB db) {
        super(db);
    }

    @Override
    protected String getTableName() {
        return "fitness_classes";
    }

    @Override
    protected FitnessClass mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new FitnessClass(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("instructor_name"),
                rs.getTimestamp("schedule_time").toLocalDateTime(),
                rs.getInt("capacity")
        );
    }

    @Override
    public boolean add(FitnessClass entity) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO fitness_classes (title, instructor_name, schedule_time, capacity) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, entity.getTitle());
            st.setString(2, entity.getInstructorName());
            st.setTimestamp(3, Timestamp.valueOf(entity.getScheduleTime()));
            st.setInt(4, entity.getCapacity());
            return st.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public FitnessClass getClassById(int id) {
        return getById(id);
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
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
            }
        }
        return 0; // if class isn't founded
    }

    @Override
    public List<FitnessClass> getAllClasses() {
        return getAll();
    }

    @Override
    public boolean addClass(FitnessClass fitnessClass) {
        return add(fitnessClass);
    }
}
