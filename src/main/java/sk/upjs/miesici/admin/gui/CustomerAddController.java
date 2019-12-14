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
import java.util.UUID;

import static sk.upjs.miesici.admin.storage.MySQLCustomerDao.errorCheck;

public class CustomerAddController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private Customer savedCustomer;
    private Customer customer = new Customer();

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
        distributePassword();
        customer.setName(nameTextField.getText());
        customer.setSurname(surnameTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setEmail(emailTextField.getText());
        customer.setAdmin(isAdminCheckBox.isSelected());
        saveCreditMembershipAndPassword();
        if (customer.getName() == null || customer.getSurname() == null || customer.getAddress() == null || customer.getEmail() == null || customer.getMembershipExp() == null ||
                customer.getLogin() == null || customer.getPassword() == null || errorCheck == 1) {
            alertPopUp();
            errorCheck = 0;
        } else {
            if (passwordTextField.getText().length() >= 6) {
                this.savedCustomer = customerDao.save(customer);
                saveButton.getScene().getWindow().hide();
            } else {
                alertPasswordPopUp();
            }
        }
    }

    @FXML
    void initialize() {

    }

    @FXML
    void togglePassword(ActionEvent event) {
        if (togglePass.isSelected()) {
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
        return UUID.randomUUID().toString();
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

    private void alertPasswordPopUp() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText("Heslo nie je dostatočne dlhé!");
        alert.setContentText("Vaše nové heslo nie je dostatočne dlhé. Zvolťe aspoň 6 znakov");
        alert.show();
    }


    private void distributePassword() {
        if (togglePass.isSelected()) {
            passwordTextField.setText(toggleTextField.getText());
        }
    }

    private void saveCreditMembershipAndPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
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
        customer.setSalt(salt);
    }

    public Customer getSavedCustomer() {
        return savedCustomer;
    }


}
