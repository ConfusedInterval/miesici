package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.klient.storage.Exercise;
import sk.upjs.miesici.klient.storage.ExerciseDao;
import sk.upjs.miesici.klient.storage.Training;
import sk.upjs.miesici.klient.storage.TypeOfExercise;
import sk.upjs.miesici.klient.storage.TypeOfExerciseDao;

public class AddExerciseController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane addExerciseAnchorPane;

	@FXML
	private TextField ownExerciseTextField;

	@FXML
	private TextField weightTextField;

	@FXML
	private TextField repsTextField;

	@FXML
	private CheckBox ownExerciseCheckBox;

	@FXML
	private ComboBox<TypeOfExercise> exerciseComboBox;

	private Training training;
	private Customer customer;

	public void setTraining(Training training) {
		this.training = training;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private TypeOfExerciseDao typeOfExerciseDao = DaoFactory.INSTANCE.getTypeOfExerciseDao();
	private ExerciseDao exerciseDao = DaoFactory.INSTANCE.getExerciseDao();
	private ObservableList<TypeOfExercise> typeOfExerciseModel;
	private CustomerDao customerDao= DaoFactory.INSTANCE.getCustomerDao();

	@FXML
	void saveExerciseClick(ActionEvent event) {
		TypeOfExercise typeOfExercise = new TypeOfExercise();
		Exercise exercise = new Exercise();
		if (ownExerciseCheckBox.isSelected()) {
			typeOfExercise.setName(ownExerciseTextField.getText());
			typeOfExerciseDao.save(typeOfExercise);
			ownExerciseTextField.setText("");
		} else {
			typeOfExercise = exerciseComboBox.getValue();
		}
		exercise.setTrainingId(training.getId());
		String reps = repsTextField.getText();
		if(reps.equals("")){
			exercise.setReps(0);
		}else {
			exercise.setReps(Integer.parseInt(reps));

		}
		String weight = weightTextField.getText();
		if(weight.equals("")) {
			exercise.setWeight(0);
		} else {
			exercise.setWeight(Double.parseDouble(weight));

		}
		exercise.setTypeOfExerciseId(typeOfExercise.getId());
		exerciseDao.saveExercise(exercise);
		repsTextField.setText("");
		weightTextField.setText("");
	}

	@FXML
	void ownExerciseClick(MouseEvent event) {
		if (ownExerciseCheckBox.isSelected()) {
			exerciseComboBox.setDisable(true);
			ownExerciseTextField.setDisable(false);
		}
		if (!ownExerciseCheckBox.isSelected()) {
			exerciseComboBox.setDisable(false);
			ownExerciseTextField.setDisable(true);
		}
	}

	@FXML
	void initialize() {
		typeOfExerciseDao.setCustomer(customer);
		typeOfExerciseModel = FXCollections.observableArrayList(typeOfExerciseDao.getAllByClientId(customer.getId()));
		exerciseComboBox.getItems().addAll(typeOfExerciseModel);
				if (typeOfExerciseModel.size() != 0) {
					exerciseComboBox.setValue(typeOfExerciseModel.get(0));
				}
				ownExerciseTextField.setDisable(true);

	}
}
