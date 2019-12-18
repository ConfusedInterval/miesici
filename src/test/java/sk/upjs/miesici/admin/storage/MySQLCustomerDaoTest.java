package sk.upjs.miesici.admin.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySQLCustomerDaoTest {

    CustomerDao dao = DaoFactory.INSTANCE.getCustomerDao();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetAll() {
        List<Customer> all = dao.getAll();
        assertNotNull(all);
        assertTrue(all.size() > 0);
    }

    @Test
    void testSave() {
        List<Customer> all = dao.getAll();
        Customer customer = new Customer();
        customer.setId(7L);
        customer.setName("xx");
        customer.setSurname("xx");
        customer.setAddress("xx");
        customer.setEmail("xx@xx");
        customer.setCredit(150);
        customer.setMembershipExp(Date.valueOf("2020-01-01"));
        customer.setLogin("xx");
        customer.setPassword("xx");
        customer.setSalt("xx");
        customer.setAdmin(true);
        dao.save(customer);
        assertEquals(all.size() + 1, dao.getAll().size());
    }

    @Test
    void testEdit() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("xx");
        customer.setSurname("xx");
        customer.setAddress("xx");
        customer.setEmail("xx@xx");
        customer.setCredit(150);
        customer.setMembershipExp(Date.valueOf("2020-01-01"));
        customer.setPassword("xx");
        customer.setSalt("xx");
        customer.setAdmin(true);
        dao.edit(customer);
        assertEquals("xx", dao.getAll().get(0).getName());
        assertEquals("xx", dao.getAll().get(0).getSurname());
        assertEquals("xx", dao.getAll().get(0).getAddress());
        assertEquals("xx@xx", dao.getAll().get(0).getEmail());
        assertEquals("xx", dao.getAll().get(0).getPassword());
        assertEquals("xx", dao.getAll().get(0).getSalt());
        assertEquals(150, dao.getAll().get(0).getCredit());
        assertTrue(dao.getAll().get(0).isAdmin());
        assertEquals(Date.valueOf("2020-01-01"), dao.getAll().get(0).getMembershipExp());
    }

    @Test
    void testGetBylogin() {
        List<Customer> all = dao.getAll();
        assertEquals(all.get(3).getLogin(), dao.getBylogin("admin").getLogin());
    }
}