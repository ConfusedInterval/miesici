package sk.upjs.miesici.klient.storage;

import java.util.List;

public interface ExerciseDao {
	
	List<Exercise> getAllByTrainingId(long trainingId);
	void saveExercise(Exercise exercise);
	
}
