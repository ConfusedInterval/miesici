package sk.upjs.miesici.klient.storage;

import java.util.List;

public interface ExerciseDao {
	
	List<Exercise> getAllByTrainingId(Long trainingId);
	Exercise saveExercise(Exercise exercise);
	void deleteExerciseByTrainingId(long id);
	
}
