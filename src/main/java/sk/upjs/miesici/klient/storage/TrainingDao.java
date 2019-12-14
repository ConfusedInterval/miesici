package sk.upjs.miesici.klient.storage;

import java.util.List;

public interface TrainingDao {
	
	List<Training> getAll();
	List<Training> getAllbyClientId(long id);


}
