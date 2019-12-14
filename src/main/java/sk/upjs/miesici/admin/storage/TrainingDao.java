package sk.upjs.miesici.admin.storage;

import java.util.List;

public interface TrainingDao {
	
	List<Training> getAll();
	List<Training> getAllbyClientId(long id);


}
