package repositories.interfaces;


import entities.Member;
import java.util.List;

public interface IMemberRepository {
    boolean createMember(Member member);
    Member getMemberById(int id);
    List<Member> getAllMembers();
    Member getMemberByEmail(String email);
}
