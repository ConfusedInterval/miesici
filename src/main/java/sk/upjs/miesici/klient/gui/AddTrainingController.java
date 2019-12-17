package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.klient.storage.Training;
import sk.upjs.miesici.klient.storage.TrainingDao;

public class AddTrainingController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane addTrainingAnchorPane;

	@FXML
	private TextField nameOfTraining;

	@FXML
	private Button saveButton;

	@FXML
	private TextArea noteOfTraining;

	@FXML
	private DatePicker datePicker;

	private Customer customer;
	private Training training;
	private Training savedTraining;
	private boolean edit = false;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Training getSavedTraining() {
		return savedTraining;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	private TrainingDao trainingDao = DaoFactory.INSTANCE.getTrainingDao();

	@FXML
	void saveTrainingClick(ActionEvent event) {
			if (!edit) {
				training = new Training();
			}
			training.setClientId(customer.getId());
			training.setDate(java.sql.Date.valueOf(datePicker.getValue()));
			training.setName(nameOfTraining.getText());
			training.setNote(noteOfTraining.getText());
			this.savedTraining = trainingDao.saveTraining(training);
			saveButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {
		if (training != null) {
			edit = true;
			datePicker.setValue(training.getDate().toLocalDate());
			nameOfTraining.setText(training.getName());
			noteOfTraining.setText(training.getNote());
		} else {
			datePicker.setValue(LocalDate.now());
		}
	}
}
