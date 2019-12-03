package sk.upjs.miesici.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sk.upjs.miesici.admin.gui.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private CheckBox toggleButton;

    @FXML
    private TextField toggleTextField;
    

	@FXML
    void loginButtonClick(ActionEvent event) {
        MainController controller = new MainController();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sk/upjs/miesici/admin/gui/Main.fxml"));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
            modalStage.setTitle("Hlavné okno");
            modalStage.setMinHeight(196);
            modalStage.setMinWidth(600);
            modalStage.show();
            loginButton.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onToggleChanged(ActionEvent event) {
        if (toggleButton.isSelected()){
            toggleTextField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            toggleTextField.setVisible(true);
        } else {
            passwordTextField.setText(toggleTextField.getText());
            passwordTextField.setVisible(true);
            toggleTextField.setVisible(false);
        }
    }
}