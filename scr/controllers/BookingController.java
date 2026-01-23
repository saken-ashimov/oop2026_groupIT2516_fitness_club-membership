package controllers;

import entities.FitnessClass;
import exceptions.ClassFullException;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IFitnessClassRepository;
import services.BookingService;

import java.util.List;

public class BookingController {
    private final IBookingRepository bookingRepo;
    private final IFitnessClassRepository classRepo;
    private final BookingService bookingService;

    public BookingController(IBookingRepository bookingRepo, IFitnessClassRepository classRepo) {
        this.bookingRepo = bookingRepo;
        this.classRepo = classRepo;
        this.bookingService = new BookingService(bookingRepo, classRepo);
    }

    public void printAllClasses() {
        List<FitnessClass> classes = classRepo.getAllClasses();
        if (classes == null || classes.isEmpty()) {
            System.out.println("Нет доступных тренировок.");
            return;
        }
        System.out.println("\nДоступные тренировки:");
        for (FitnessClass fc : classes) {
            System.out.println(fc.toString());
        }
    }

    public String bookClass(int memberId, int classId) {
        try {
            return bookingService.bookClass(memberId, classId);
        } catch (ClassFullException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    public String getMemberSchedule(int memberId) {
        List<String> classes = bookingRepo.getClassesByMemberId(memberId);

        if (classes == null || classes.isEmpty()) {
            return "У пользователя нет активных записей.";
        }

        StringBuilder sb = new StringBuilder("Расписание участника:\n");
        for (String className : classes) {
            sb.append("- ").append(className).append("\n");
        }
        return sb.toString();
    }
}
