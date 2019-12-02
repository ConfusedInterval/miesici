package sk.upjs.miesici.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static sk.upjs.miesici.admin.MySQLCustomerDao.errorCheck;

public class CustomerAddController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private CustomerFxModel customerModel;
    private Customer savedCustomer;

    public CustomerAddController() {
        customerModel = new CustomerFxModel();
    }

    public CustomerAddController(Customer customer) {
        customerModel = new CustomerFxModel();
        customerModel.load(customer);
    }

    @FXML
    private Button saveButton;

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
        passwordTextField.setText(generateText());
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) {
        Customer customer = new Customer();
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
        customer.setLogin(loginTextField.getText());
        customer.setPassword(passwordTextField.getText());
        customer.setAdmin(isAdminCheckBox.isSelected());
        customer.setSalt(generateText());
        customerModel.getCustomers().add(customer);
        customerModel.load(customer);
        if (customerModel.getName() == null || customerModel.getSurname() == null || customerModel.getAddress() == null || customerModel.getEmail() == null || customerModel.getMembershipExp() == null ||
                customerModel.getLogin() == null || customerModel.getPassword() == null || errorCheck == 1) {
            alertPopUp();
            errorCheck = 0;
        } else {
            this.savedCustomer = customerDao.save(customerModel.getCustomer());
            saveButton.getScene().getWindow().hide();
        }
    }

    @FXML
    void initialize() {
    }

    public Customer getSavedCustomer() {
        return savedCustomer;
    }

    private String generateText() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
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
