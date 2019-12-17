package sk.upjs.miesici.klient.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.klient.storage.Exercise;
import sk.upjs.miesici.klient.storage.ExerciseDao;
import sk.upjs.miesici.klient.storage.Training;

public class TrainingController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableView<Exercise> trainingTableView;

	private Training training;
	private Customer customer;
	private ExerciseDao exerciseDao = DaoFactory.INSTANCE.getExerciseDao();
	private ObservableList<Exercise> exercisesModel;

	public void setTraining(Training training) {
		this.training = training;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private void showAddExerciseController(AddExerciseController controller) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/sk/upjs/miesici/klient/gui/AddExercise.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage modalStage = new Stage();
			modalStage.setScene(scene);
			modalStage.initModality(Modality.APPLICATION_MODAL);
			modalStage.setTitle("Pridať cvik");
			modalStage.setResizable(false);
			modalStage.getIcons().add(
					new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
			modalStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void addExercisesClick(ActionEvent event) {
		AddExerciseController addExerciseController = new AddExerciseController();
		addExerciseController.setTraining(training);
		addExerciseController.setCustomer(customer);
		showAddExerciseController(addExerciseController);
		if (addExerciseController.getSavedExcercise() != null) {
			exercisesModel = FXCollections.observableArrayList(exerciseDao.getAllByTrainingId(training.getId()));
			trainingTableView.setItems(FXCollections.observableArrayList(exercisesModel));
		}
	}

	@FXML
	void deleteExercisesClick(ActionEvent event) {
		Exercise exercise = trainingTableView.getSelectionModel().getSelectedItem();
		if (exercise != null) {
			exerciseDao.deleteExerciseByTypeOfExerciseIdAndTrainingId(exercise.getTypeOfExerciseId(), exercise.getTrainingId());
			exercisesModel = FXCollections.observableArrayList(exerciseDao.getAllByTrainingId(training.getId()));
			trainingTableView.setItems(FXCollections.observableArrayList(exercisesModel));
			
		}
	}


	@FXML
	void initialize() {
		exercisesModel = FXCollections.observableArrayList(exerciseDao.getAllByTrainingId(training.getId()));
		trainingTableView.setItems(FXCollections.observableArrayList(exercisesModel));

		TableColumn<Exercise, String> typeOfExercise = new TableColumn<Exercise, String>("Cvik");
		typeOfExercise.setCellValueFactory(new PropertyValueFactory<>("name"));
		trainingTableView.getColumns().add(typeOfExercise);

		TableColumn<Exercise, Double> weight = new TableColumn<Exercise, Double>("Váha");
		weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		trainingTableView.getColumns().add(weight);

		TableColumn<Exercise, Integer> reps = new TableColumn<Exercise, Integer>("Počet opakovaní");
		reps.setCellValueFactory(new PropertyValueFactory<>("reps"));
		trainingTableView.getColumns().add(reps);
	}
}