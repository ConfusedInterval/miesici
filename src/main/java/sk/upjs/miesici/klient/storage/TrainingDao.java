package sk.upjs.miesici.klient.storage;

import java.util.List;

import sk.upjs.miesici.admin.storage.Customer;

public interface TrainingDao {

	public static final Customer customer = new Customer();

	void setCustomer(Customer customer);

	List<Training> getAll();

	List<Training> getAllbyClientId(long id);

	Training saveTraining(Training training);
	
	void deleteTrainingById(long id);

}
