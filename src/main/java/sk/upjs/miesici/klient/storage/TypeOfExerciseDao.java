package sk.upjs.miesici.klient.storage;

import java.util.List;

public interface TypeOfExerciseDao {
	
	
	
	public List<TypeOfExercise> getAll();
	
	public TypeOfExercise save(TypeOfExercise typeOfExercise);
}
