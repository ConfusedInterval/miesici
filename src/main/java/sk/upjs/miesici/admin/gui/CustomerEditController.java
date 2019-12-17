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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;


public class CustomerEditController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private Customer customer;
    private int errorCheck = 0;

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
    private PasswordField passwordTextField;

    @FXML
    private TextField addCreditTextField;

    @FXML
    private CheckBox passwordEditButton;

    @FXML
    private TextField toggleTextField;

    @FXML
    void saveCreditButtonClick(ActionEvent event) {
        try {
            double addedValue = Double.parseDouble(addCreditTextField.getText());
            creditTextField.setText(String.valueOf(Double.parseDouble(creditTextField.getText()) + addedValue));
            addCreditTextField.setText("");
        } catch (NumberFormatException e) {
            showAlert("Údaje nie sú vyplnené správne!", "Zadajte číselnú hodnotu.");
        }
    }

    @FXML
    void initialize() {
        nameTextField.setText(customer.getName());
        surnameTextField.setText(customer.getSurname());
        addressTextField.setText(customer.getAddress());
        emailTextField.setText(customer.getEmail());
        creditTextField.setText(Double.toString(customer.getCredit()));
        expireTextField.setText(String.valueOf(customer.getMembershipExp()));
        if (customer.isAdmin()) {
            isAdminCheckBox.setSelected(true);
        }
    }

    @FXML
    void addOneMonthButtonClick(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(expireTextField.getText(), formatter);
            if (date.isBefore(LocalDate.now())) {
                LocalDate ldt = LocalDate.now().plusMonths(1);
                expireTextField.setText(formatter.format(ldt));
            } else {
                LocalDate ldt = date.plusMonths(1);
                expireTextField.setText(formatter.format(ldt));
            }
        } catch (DateTimeParseException e) {
            LocalDate ldt = LocalDate.now().plusMonths(1);
            expireTextField.setText(formatter.format(ldt));
        }
    }

    @FXML
    void addThreeMonthButtonClick(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(expireTextField.getText(), formatter);
            if (date.isBefore(LocalDate.now())) {
                LocalDate ldt = LocalDate.now().plusMonths(3);
                expireTextField.setText(formatter.format(ldt));
            } else {
                LocalDate ldt = date.plusMonths(3);
                expireTextField.setText(formatter.format(ldt));
            }
        } catch (DateTimeParseException e) {
            LocalDate ldt = LocalDate.now().plusMonths(3);
            expireTextField.setText(formatter.format(ldt));
        }
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException {
        distributePassword();
        if (nameTextField.getText().equals("") || surnameTextField.getText().equals("") || addressTextField.getText().equals("") || emailTextField.getText().equals("")) {
            showAlert("Údaje nie sú vyplnené správne!", "Prosím vyplňte všetky údaje.");
        } else {
            if (!emailTextField.getText().contains("@")) {
                showAlert("Údaje nie sú vyplnené správne!", "Neplatný formát emailovej adresy.");
            } else {
                checkForErrors();
                checkAndSave();
            }
        }
    }

    private void checkForErrors(){
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

    private void checkAndSave() throws InvalidKeySpecException, NoSuchAlgorithmException {
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
            if (passwordTextField.getText().length() >= 6 || passwordTextField.getText().length() == 0) {
                customer.setName(nameTextField.getText());
                customer.setSurname(surnameTextField.getText());
                customer.setAddress(addressTextField.getText());
                customer.setEmail(emailTextField.getText());
                customer.setAdmin(isAdminCheckBox.isSelected());
                if (passwordTextField.getText().length() >= 6) {
                    String salt = generateRandomText();
                    customer.setSalt(salt);
                    customer.setPassword(hashPassword(passwordTextField.getText(), salt));
                }
                customerDao.edit(customer);
                saveButton.getScene().getWindow().hide();
            } else {
                showAlert("Heslo nie je dostatočne dlhé!", "Vaše nové heslo nie je dostatočne dlhé. Zvolťe aspoň 6 znakov");
            }
        }
    }

    @FXML
    void editToggleClick(ActionEvent event) {
        if (passwordEditButton.isSelected()) {
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
        passwordTextField.setText(generateRandomText());
        toggleTextField.setText(passwordTextField.getText());
    }

    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // https://adambard.com/blog/3-wrong-ways-to-store-a-password/
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 2000, 512);
        SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hashedPassword = key.generateSecret(spec).getEncoded();
        return String.format("%x", new BigInteger(hashedPassword));
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    private void distributePassword() {
        if (passwordEditButton.isSelected()) {
            passwordTextField.setText(toggleTextField.getText());
        }
    }

    private String generateRandomText() {
        return UUID.randomUUID().toString();
    }
}

