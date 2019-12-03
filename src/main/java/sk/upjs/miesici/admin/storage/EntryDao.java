package sk.upjs.miesici.admin.storage;

import java.util.List;

public interface EntryDao {
    List<Entrance> getAll();

    Entrance save(Entrance entrance);
}
