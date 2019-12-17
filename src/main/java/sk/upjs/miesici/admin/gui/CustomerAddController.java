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
    private int errorCheck = 0;

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
    void initialize() {
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException {
        distributePassword();
        if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || addressTextField.getText().equals("") || emailTextField.getText().equals("") ||
                loginTextField.getText().equals("") || passwordTextField.getText().equals("") || expireTextField.getText().equals("") || creditTextField.getText().equals("")) {
            showAlert("Údaje nie sú vyplnené správne!", "Prosím vyplňte všetky údaje.");
        } else {
            if (!emailTextField.getText().contains("@")) {
                showAlert("Údaje nie sú vyplnené správne!", "Neplatný formát emailovej adresy.");
            } else {
                if (!checkIfLoginIsTaken()) {
                    checkForErrors();
                    checkIfCorrectAndSave();
                } else {
                    showAlert("Login s rovnakým názvom už existuje!", "Zvoľte iné prihlasovacie meno!");
                }
            }
        }
    }

    private void checkIfCorrectAndSave() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (errorCheck != 0) {
            switch (errorCheck) {
                case 1:
                    showAlert("Údaje nie sú vyplnené správne!", "Kolonka kredit obsahuje nesprávny formát.");
                    break;
                case 2:
                    showAlert("Údaje nie sú vyplnené správne!", "Expirácia je staršia ako " + LocalDate.now() + ". Zvoľte novší dátum expirácie.");
                    break;
                case 3:
                    showAlert("Údaje nie sú vyplnené správne!", "Zadajte expiráciu vo formáte YYYY-MM-DD.");
                    break;
            }
            errorCheck = 0;
        } else {
            if (passwordTextField.getText().length() >= 6) {
                Customer addCustomer = new Customer();
                addCustomer.setName(nameTextField.getText());
                addCustomer.setSurname(surnameTextField.getText());
                addCustomer.setAddress(addressTextField.getText());
                addCustomer.setEmail(emailTextField.getText());
                addCustomer.setCredit(Double.parseDouble(creditTextField.getText()));
                addCustomer.setMembershipExp(java.sql.Date.valueOf(expireTextField.getText()));
                addCustomer.setLogin(loginTextField.getText());
                String salt = generateRandomSalt();
                addCustomer.setSalt(salt);
                addCustomer.setPassword(hashPassword(passwordTextField.getText(), salt));
                addCustomer.setAdmin(isAdminCheckBox.isSelected());
                this.savedCustomer = customerDao.save(addCustomer);
                saveButton.getScene().getWindow().hide();
            } else {
                showAlert("Heslo nie je dostatočne dlhé!", "Vaše nové heslo nie je dostatočne dlhé. Zvolťe aspoň 6 znakov.");
            }
        }
    }

    private void checkForErrors() {
        try {
            Double.parseDouble(creditTextField.getText());
        } catch (NumberFormatException ignored) {
            errorCheck = 1;
        }
        try {
            Date date = java.sql.Date.valueOf(expireTextField.getText());
            if (!date.toLocalDate().isAfter(LocalDate.now())) {
                errorCheck = 2;
            }
        } catch (IllegalArgumentException ignored) {
            errorCheck = 3;
        }
    }


    private String hashPassword(String password, String salt) throws
            NoSuchAlgorithmException, InvalidKeySpecException {
        // https://adambard.com/blog/3-wrong-ways-to-store-a-password/
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 2000, 512);
        SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = key.generateSecret(spec).getEncoded();
        return String.format("%x", new BigInteger(hashedPassword));
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

    @FXML
    void generatePasswordButtonClick(ActionEvent event) {
        passwordTextField.clear();
        toggleTextField.clear();
        passwordTextField.setText(generateRandomSalt());
        toggleTextField.setText(passwordTextField.getText());
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Neúspešné vykonanie príkazu");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
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

    private String generateRandomSalt() {
        return UUID.randomUUID().toString();
    }

    public Customer getSavedCustomer() {
        return savedCustomer;
    }


}
