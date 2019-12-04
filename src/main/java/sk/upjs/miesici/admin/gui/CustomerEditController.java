package sk.upjs.miesici.admin.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static sk.upjs.miesici.admin.gui.MainController.idOfCustomer;
import static sk.upjs.miesici.admin.storage.MySQLCustomerDao.errorCheck;

public class CustomerEditController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private ObservableList<Customer> customersModel = FXCollections.observableArrayList(customerDao.getAll());

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
    void initialize() {
        Customer customer = getById(idOfCustomer);
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
    void addOneMonthButtonClick(ActionEvent event) throws ParseException {
        LocalDateTime ldt = LocalDateTime.now().plusMonths(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expireTextField.setText(format.format(ldt));
    }

    @FXML
    void addThreeMonthButtonClick(ActionEvent event) {
        LocalDateTime ldt = LocalDateTime.now().plusMonths(3);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expireTextField.setText(format.format(ldt));
    }

    @FXML
    void saveCreditButtonClick(ActionEvent event) {
        double c = Double.parseDouble(addCreditTextField.getText());
        creditTextField.setText(String.valueOf(Double.parseDouble(creditTextField.getText()) + c));
        addCreditTextField.setText("");
    }

    @FXML
    void saveCustomerButtonClick(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Customer customer = getById(idOfCustomer);
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
            customer.setSalt(generateRandomText());
            customer.setPassword(hashPassword(passwordTextField.getText(), customer.getSalt()));
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

    private String generateRandomText() {
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Neplatný formulár");
        alert.setHeaderText("Údaje nie sú vyplnené správne.");
        alert.setContentText("Prosím vyplňte všetky údaje!");
        alert.show();
    }

    private Customer getById(Long id) {
        for (Customer customer : customersModel) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }
}

