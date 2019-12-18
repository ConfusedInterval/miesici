package sk.upjs.miesici.klient.storage;

import java.util.List;

import sk.upjs.miesici.admin.storage.Customer;

public interface TypeOfExerciseDao {

	Customer customer = new Customer();

	void setCustomer(Customer customer);

	List<TypeOfExercise> getAll();

	List<TypeOfExercise> getAllByClientId(Long clientId);

	List<TypeOfExercise> getAllbyId(Long id);

	TypeOfExercise save(TypeOfExercise typeOfExercise);
}
