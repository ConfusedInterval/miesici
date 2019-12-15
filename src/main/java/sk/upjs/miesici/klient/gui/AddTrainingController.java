package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.DaoFactory;
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
	private ComboBox<TypeOfExercise> exerciseComboBox;

	@FXML
	private CheckBox ownExerciseCheckBox;

	private Customer customer;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private TypeOfExerciseDao typeOfExerciseDao = DaoFactory.INSTANCE.getTypeOfExerciseDao();
	private ObservableList<TypeOfExercise> typeOfExerciseModel;

	@FXML
	void AddExerciseClick(ActionEvent event) {
		addTrainingAnchorPane.setVisible(false);
		Stage stage = (Stage) addTrainingAnchorPane.getScene().getWindow();
		stage.setTitle("Pridanie cviku");

		addExerciseAnchorPane.setVisible(true);
	}

	@FXML
	void saveClick(ActionEvent event) {
		TypeOfExercise t = new TypeOfExercise();
		t.setName(exerciseComboBox.getEditor().getText());
		typeOfExerciseDao.save(t);

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
		exerciseComboBox.setEditable(true);

	}
}
