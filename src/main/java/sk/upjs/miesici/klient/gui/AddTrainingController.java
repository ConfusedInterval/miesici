package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.klient.storage.Exercise;
import sk.upjs.miesici.klient.storage.ExerciseDao;
import sk.upjs.miesici.klient.storage.Training;
import sk.upjs.miesici.klient.storage.TrainingDao;
import sk.upjs.miesici.klient.storage.TypeOfExercise;
import sk.upjs.miesici.klient.storage.TypeOfExerciseDao;

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
	private TextArea noteOfTraining;

	@FXML
	private DatePicker datePicker;

	private Customer customer;
	private Training training;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private TrainingDao trainingDao = DaoFactory.INSTANCE.getTrainingDao();

	@FXML
	void saveTrainingClick(ActionEvent event) {
	//	if (nameOfTraining.getText() != null) {
			training = new Training();
			training.setClientId(customer.getId());
			training.setDate(java.sql.Date.valueOf(datePicker.getValue()));
			training.setName(nameOfTraining.getText());
			training.setNote(noteOfTraining.getText());
			trainingDao.saveTraining(training);
//		} else {
//			nameAlert();
//		}
	}
	
	void nameAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Nezadali ste meno!");
		alert.setHeaderText("Nezadali ste meno!");
		alert.setContentText("Nezadali ste meno! Pre uloženie tréningu musite zadať meno.");
		alert.show();
	}

	@FXML
	void initialize() {
		datePicker.setValue(LocalDate.now());
		;
	}
}
