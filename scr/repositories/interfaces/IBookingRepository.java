package repositories.interfaces;

public interface IBookingRepository {
    boolean bookMemberToClass(int memberId, int classId);
    boolean isMemberAlreadyBooked(int memberId, int classId);
    int getParticipantsCount(int classId);
}

