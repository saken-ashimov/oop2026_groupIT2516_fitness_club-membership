package controllers;

import repositories.interfaces.IBookingRepository;
import java.util.List;

public class BookingController {
    private final IBookingRepository bookingRepo;

    public BookingController(IBookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }


    public String getMemberSchedule(int memberId) {

        List<String> classes = bookingRepo.getClassesByMemberId(memberId);

        if (classes == null || classes.isEmpty()) {
            return "No class bookings found for Member ID: " + memberId;
        }

        StringBuilder sb = new StringBuilder("Member Class Schedule:\n");
        for (String className : classes) {
            sb.append("- ").append(className).append("\n");
        }
        return sb.toString();
    }
}
