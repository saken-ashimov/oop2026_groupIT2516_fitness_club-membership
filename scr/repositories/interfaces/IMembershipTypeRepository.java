package repositories.interfaces;

import entities.MembershipType;
import java.util.List;

public interface IMembershipTypeRepository {
    List<MembershipType> getAllMembershipTypes();
    MembershipType getMembershipTypeById(int id);
}
