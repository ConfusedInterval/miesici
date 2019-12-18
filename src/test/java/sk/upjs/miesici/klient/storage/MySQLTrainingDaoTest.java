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

class MySQLTrainingDaoTest {
    TrainingDao dao = DaoFactory.INSTANCE.getTrainingDao();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Training> all = dao.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void saveTraining() {
        List<Training> all = dao.getAll();
        Training training = new Training();
        training.setClientId(1L);
        training.setDate(Date.valueOf("2020-01-01"));
        training.setName("good");
        training.setNote("one");
        training.setDayofTheWeek(3);
        dao.saveTraining(training);
        assertEquals(all.size() + 1, dao.getAll().size());
    }

    @Test
    void getAllbyClientId() {
        assertEquals(1, dao.getAllbyClientId(5L).size());
    }

    @Test
    void deleteTrainingById() {
        List<Training> all = dao.getAll();
        dao.deleteTrainingById(6L);
        assertEquals(all.size() - 1, dao.getAll().size());
    }

    @Test
    void editTraining() {
        Training training = new Training();
        training.setDate(Date.valueOf("2019-12-22"));
        training.setName("Pohodička");
        training.setNote("Jak nič");
        training.setId(9L);
        dao.editTraining(training);
        assertEquals(Date.valueOf("2019-12-22"), dao.getAll().get(1).getDate());
        assertEquals("Pohodička", dao.getAll().get(1).getName());
        assertEquals("Jak nič", dao.getAll().get(1).getNote());
    }
}