package sk.upjs.miesici.admin.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.RandomStringUtils;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;

import java.security.SecureRandom;

import static sk.upjs.miesici.admin.gui.MainController.idOfCustomer;
import static sk.upjs.miesici.admin.storage.MySQLCustomerDao.errorCheck;

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
    private CheckBox passwordEditButton;

    @FXML
    private TextField toggleTextField;

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
            errorCheck = 0;
        } else {
            customerDao.edit(customer);
            saveButton.getScene().getWindow().hide();
        }
    }


    @FXML
    void initialize() {
    }

    @FXML
    void editToggleClick(ActionEvent event) {
        if (passwordEditButton.isSelected()){
            toggleTextField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            toggleTextField.setVisible(true);
        } else {
            passwordTextField.setText(toggleTextField.getText());
            passwordTextField.setVisible(true);
            toggleTextField.setVisible(false);
        }
    }

    @FXML
    void generatePasswordButtonClick(ActionEvent event) {
        passwordTextField.clear();
        toggleTextField.clear();
        passwordTextField.setText(generateText());
        toggleTextField.setText(passwordTextField.getText());
    }

    private String generateText() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(32, 0, possibleCharacters.length - 1, true, true, possibleCharacters, new SecureRandom());
        return randomStr;
    }

    private void alertPopUp() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText("Údaje nie sú vyplnené správne.");
        alert.setContentText("Prosím vyplňte všetky údaje!");
        alert.show();
    }
}

