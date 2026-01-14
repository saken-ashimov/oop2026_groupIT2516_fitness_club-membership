package entities;

public class Membership {
    private int id;
    private int memberId; // ← связь с Member
    private LocalDate startDate;
    private LocalDate endDate;
}
