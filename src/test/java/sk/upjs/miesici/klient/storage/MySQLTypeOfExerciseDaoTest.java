package sk.upjs.miesici.klient.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.upjs.miesici.admin.storage.DaoFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLTypeOfExerciseDaoTest {
    TypeOfExerciseDao dao = DaoFactory.INSTANCE.getTypeOfExerciseDao();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<TypeOfExercise> all = dao.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void getAllByClientId() {
        assertEquals(5, dao.getAllByClientId(5L).size());
    }

    @Test
    void save() {
        List<TypeOfExercise> all = dao.getAll();
        TypeOfExercise type = new TypeOfExercise();
        type.setName("hrazda");
        type.setClientId(47L);
        dao.save(type);
        assertEquals(all.size() + 1, dao.getAll().size());
    }
}