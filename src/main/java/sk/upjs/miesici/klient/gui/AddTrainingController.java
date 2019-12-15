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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    	typeOfExerciseModel = FXCollections.observableArrayList(typeOfExerciseDao.getAll());
    	exerciseComboBox.getItems().addAll(typeOfExerciseModel);
    	exerciseComboBox.setValue(typeOfExerciseModel.get(0));
    	exerciseComboBox.setEditable(true);
    	
    }
}
