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
        // 1. Проверяем вместимость (Capacity)
        int currentPeople = bookingRepo.getParticipantsCount(classId);
        int maxCapacity = classRepo.getClassCapacity(classId); // Нужно добавить этот метод в репозиторий классов

        if (currentPeople >= maxCapacity) {
            throw new ClassFullException("Извините, на эту тренировку мест больше нет!");
        }

        // 2. Проверяем, не записан ли он уже
        if (bookingRepo.isMemberAlreadyBooked(memberId, classId)) {
            return "Вы уже записаны на это занятие.";
        }

        // 3. Если всё ок — записываем
        boolean success = bookingRepo.bookMemberToClass(memberId, classId);
        return success ? "Успешная запись!" : "Ошибка при записи.";
    }
}
