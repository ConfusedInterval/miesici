package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sk.upjs.miesici.admin.storage.Customer;
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
	private Button saveButton;

	@FXML
	private TextField repsTextField;

	@FXML
	private CheckBox ownExerciseCheckBox;

	@FXML
	private ComboBox<TypeOfExercise> exerciseComboBox;

	private Training training;
	private Customer customer;
	private Exercise savedExcercise;

	public void setTraining(Training training) {
		this.training = training;
	}

	public Exercise getSavedExcercise() {
		return savedExcercise;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private TypeOfExerciseDao typeOfExerciseDao = DaoFactory.INSTANCE.getTypeOfExerciseDao();
	private ExerciseDao exerciseDao = DaoFactory.INSTANCE.getExerciseDao();
	private ObservableList<TypeOfExercise> typeOfExerciseModel;

	@FXML
	void saveExerciseClick(ActionEvent event) {
		TypeOfExercise typeOfExercise = new TypeOfExercise();
		Exercise exercise = new Exercise();
		if (ownExerciseCheckBox.isSelected()) {
			typeOfExercise.setName(ownExerciseTextField.getText());
			typeOfExerciseDao.save(typeOfExercise);
		} else {
			typeOfExercise = exerciseComboBox.getValue();
		}
		exercise.setTrainingId(training.getId());
		String reps = repsTextField.getText();
		if (reps.equals("")) {
			exercise.setReps(0);
		} else {
			exercise.setReps(Integer.parseInt(reps));

		}
		String weight = weightTextField.getText();

		if (weight.equals("")) {
			exercise.setWeight(0);
		} else {
			exercise.setWeight(Double.parseDouble(weight));

		}
		exercise.setTypeOfExerciseId(typeOfExercise.getId());
		ownExerciseTextField.setText("");
		repsTextField.setText("");
		weightTextField.setText("");
		this.savedExcercise = exerciseDao.saveExercise(exercise);
		saveButton.getScene().getWindow().hide();
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
		// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		// http://www.tutorialsface.com/2016/12/how-to-make-numeric-decimal-textfield-in-javafx-example-tutorial/
		// force the field to be numeric only
		repsTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					repsTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		weightTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
					weightTextField.setText(oldValue);
				}
			}
		});
		typeOfExerciseDao.setCustomer(customer);
		typeOfExerciseModel = FXCollections.observableArrayList(typeOfExerciseDao.getAllByClientId(customer.getId()));
		exerciseComboBox.getItems().addAll(typeOfExerciseModel);
		if (typeOfExerciseModel.size() != 0) {
			exerciseComboBox.setValue(typeOfExerciseModel.get(0));
		}
		ownExerciseTextField.setDisable(true);

	}
}
