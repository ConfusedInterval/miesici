package sk.upjs.miesici.klient.storage;

import java.util.List;

public interface TrainingDao {

	List<Training> getAll();

	List<Training> getAllbyClientId(Long id);

	Training saveTraining(Training training);

	void deleteTrainingById(Long id);

	void editTraining(Training training);

}
