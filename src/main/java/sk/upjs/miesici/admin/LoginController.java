package sk.upjs.miesici.admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;
    

	@FXML
    void loginButtonClick(ActionEvent event) {
    	App.switchScene(new MainController(), "Main.fxml");
    	App.getPrimaryStage().setMinHeight(400);
    	App.getPrimaryStage().setMinWidth(530);
    	App.getPrimaryStage().setTitle("Miesiči");


    }
  
    
  

}