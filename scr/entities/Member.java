package entities;

import java.time.LocalDate;


public class Member {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate joinDate;
    private int membershipTypeId;

    // Construct with parametern
    public Member(int id, String fullName, String email, String phone, LocalDate joinDate, int membershipTypeId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.joinDate = joinDate;
        this.membershipTypeId = membershipTypeId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public int getMembershipTypeId() { return membershipTypeId; }
    public void setMembershipTypeId(int membershipTypeId) { this.membershipTypeId = membershipTypeId; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + fullName + " | Email: " + email;
    }
}
