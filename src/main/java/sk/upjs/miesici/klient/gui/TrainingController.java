package sk.upjs.miesici.klient.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ExerciseDao exerciseDao = DaoFactory.INSTANCE.getExerciseDao();
    private ObservableList<Exercise> exercisesModel; 
    

    public void setTraining(Training training) {
		this.training = training;
	}


	@FXML
    void initialize() {
		exercisesModel = FXCollections.observableArrayList(exerciseDao.getAllByTrainingId(training.getId()));
		trainingTableView.setItems(FXCollections.observableArrayList(exercisesModel));
		System.out.println(exerciseDao.getAllByTrainingId(training.getId()));
		
		TableColumn<Exercise, Long> typeOfExercise = new TableColumn<Exercise, Long>("Cvik");
		typeOfExercise.setCellValueFactory(new PropertyValueFactory<>("typeOfExerciseId"));
		trainingTableView.getColumns().add(typeOfExercise);
		
		TableColumn<Exercise, Double> weight = new TableColumn<Exercise, Double>("Váha");
		weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		trainingTableView.getColumns().add(weight);
		
		TableColumn<Exercise, Integer> reps = new TableColumn<Exercise, Integer>("Počet opakovaní");
		reps.setCellValueFactory(new PropertyValueFactory<>("reps"));
		trainingTableView.getColumns().add(reps);

    }
}