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
            System.out.println("Not class available.");
            return;
        }
        System.out.println("\nClasses:");
        for (FitnessClass fc : classes) {
            System.out.println(fc.toString());
        }
    }

    public String bookClass(int memberId, int classId) {
        try {
            return bookingService.bookClass(memberId, classId);
        } catch (ClassFullException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getMemberSchedule(int memberId) {
        List<String> classes = bookingRepo.getClassesByMemberId(memberId);

        if (classes == null || classes.isEmpty()) {
            return "User haven't classes.";
        }

        StringBuilder sb = new StringBuilder("Users booking:\n");
        for (String className : classes) {
            sb.append("- ").append(className).append("\n");
        }
        return sb.toString();
    }
}
