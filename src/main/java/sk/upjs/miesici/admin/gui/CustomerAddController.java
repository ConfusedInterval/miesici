package sk.upjs.miesici.admin.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.RandomStringUtils;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import static sk.upjs.miesici.admin.storage.MySQLCustomerDao.errorCheck;

public class CustomerAddController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private CustomerFxModel customerModel;
    private Customer savedCustomer;

    public CustomerAddController() {
        customerModel = new CustomerFxModel();
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
    private TextField toggleTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private CheckBox togglePass;

    @FXML
    void saveCustomerButtonClick(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException {
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
        String salt = generateRandomSalt();
        customer.setPassword(hashPassword(passwordTextField.getText(), salt));
        customer.setAdmin(isAdminCheckBox.isSelected());
        customer.setSalt(salt);
        customerModel.getCustomers().add(customer);
        customerModel.load(customer);
        if (customerModel.getName() == null || customerModel.getSurname() == null || customerModel.getAddress() == null || customerModel.getEmail() == null || customerModel.getMembershipExp() == null ||
                customerModel.getLogin() == null || customerModel.getPassword() == null || errorCheck == 1) {
            alertPopUp();
            errorCheck = 0;
        } else {
            this.savedCustomer = customerDao.save(customerModel.getCustomer());
            if (errorCheck == 0){
                saveButton.getScene().getWindow().hide();
            } else {
                errorCheck = 0;
            }
        }
    }

    @FXML
    void initialize() {

    }

    @FXML
    void togglePassword(ActionEvent event) {
        if (togglePass.isSelected()){
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
        passwordTextField.setText(generateRandomSalt());
        toggleTextField.setText(passwordTextField.getText());
    }

    private String generateRandomSalt() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?").toCharArray();
        String randomStr = RandomStringUtils.random(32, 0, possibleCharacters.length - 1, true, true, possibleCharacters, new SecureRandom());
        return randomStr;
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(
                passwordChars,
                saltBytes,
                2000,
                512
        );
        SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = key.generateSecret(spec).getEncoded();
        return String.format("%x", new BigInteger(hashedPassword));
    }

    private void alertPopUp() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText("Údaje nie sú vyplnené správne.");
        alert.setContentText("Prosím vyplňte všetky údaje!");
        alert.show();
    }


    public Customer getSavedCustomer() {
        return savedCustomer;
    }
}
