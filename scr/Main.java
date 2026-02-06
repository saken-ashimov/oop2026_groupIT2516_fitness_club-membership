import Database.IDB;
import Database.PostgresDB;
import controllers.BookingController;
import controllers.MemberController;
import entities.FitnessClassFactory;
import entities.FitnessClassType;
import entities.Member;
import repositories.BookingRepository;
import repositories.FitnessClassRepository;
import repositories.MemberRepository;
import repositories.MembershipTypeRepository;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IFitnessClassRepository;
import repositories.interfaces.IMemberRepository;
import repositories.interfaces.IMembershipTypeRepository;
import services.AnalyticsService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1 Initialization
        IDB db = PostgresDB.getInstance();
        IMemberRepository memberRepo = new MemberRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);
        IMembershipTypeRepository membershipRepo = new MembershipTypeRepository(db);
        FitnessClassRepository classRepoImpl = new FitnessClassRepository(db);
        IFitnessClassRepository classRepo = classRepoImpl;
        AnalyticsService analyticsService = new AnalyticsService(classRepo, bookingRepo);

        // Controllers
        MemberController memberController = new MemberController(memberRepo, membershipRepo, bookingRepo);
        BookingController bookingController = new BookingController(bookingRepo, classRepo);

        Scanner scanner = new Scanner(System.in);

        // CLI
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add new member");
            System.out.println("2. Chech for member attendance history  (by Email of phone)");
            System.out.println("3. Sign up for a class");
            System.out.println("4. Renew membership");
            System.out.println("5. Print classes analytics report");
            System.out.println("6. Add a sample class (factory)");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Completion of work...");
                break;
            }

            if (input.equals("1")) {
                // Логика сбора данных
                System.out.print("Enter a name: ");
                String name = scanner.nextLine();

                System.out.print("Enter an email: ");
                String email = scanner.nextLine();

                System.out.print("Enter a phone number: ");
                String phone = scanner.nextLine();

                // Print all membership type
                memberController.printMembershipTypes();
                System.out.print("Enter the id of your choice: ");
                int typeId = Integer.parseInt(scanner.nextLine());

                // Give information to controllers
                String result = memberController.register(name, email, phone, typeId);
                System.out.println(result);

            } else if (input.equals("2")) {
                System.out.print("Enter the Members's Email or phone number: ");
                String searchData = scanner.nextLine();


                String info = memberController.getMemberInfo(searchData);
                System.out.println(info);
            } else if (input.equals("3")) {
                System.out.print("Enter the Member's email or phone number: ");
                String searchData = scanner.nextLine();

                Member member = memberController.findMember(searchData);
                if (member == null) {
                    System.out.println("Member is not founded!");
                    continue;
                }

                System.out.println("Hello, " + member.getFullName() + "!");
                bookingController.printAllClasses();

                System.out.print("Enter the id of class: ");
                try {
                    int classId = Integer.parseInt(scanner.nextLine());
                    String result = bookingController.bookClass(member.getId(), classId);
                    System.out.println(result);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Enter an INTEGER ID.");
                }
            } else if (input.equals("5")) {
                analyticsService.printReport    ();
            } else if (input.equals("6")) {
                System.out.println("Choose class type:");
                System.out.println("1. Yoga");
                System.out.println("2. Boxing");
                System.out.println("3. Cardio");
                System.out.print("Enter type: ");
                String typeInput = scanner.nextLine();
                FitnessClassType type = null;
                if ("1".equals(typeInput)) {
                    type = FitnessClassType.YOGA;
                } else if ("2".equals(typeInput)) {
                    type = FitnessClassType.BOXING;
                } else if ("3".equals(typeInput)) {
                    type = FitnessClassType.CARDIO;
                }

                if (type == null) {
                    System.out.println("Unknown class type.");
                    continue;
                }

                System.out.print("Enter instructor name: ");
                String instructorName = scanner.nextLine();
                System.out.print("Enter schedule time (yyyy-MM-dd HH:mm): ");
                String scheduleInput = scanner.nextLine();
                LocalDateTime scheduleTime;
                try {
                    scheduleTime = LocalDateTime.parse(scheduleInput, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                } catch (java.time.format.DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm.");
                    continue;
                }
                boolean success = classRepo.addClass(
                        FitnessClassFactory.createClass(type, instructorName, scheduleTime)
                );
                System.out.println(success ? "Class created." : "Error: could not create class.");
            } else if (input.equals("4")) {
                System.out.print("Enter the Member's email or phone number: ");
                String searchData = scanner.nextLine();

                Member member = memberController.findMember(searchData);
                if (member == null) {
                    System.out.println("Member is not founded!");
                    continue;
                }

                memberController.printMembershipTypes();
                System.out.print("Enter the membership type id for renewal: ");
                try {
                    int typeId = Integer.parseInt(scanner.nextLine());
                    String result = memberController.renewMembership(searchData, typeId);
                    System.out.println(result);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Enter an INTEGER ID.");
                }

            } else {
                System.out.println("Wrong input, try again.");
            }
        }
        scanner.close();
    }
}
