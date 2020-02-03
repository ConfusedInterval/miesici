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
        List<Exercise> all = dao.getAllByTrainingId(74L);
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void saveExercise() {
        List<Exercise> all = dao.getAllByTrainingId(74L);
        Exercise exercise = new Exercise();
        exercise.setReps(15);
        exercise.setWeight(80);
        exercise.setTrainingId(74L);
        exercise.setTypeOfExerciseId(69L);
        dao.saveExercise(exercise);
        assertEquals(all.size() + 1, dao.getAllByTrainingId(74L).size());
    }

    @Test
    void deleteExerciseByTrainingId() {
        Long trainingId = 76L;
        List<Exercise> all = dao.getAllByTrainingId(trainingId);
        if (all.size() != 0 ){
            dao.deleteExerciseByTrainingId(trainingId);
            assertEquals(all.size() - 1, dao.getAllByTrainingId(trainingId).size());
        } else {
            assertEquals(0, dao.getAllByTrainingId(trainingId).size());
        }
    }

    @Test
    void deleteExerciseById() {
        int check = 0;
        Long trainingId = 74L;
        List<Exercise> all = dao.getAllByTrainingId(trainingId);

        for (Exercise exercise: all) {
            if (exercise.getId() == 18L){
                check = 1;
                dao.deleteExerciseById(18L);
                assertEquals(all.size() - 1 , dao.getAllByTrainingId(trainingId).size());
                break;
            }
        }
        if (check != 1){
            assertEquals(all.size(), dao.getAllByTrainingId(trainingId).size());
        }
    }
}