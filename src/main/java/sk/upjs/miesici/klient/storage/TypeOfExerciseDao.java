package sk.upjs.miesici.klient.storage;

import java.util.List;

import sk.upjs.miesici.admin.storage.Customer;

public interface TypeOfExerciseDao {
	
	public static final Customer customer = new Customer();
	
	public void setCustomer(Customer customer);
	
	public List<TypeOfExercise> getAll();
	
	public List<TypeOfExercise> getAllByClientId(long clientId);
	
	public TypeOfExercise save(TypeOfExercise typeOfExercise);
}
