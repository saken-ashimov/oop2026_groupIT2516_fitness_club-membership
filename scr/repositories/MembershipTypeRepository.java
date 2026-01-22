package repositories;

import Database.IDB;
import entities.MembershipType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipTypeRepository {
    private final IDB db;

    public MembershipTypeRepository(IDB db) {
        this.db = db;
    }

    public List<MembershipType> getAllMembershipTypes() { //  List all entities flow
        List<MembershipType> types = new ArrayList<>();
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM membership_types";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                types.add(new MembershipType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("duration_months")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return types;
    }
}
