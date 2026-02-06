package repositories;

import Database.IDB;
import entities.Member;
import exceptions.DuplicateMemberException;
import repositories.interfaces.IMemberRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository extends PostgresRepository<Member> implements IMemberRepository {
    public MemberRepository(IDB db) {
        super(db);
    }

    @Override
    protected String getTableName() {
        return "members";
    }

    @Override
    protected Member mapResultSetToEntity(ResultSet rs) throws SQLException {
        Date joinDate = rs.getDate("join_date");
        return new Member(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("phone"),
                joinDate == null ? null : joinDate.toLocalDate(),
                rs.getInt("membership_type_id")
        );
    }

    @Override
    public boolean add(Member entity) {
        try {
            return createMember(entity);
        } catch (DuplicateMemberException e) {
            return false;
        }
    }


    @Override
    public boolean createMember(Member member) throws DuplicateMemberException {
        Connection con = null;
        try {
            con = db.getConnection();


            String sql = "INSERT INTO members (full_name, email, phone, membership_type_id) VALUES (?, ?, ?, ?)";

            PreparedStatement st = con.prepareStatement(sql);


            st.setString(1, member.getFullName());
            st.setString(2, member.getEmail());
            st.setString(3, member.getPhone());
            st.setInt(4, member.getMembershipTypeId());

            st.execute();

            return true;
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException && "23505".equals(((SQLException) e).getSQLState())) {
                throw new DuplicateMemberException("Member with these email or phone number already exist.");
            }
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return getAll();
    }

    @Override
    public Member getMemberById(int id) {
        return getById(id);
    }

    @Override
    public Member getMemberByEmail(String email) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM members WHERE email = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return null;
    }
    @Override
    public Member getMemberByPhone(String phone) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM members WHERE phone = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, phone);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    @Override
    public boolean updateMembership(int memberId, int membershipTypeId, java.time.LocalDate joinDate) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "UPDATE members SET membership_type_id = ?, join_date = ? WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, membershipTypeId);
            st.setDate(2, Date.valueOf(joinDate));
            st.setInt(3, memberId);
            return st.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}