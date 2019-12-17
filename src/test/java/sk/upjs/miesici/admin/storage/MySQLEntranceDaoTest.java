package sk.upjs.miesici.admin.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLEntranceDaoTest {

    EntranceDao dao = DaoFactory.INSTANCE.getEntranceDao();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        List<Entrance> all = dao.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void saveArrival() {
        List<Entrance> all = dao.getAll();
        Entrance entrance = new Entrance();
        entrance.setKlient_id(1L);
        entrance.setName("xx");
        entrance.setSurname("xx");
        entrance.setArrival("2019-12-17 16:12:44");
        entrance.setLocker(155);
        dao.saveArrival(entrance);
        assertEquals(all.size() + 1, dao.getAll().size());
    }

    @Test
    void saveExit() {
        Entrance entrance = new Entrance();
        entrance.setKlient_id(1L);
        entrance.setExit("2019-12-17 18:12:44");
        entrance.setTime("02:00:00");
        entrance.setId(93L);
        dao.saveExit(entrance);
        assertEquals(1L, dao.getAll().get(12).getKlient_id());
        assertEquals("02:00:00", dao.getAll().get(12).getTime());
        assertEquals("2019-12-17 18:12:44", dao.getAll().get(12).getExit());
        assertEquals(93L, dao.getAll().get(12).getId());
    }

    @Test
    void getByCustomerId() {
        assertEquals(3, dao.getByCustomerId(51).size());
    }
}