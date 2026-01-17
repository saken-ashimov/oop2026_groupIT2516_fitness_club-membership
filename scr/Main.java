package org.example.fitness;

import Database.IDB;
import Database.PostgresDB;
import entities.Member;
import repositories.MemberRepository;
import repositories.interfaces.IMemberRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();

        IMemberRepository repo = new MemberRepository(db);


        System.out.println("Add member....");

        Member newMember = new Member(0, "Test User", "test@mail.ru", "87771234567", null, 1);
        boolean created = repo.createMember(newMember);

        if (created) {
            System.out.println("Cool!");
        } else {
            System.out.println("Error");
        }


        System.out.println("\nAll fat niggers:");
        List<Member> members = repo.getAllMembers();
        for (Member m : members) {
            System.out.println(m);
        }
    }
}
