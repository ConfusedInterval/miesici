package sk.upjs.miesici.admin;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static sk.upjs.miesici.admin.MainController.idOfCustomer;
import static sk.upjs.miesici.admin.MySQLCustomerDao.errorCheck;

public class CustomerEditController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();

    @FXML
    private Button saveButton;

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
        passwordTextField.setText(randomStr);
    }

    @FXML
    void saveCreditButtonClick(ActionEvent event) {
        double c = Double.parseDouble(addCreditTextField.getText());
        creditTextField.setText(String.valueOf(Double.parseDouble(creditTextField.getText()) + c));
        addCreditTextField.setText("");
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) {
        Customer customer = new Customer();
        customer.setId(idOfCustomer);
        customer.setName(nameTextField.getText());
        customer.setSurname(surnameTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setEmail(emailTextField.getText());
        try {
            customer.setCredit(Double.parseDouble(creditTextField.getText()));
        } catch (NumberFormatException ignored) {
            errorCheck = 1;
        }
        try {
            customer.setMembershipExp(java.sql.Date.valueOf(expireTextField.getText()));
        } catch (IllegalArgumentException ignored) {
            errorCheck = 1;
        }
        if (!passwordTextField.getText().equals("")) {
            customer.setPassword(passwordTextField.getText());
        }
        customer.setAdmin(isAdminCheckBox.isSelected());

        if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || addressTextField.getText().equals("") || emailTextField.getText().equals("") || creditTextField.getText().equals("")
                || expireTextField.getText().equals("") || errorCheck == 1) {
            alertPopUp();
        } else {
            customerDao.edit(customer);
            saveButton.getScene().getWindow().hide();
        }
    }


    @FXML
    void initialize() {
    }


    private void alertPopUp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText("Údaje nie sú vyplnené správne.");
        alert.setContentText("Prosím vyplňte všetky údaje!");
        alert.show();
    }
}

