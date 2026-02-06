package services;

import entities.FitnessClass;
import repositories.interfaces.IFitnessClassRepository;
import repositories.interfaces.IBookingRepository;

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
        classRepository.getAllClasses()
                .stream()
                .sorted((a, b) -> Integer.compare(
                        bookingRepository.getParticipantsCount(a.getId()),
                        bookingRepository.getParticipantsCount(b.getId())
                ))
                .limit(2)
                .forEach(fc -> System.out.println("- " + fc + " | Participants: "
                        + bookingRepository.getParticipantsCount(fc.getId())));
    }
}
