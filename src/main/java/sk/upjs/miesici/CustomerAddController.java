package sk.upjs.miesici;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class CustomerAddController {

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
    private PasswordField passwordTextField;

    @FXML
    void generatePasswordButtonClick(ActionEvent event) {
    	passwordTextField.clear();
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(16, 0, possibleCharacters.length - 1, true, true, possibleCharacters, new SecureRandom());
        passwordTextField.appendText(randomStr);
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}
