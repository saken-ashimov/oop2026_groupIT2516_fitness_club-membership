package entities;

import java.time.LocalDate;

public class MemberFactory {
    public static Member createMember(String name, String email, String phone, int typeId) {
        return new Member.Builder()
                .setFullName(name)
                .setEmail(email)
                .setPhone(phone)
                .setMembershipTypeId(typeId)
                .setJoinDate(LocalDate.now())
                .build();
    }
}