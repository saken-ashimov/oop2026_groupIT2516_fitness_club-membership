package services;

import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IFitnessClassRepository;
import exceptions.ClassFullException;

public class BookingService {
    private final IBookingRepository bookingRepo;
    private final IFitnessClassRepository classRepo;

    public BookingService(IBookingRepository bookingRepo, IFitnessClassRepository classRepo) {
        this.bookingRepo = bookingRepo;
        this.classRepo = classRepo;
    }

    public String bookClass(int memberId, int classId) throws ClassFullException {
        // Check capacity
        int currentPeople = bookingRepo.getParticipantsCount(classId);
        int maxCapacity = classRepo.getClassCapacity(classId); // Нужно добавить этот метод в репозиторий классов

        if (currentPeople >= maxCapacity) {
            throw new ClassFullException("Sorry, this class is full!");
        }

        //  Chek for booking
        if (bookingRepo.isMemberAlreadyBooked(memberId, classId)) {
            return "You already attend this class.";
        }

        // If previous good - make booking
        boolean success = bookingRepo.bookMemberToClass(memberId, classId);
        return success ? "Booking is successful!" : "Recording error.";
    }
}
