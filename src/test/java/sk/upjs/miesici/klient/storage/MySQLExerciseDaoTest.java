package sk.upjs.miesici.klient.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLExerciseDaoTest {

    ExerciseDao dao = DaoFactory.INSTANCE.getExerciseDao();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllByTrainingId() {
        List<Exercise> all = dao.getAllByTrainingId(2L);
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void saveExercise() {
        List<Exercise> all = dao.getAllByTrainingId(2L);
        Exercise exercise = new Exercise();
        exercise.setReps(15);
        exercise.setWeight(80);
        exercise.setTrainingId(2L);
        exercise.setTypeOfExerciseId(2L);
        dao.saveExercise(exercise);
        assertEquals(all.size() + 1, dao.getAllByTrainingId(2L).size());
    }

    @Test
    void deleteExerciseByTrainingId() {
        dao.deleteExerciseByTrainingId(2L);
        assertEquals(0, dao.getAllByTrainingId(2L).size());
    }

    @Test
    void deleteExerciseById() {
        List<Exercise> all = dao.getAllByTrainingId(74L);
        dao.deleteExerciseById(12L);
        assertEquals(2,dao.getAllByTrainingId(74L).size());
    }
}