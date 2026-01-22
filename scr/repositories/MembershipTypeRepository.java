package repositories;

import Database.IDB;
import entities.MembershipType;
import repositories.interfaces.IMembershipTypeRepository; // Импортируем интерфейс
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipTypeRepository implements IMembershipTypeRepository {
    private final IDB db;

    public MembershipTypeRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<MembershipType> getAllMembershipTypes() {
        List<MembershipType> types = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM membership_types")) {

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
        }
        return types;
    }

    @Override
    public MembershipType getMembershipTypeById(int id) {
        // Добавим этот метод для полноты картины
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM membership_types WHERE id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new MembershipType(
                        rs.getInt("id"), rs.getString("name"),
                        rs.getDouble("price"), rs.getInt("duration_months")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}