import Database.IDB;
import Database.PostgresDB;
import controllers.BookingController;
import controllers.MemberController;
import entities.Member;
import repositories.BookingRepository;
import repositories.FitnessClassRepository;
import repositories.MemberRepository;
import repositories.MembershipTypeRepository;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IFitnessClassRepository;
import repositories.interfaces.IMemberRepository;
import repositories.interfaces.IMembershipTypeRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Инициализация (сборка проекта)
        IDB db = new PostgresDB();
        IMemberRepository memberRepo = new MemberRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);
        IMembershipTypeRepository membershipRepo = new MembershipTypeRepository(db);
        IFitnessClassRepository classRepo = new FitnessClassRepository(db);

        // Контроллеры
        MemberController memberController = new MemberController(memberRepo, membershipRepo, bookingRepo);
        BookingController bookingController = new BookingController(bookingRepo, classRepo);

        Scanner scanner = new Scanner(System.in);

        // ТОТ САМЫЙ ЦИКЛ, чтобы программа не закрывалась
        while (true) {
            System.out.println("\n--- ГЛАВНОЕ МЕНЮ ---");
            System.out.println("1. Зарегистрировать нового участника");
            System.out.println("2. Найти информацию и расписание (по Email, телефону или ID)");
            System.out.println("3. Записаться на занятие");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Завершение работы...");
                break;
            }

            if (input.equals("1")) {
                // Логика сбора данных
                System.out.print("Введите имя: ");
                String name = scanner.nextLine();

                System.out.print("Введите email: ");
                String email = scanner.nextLine();

                System.out.print("Введите телефон: ");
                String phone = scanner.nextLine();

                // Контроллер выводит типы из базы
                memberController.printMembershipTypes();
                System.out.print("Введите ID выбранного абонемента: ");
                int typeId = Integer.parseInt(scanner.nextLine());

                // Отдаем данные контроллеру на обработку
                String result = memberController.register(name, email, phone, typeId);
                System.out.println(result);

            } else if (input.equals("2")) {
                System.out.print("Введите Email, телефон или ID пользователя: ");
                String searchData = scanner.nextLine();

                // Контроллер возвращает строку с данными и списком занятий
                String info = memberController.getMemberInfo(searchData);
                System.out.println(info);
            } else if (input.equals("3")) {
                System.out.print("Введите Email, телефон или ID пользователя для записи: ");
                String searchData = scanner.nextLine();

                Member member = memberController.findMember(searchData);
                if (member == null) {
                    System.out.println("Пользователь не найден!");
                    continue;
                }

                System.out.println("Привет, " + member.getFullName() + "!");
                bookingController.printAllClasses();

                System.out.print("Введите ID выбранной тренировки: ");
                try {
                    int classId = Integer.parseInt(scanner.nextLine());
                    String result = bookingController.bookClass(member.getId(), classId);
                    System.out.println(result);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Введите числовой ID.");
                }

            } else {
                System.out.println("Неверный ввод, попробуйте еще раз.");
            }
        }
        scanner.close();
    }
}
