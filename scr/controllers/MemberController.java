package controllers;

import entities.Member;
import entities.MembershipType;
import exceptions.DuplicateMemberException;
import repositories.interfaces.IMemberRepository;
import repositories.interfaces.IMembershipTypeRepository;
import repositories.interfaces.IBookingRepository;
import java.time.LocalDate;
import java.util.List;

public class MemberController {
    private final IMemberRepository memberRepo;
    private final IMembershipTypeRepository membershipRepo;
    private final IBookingRepository bookingRepo;

    public MemberController(IMemberRepository memberRepo,
                            IMembershipTypeRepository membershipRepo,
                            IBookingRepository bookingRepo) {
        this.memberRepo = memberRepo;
        this.membershipRepo = membershipRepo;
        this.bookingRepo = bookingRepo;
    }

    // 1. For given Membership types
    public void printMembershipTypes() {
        List<MembershipType> types = membershipRepo.getAllMembershipTypes();
        if (types == null || types.isEmpty()) {
            System.out.println("Not membership types found.");
            return;
        }
        System.out.println("\nOur Membership Types:");
        for (MembershipType t : types) {
            System.out.println(t.getId() + ". " + t.toString());
        }
    }

    // 2. Registration
    public String register(String name, String email, String phone, int typeId) {
        Member m = new Member(0, name, email, phone, LocalDate.now(), typeId);

        try {
            boolean success = memberRepo.createMember(m);
            return success ? "Add Member!" : "Error: Can't insert to BD.";
        } catch (DuplicateMemberException e) {
            return "Error: " + e.getMessage();
        }
    }

    // 4. Find Member
    public Member findMember(String input) {
        if (input.contains("@")) {
            return memberRepo.getMemberByEmail(input);
        } else if (input.startsWith("+") || input.replaceAll("\\D", "").length() >= 10) {
            return memberRepo.getMemberByPhone(input);
        } else {
            try {
                int id = Integer.parseInt(input);
                return memberRepo.getMemberById(id);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    // 3. User attendance history
    public String getMemberInfo(String input) {
        Member member = findMember(input);
        if (member == null) return "Member not found.";

        // get class booking by JOIN in BookingRepository
        List<String> classes = bookingRepo.getClassesByMemberId(member.getId());

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Info about Member ===\n");
        sb.append("Name: ").append(member.getFullName()).append("\n");
        sb.append("Email: ").append(member.getEmail()).append("\n");
        sb.append("Class booking:\n");

        if (classes.isEmpty()) {
            sb.append("- Not booking.");
        } else {
            for (String c : classes) {
                sb.append("- ").append(c).append("\n");
            }
        }

        return sb.toString();
    }
}