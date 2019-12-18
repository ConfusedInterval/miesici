package sk.upjs.miesici.admin.storage;

import java.util.List;

public interface EntranceDao {

	List<Entrance> getAll();

	List<Entrance> getByCustomerId(long id);

	Entrance saveArrival(Entrance entrance);

	void saveExit(Entrance entrance);
}
