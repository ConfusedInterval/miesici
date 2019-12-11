package sk.upjs.miesici.klient;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane trainingAnchorPane;

    @FXML
    private AnchorPane contactAnchorPane;

    @FXML
    private AnchorPane entriesAnchorPane;

    @FXML
    private AnchorPane settingsAnchorPane;

    @FXML
    private AnchorPane homeAnchorPane;


    

	public AnchorPane getSettingsAnchorPane() {
		return settingsAnchorPane;
	}

	public AnchorPane getTrainingAnchorPane() {
		return trainingAnchorPane;
	}

	public AnchorPane getContactAnchorPane() {
		return contactAnchorPane;
	}

	public AnchorPane getHomeAnchorPane() {
		return homeAnchorPane;
	}

	public AnchorPane getEntriesAnchorPane() {
		return entriesAnchorPane;
	}
	
	public void hideAll() {
		getSettingsAnchorPane().setVisible(false);
		getTrainingAnchorPane().setVisible(false);
		getContactAnchorPane().setVisible(false);
		getHomeAnchorPane().setVisible(false);
		getEntriesAnchorPane().setVisible(false);
	}
	
    @FXML
    void contactClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getContactAnchorPane().getScene().getWindow();
    	stage.setTitle("Kontakt");
    	getContactAnchorPane().setVisible(true);
    }

    @FXML
    void entriesClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getEntriesAnchorPane().getScene().getWindow();
    	stage.setTitle("Vstupy");
    	getEntriesAnchorPane().setVisible(true);
    }

    @FXML
    void homeClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getHomeAnchorPane().getScene().getWindow();
    	stage.setTitle("Domov");
    	getHomeAnchorPane().setVisible(true);

    }

    @FXML
    void settingClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getSettingsAnchorPane().getScene().getWindow();
    	stage.setTitle("Nastavenia");
    	getSettingsAnchorPane().setVisible(true);

    }

    @FXML
    void trainingClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getTrainingAnchorPane().getScene().getWindow();
    	stage.setTitle("Tréning");
    	getTrainingAnchorPane().setVisible(true);
    }

    
}
