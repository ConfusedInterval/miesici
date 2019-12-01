package sk.upjs.miesici;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAll();

    Customer save(Customer customer);
}
