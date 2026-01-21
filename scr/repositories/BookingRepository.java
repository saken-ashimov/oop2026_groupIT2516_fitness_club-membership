package repositories;

import Database.IDB;
import repositories.interfaces.IBookingRepository;
import java.sql.*;

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

        return false;
    }

    @Override
    public int getParticipantsCount(int classId) {

        return 0;
    }
}
