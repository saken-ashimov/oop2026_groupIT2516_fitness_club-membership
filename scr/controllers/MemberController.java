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

    // 1. Метод для вывода типов абонементов (тот самый PrintMembershipTypes)
    public void printMembershipTypes() {
        List<MembershipType> types = membershipRepo.getAllMembershipTypes();
        if (types == null || types.isEmpty()) {
            System.out.println("Список абонементов пуст.");
            return;
        }
        System.out.println("\nДоступные абонементы:");
        for (MembershipType t : types) {
            // Использует toString() из твоего класса MembershipType
            System.out.println(t.getId() + ". " + t.toString());
        }
    }

    // 2. Метод для регистрации (register)
    public String register(String name, String email, String phone, int typeId) {
        // Создаем объект сущности (Entity)
        Member m = new Member(0, name, email, phone, LocalDate.now(), typeId);

        // Отдаем репозиторию на сохранение
        try {
            boolean success = memberRepo.createMember(m);
            return success ? "Успешно: Участник зарегистрирован!" : "Ошибка: Не удалось сохранить в базу.";
        } catch (DuplicateMemberException e) {
            return "Ошибка: " + e.getMessage();
        }
    }

    // 4. Поиск участника
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

    // 3. Метод для получения инфо и расписания (getMemberInfo)
    public String getMemberInfo(String input) {
        Member member = findMember(input);
        if (member == null) return "Пользователь не найден.";

        // Получаем список занятий через JOIN в BookingRepository
        List<String> classes = bookingRepo.getClassesByMemberId(member.getId());

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Информация о пользователе ===\n");
        sb.append("Имя: ").append(member.getFullName()).append("\n");
        sb.append("Email: ").append(member.getEmail()).append("\n");
        sb.append("Расписание занятий:\n");

        if (classes.isEmpty()) {
            sb.append("- Нет активных записей на тренировки.");
        } else {
            for (String c : classes) {
                sb.append("- ").append(c).append("\n");
            }
        }

        return sb.toString();
    }
}