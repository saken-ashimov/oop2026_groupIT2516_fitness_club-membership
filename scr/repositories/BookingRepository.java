package repositories;

import Database.IDB;
import repositories.interfaces.IBookingRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) { this.db = db; }

    @Override
    public boolean bookMemberToClass(int memberId, int classId) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO class_bookings (member_id, class_id) VALUES (?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, memberId);
            st.setInt(2, classId);
            st.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
    }

    @Override
    public boolean isMemberAlreadyBooked(int memberId, int classId) {
        // SQL запрос, который проверяет наличие записи в таблице class_bookings
        // Вернет true, если запись уже есть
        return false; // (реализуй по аналогии с SELECT)
    }

    @Override
    public int getParticipantsCount(int classId) {
        // SQL: SELECT count(*) FROM class_bookings WHERE class_id = ?
        // Нужно, чтобы знать, не переполнен ли класс
        return 0;
    }
    @Override
    public List<String> getClassesByMemberId(int memberId) {
        List<String> classes = new ArrayList<>();
        Connection con = null;
        try {
            con = db.getConnection();
            // Делаем JOIN, чтобы достать названия занятий для этого пользователя
            String sql = "SELECT f.title, f.schedule_time " +
                    "FROM class_bookings cb " +
                    "JOIN fitness_classes f ON cb.class_id = f.id " +
                    "WHERE cb.member_id = ?";

            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, memberId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String classInfo = rs.getString("title") + " (Time: " + rs.getTimestamp("schedule_time") + ")";
                classes.add(classInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return classes;
    }




}
