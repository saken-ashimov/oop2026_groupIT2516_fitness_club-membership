package repositories.interfaces;

import entities.FitnessClass;
import java.util.List;

public interface IFitnessClassRepository {
    FitnessClass getClassById(int id);
    List<FitnessClass> getAllClasses();
    int getClassCapacity(int id);
}

