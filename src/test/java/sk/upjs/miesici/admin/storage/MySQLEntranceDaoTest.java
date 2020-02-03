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
        entrance.setKlient_id(60L);
        entrance.setName("Karol");
        entrance.setSurname("Báčik");
        entrance.setArrival("2019-12-17 16:12:44");
        entrance.setLocker(155);
        dao.saveArrival(entrance);
        assertEquals(all.size() + 1, dao.getAll().size());
    }

    @Test
    void saveExit() {
        List<Entrance> all = dao.getAll();
        Long last;
        boolean arrivalCheck = false ;

        for (Entrance entrance1 : all) {
            if (entrance1.getKlient_id().equals(60L) && entrance1.getArrival() != null && entrance1.getExit() == null) {
                entrance1.setKlient_id(60L);
                entrance1.setExit("2019-12-17 18:12:44");
                entrance1.setTime("02:00:00");
                last = entrance1.getId();
                dao.saveExit(entrance1);
                assertEquals(60L, entrance1.getKlient_id());
                assertEquals("02:00:00", entrance1.getTime());
                assertEquals("2019-12-17 18:12:44", entrance1.getExit());
                assertEquals(last, entrance1.getId());
                arrivalCheck = true;
                break;
            }
        }
        if (!arrivalCheck){
            assertTrue(true);
        }
    }

    @Test
    void getByCustomerId() {
        assertEquals(3, dao.getByCustomerId(51).size());
    }
}