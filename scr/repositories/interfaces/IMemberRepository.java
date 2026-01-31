package repositories.interfaces;


import entities.Member;
import java.util.List;
import exceptions.DuplicateMemberException;

public interface IMemberRepository {
    boolean createMember(Member member) throws DuplicateMemberException;
    Member getMemberById(int id);
    List<Member> getAllMembers();
    Member getMemberByEmail(String email);
    Member getMemberByPhone(String phone);
    boolean updateMembership(int memberId, int membershipTypeId, java.time.LocalDate joinDate);
}
