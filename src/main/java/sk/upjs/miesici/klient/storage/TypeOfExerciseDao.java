package sk.upjs.miesici.klient.storage;

import java.util.List;

import sk.upjs.miesici.admin.storage.Customer;

public interface TypeOfExerciseDao {

    public static final Customer customer = new Customer();

    void setCustomer(Customer customer);

    List<TypeOfExercise> getAll();

    List<TypeOfExercise> getAllByClientId(long clientId);

    List<TypeOfExercise> getAllbyId(long id);

    TypeOfExercise save(TypeOfExercise typeOfExercise);
}
