package sk.upjs.miesici;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class CustomerEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private TextField creditTextField;

    @FXML
    private TextField expireTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField creditTextField1;

    @FXML
    void generatePasswordButtonClick(ActionEvent event) {

    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        
    }
}
