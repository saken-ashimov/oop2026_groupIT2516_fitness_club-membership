package repositories.interfaces;

import java.util.List;

public interface IBookingRepository {
    boolean bookMemberToClass(int memberId, int classId);
    boolean isMemberAlreadyBooked(int memberId, int classId);
    int getParticipantsCount(int classId);
    List<String> getClassesByMemberId(int memberId);

}

