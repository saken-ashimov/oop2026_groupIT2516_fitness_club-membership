package services;

import entities.FitnessClass;
import repositories.interfaces.IFitnessClassRepository;
import repositories.interfaces.IBookingRepository;
import java.util.Map;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final IFitnessClassRepository classRepository;
    private final IBookingRepository bookingRepository;

    public AnalyticsService(IFitnessClassRepository classRepository, IBookingRepository bookingRepository) {
        this.classRepository = classRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<FitnessClass> filterClasses(Predicate<FitnessClass> criteria) {
        return classRepository.getAllClasses()
                .stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }

    public void printReport() {
        System.out.println("\n=== Analytics Report ===");

        System.out.println("Yoga classes:");
        filterClasses(fc -> fc.getTitle().toLowerCase().contains("yoga"))
                .forEach(fc -> System.out.println("- " + fc));

        System.out.println("Two classes with the lowest attendance:");
        Map<Integer, Integer> counts = bookingRepository.getClassParticipantCounts();

        classRepository.getAllClasses()
                .stream()
                // 2. Сортируем, беря данные из Map (операция в памяти, очень быстрая)
                .sorted((a, b) -> Integer.compare(
                        counts.getOrDefault(a.getId(), 0),
                        counts.getOrDefault(b.getId(), 0)
                ))
                .limit(2)
                .forEach(fc -> System.out.println("- " + fc + " | Participants: "
                        + counts.getOrDefault(fc.getId(), 0))); // Тут тоже берем из памяти
    }
}
