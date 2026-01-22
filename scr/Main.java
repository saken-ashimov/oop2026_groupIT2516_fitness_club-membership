
import Database.IDB;
import Database.PostgresDB;
import exceptions.ClassFullException;
import repositories.*;
import repositories.interfaces.*;
import services.BookingService;

public class Main {
    public static void main(String[] args) {

        IDB db = new PostgresDB();


        IMemberRepository memberRepo = new MemberRepository(db);
        IFitnessClassRepository classRepo = new FitnessClassRepository(db);
        IBookingRepository bookingRepo = new BookingRepository(db);

        // 3. Инициализация сервиса (связываем логику)
        BookingService bookingService = new BookingService(bookingRepo, classRepo);

        // ДАННЫЕ ДЛЯ ТЕСТА (убедись, что такие ID есть в твоей БД)
        int testMemberId = 4;
        int testClassId = 1;

        System.out.println("--- Старт теста системы бронирования ---");

        try {
            // Пробуем записать человека
                bookingService.bookClass(testMemberId, testClassId);
            System.out.println("✅ Результат: Запись успешно добавлена в БД.");
        } catch (ClassFullException e) {
            // Если сработало наше предупреждение о переполнении
            System.err.println("❌ ОШИБКА: " + e.getMessage());
        } catch (Exception e) {
            // Если случилась какая-то другая ошибка (например, БД отключилась)
            System.err.println("❌ Системная ошибка: " + e.getMessage());
        }

        System.out.println("--- Тест завершен ---");
    }
}
