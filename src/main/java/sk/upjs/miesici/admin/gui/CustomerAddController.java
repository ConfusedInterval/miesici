package sk.upjs.miesici.admin.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomerAddController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private Customer savedCustomer;
    private boolean errorCheck;

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
        Customer addCustomer = new Customer();
        distributePassword();
        addCustomer.setName(nameTextField.getText());
        addCustomer.setSurname(surnameTextField.getText());
        addCustomer.setAddress(addressTextField.getText());
        addCustomer.setEmail(emailTextField.getText());
        addCustomer.setAdmin(isAdminCheckBox.isSelected());
        saveCreditMembershipAndPassword(addCustomer);
        if (!checkIfLoginIsTaken()) {
            addCustomer.setLogin(loginTextField.getText());
            checkIfCorrectAndSave(addCustomer);
        } else {
            showAlert("Login s rovnakým názvom už existuje!", "Zvoľte iné prihlasovacie meno!");
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

    private String generateRandomSalt() {
        return UUID.randomUUID().toString();
    }

    private void checkIfCorrectAndSave(Customer customer) {
        if (customer.getName().equals("") || customer.getSurname().equals("") || customer.getAddress().equals("")  || customer.getEmail().equals("") ||
                customer.getLogin().equals("") || customer.getPassword().equals("") || errorCheck) {
            showAlert("Údaje nie sú vyplnené správne!", "Prosím vyplňte všetky údaje.");
            errorCheck = false;
        } else {
            if (passwordTextField.getText().length() >= 6) {
                this.savedCustomer = customerDao.save(customer);
                saveButton.getScene().getWindow().hide();
            } else {
                showAlert("Heslo nie je dostatočne dlhé!", "Vaše nové heslo nie je dostatočne dlhé. Zvolťe aspoň 6 znakov.");
            }
        }
    }


    private boolean checkIfLoginIsTaken() {
        List<Customer> list = customerDao.getAll();
        for (Customer customer : list) {
            if (loginTextField.getText().equals(customer.getLogin())) {
                return true;
            }
        }
        return false;
    }

    private void distributePassword() {
        if (togglePass.isSelected()) {
            passwordTextField.setText(toggleTextField.getText());
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Neúspešné vykonanie príkazu");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    private void saveCreditMembershipAndPassword(Customer customer) throws InvalidKeySpecException, NoSuchAlgorithmException {
        try {
            customer.setCredit(Double.parseDouble(creditTextField.getText()));
        } catch (NumberFormatException ignored) {
            errorCheck = true;
        }
        try {
            Date date = java.sql.Date.valueOf(expireTextField.getText());
            if (date.toLocalDate().isAfter(LocalDate.now())){
                customer.setMembershipExp(date);
            } else {
                errorCheck = true;
            }
        } catch (IllegalArgumentException ignored) {
            errorCheck = true;
        }
        String salt = generateRandomSalt();
        customer.setPassword(hashPassword(passwordTextField.getText(), salt));
        customer.setSalt(salt);
    }

    public Customer getSavedCustomer() {
        return savedCustomer;
    }


}
