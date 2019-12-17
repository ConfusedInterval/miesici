package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private AnchorPane addExerciseAnchorPane;

	@FXML
	private AnchorPane addTrainingAnchorPane;

	@FXML
	private TextField ownExerciseTextField;

	@FXML
	private ComboBox<TypeOfExercise> exerciseComboBox;

	@FXML
	private CheckBox ownExerciseCheckBox;
	
    @FXML
    private TextField nameOfTraining;

    @FXML
    private TextArea noteOfTraining;

    @FXML
    private TextField dateOfTraining;
   
    @FXML
    private TextField weightTextField;

    @FXML
    private TextField repsTextField;

	private Customer customer;
	private Training training;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private TypeOfExerciseDao typeOfExerciseDao = DaoFactory.INSTANCE.getTypeOfExerciseDao();
	private ObservableList<TypeOfExercise> typeOfExerciseModel;
	private TrainingDao trainingDao = DaoFactory.INSTANCE.getTrainingDao();
	private ExerciseDao exerciseDao = DaoFactory.INSTANCE.getExerciseDao();

	@FXML
	void AddExerciseClick(ActionEvent event) {
		addTrainingAnchorPane.setVisible(false);
		Stage stage = (Stage) addTrainingAnchorPane.getScene().getWindow();
		stage.setTitle("Pridanie cviku");
		addExerciseAnchorPane.setVisible(true);
	}

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
			System.out.println(typeOfExercise);
		}
		exercise.setTrainingId(training.getId());
		exercise.setReps(Integer.parseInt(repsTextField.getText()));
		exercise.setWeight(Double.parseDouble(weightTextField.getText()));
		exercise.setTypeOfExerciseId(typeOfExercise.getId());
		exerciseDao.saveExercise(exercise);
		repsTextField.setText("");
		weightTextField.setText("");
	}
	
	

    @FXML
    void saveTrainingClick(ActionEvent event) {
    	training = new Training();
    	training.setClientId(customer.getId());
    	training.setDate(dateOfTraining.getText());
    	training.setName(nameOfTraining.getText());
    	training.setNote(noteOfTraining.getText());
    	trainingDao.saveTraining(training);
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
		addExerciseAnchorPane.setVisible(false);
		typeOfExerciseDao.setCustomer(customer);
		typeOfExerciseModel = FXCollections.observableArrayList(typeOfExerciseDao.getAllByClientId(customer.getId()));
		exerciseComboBox.getItems().addAll(typeOfExerciseModel);
		if (typeOfExerciseModel.size() != 0) {
			exerciseComboBox.setValue(typeOfExerciseModel.get(0));
		}
		ownExerciseTextField.setDisable(true);
	}
}
