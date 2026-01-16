package entities;

import java.time.LocalDateTime;


public class ClassBooking {
    private int id;
    private int memberId;
    private int classId;
    private LocalDateTime bookingDate;

    // Construct with parameters
    public ClassBooking(int id, int memberId, int classId, LocalDateTime bookingDate) {
        this.id = id;
        this.memberId = memberId;
        this.classId = classId;
        this.bookingDate = bookingDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public LocalDateTime getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

}
