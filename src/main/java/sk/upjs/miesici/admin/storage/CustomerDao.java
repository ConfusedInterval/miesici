package sk.upjs.miesici.admin.storage;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAll();

    Customer save(Customer customer);

    void edit(Customer customer);
    
    // boolean isAdmin(String login);
    
    Customer getBylogin(String login);
}
