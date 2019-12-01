package sk.upjs.miesici;

import java.util.List;

public interface EntryDao {
    List<Entrance> getAll();

    Entrance save(Entrance entrance);
}
