package sk.upjs.miesici.admin;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class CustomerEditController {

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField surnameTextField;

    @FXML
    public TextField addressTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public CheckBox isAdminCheckBox;

    @FXML
    public TextField creditTextField;

    @FXML
    public TextField expireTextField;

    @FXML
    public PasswordField passwordTextField;

    @FXML
    private TextField addCreditTextField;

    @FXML
    void addOneMonthButtonClick(ActionEvent event) {

    }

    @FXML
    void addThreeMonthButtonClick(ActionEvent event) {

    }

    @FXML
    void addYearButtonClick(ActionEvent event) {

    }

    @FXML
    void generatePasswordButtonClick(ActionEvent event) {
        passwordTextField.clear();
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(16, 0, possibleCharacters.length - 1, true, true, possibleCharacters, new SecureRandom());
        passwordTextField.appendText(randomStr);
    }

    @FXML
    void saveCreditButtonClick(ActionEvent event) {
        double c = Double.parseDouble(addCreditTextField.getText());
        creditTextField.setText(String.valueOf(Double.parseDouble(creditTextField.getText()) + c));
        addCreditTextField.setText("");
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}

