package sk.upjs.miesici.login.gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sk.upjs.miesici.admin.gui.MainController;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.klient.gui.ClientController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController {

	private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
	private Customer assign;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button loginButton;

	@FXML
	private TextField loginTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private CheckBox toggleButton;

	@FXML
	private TextField toggleTextField;

	@FXML
	private ImageView loginImageView;


	@FXML
	void loginButtonClick(ActionEvent event) throws InvalidKeySpecException, NoSuchAlgorithmException {
		try {
			assign = customerDao.getBylogin(loginTextField.getText());
			String passwordHidden = hashPassword(passwordTextField.getText(), assign.getSalt());
			String passwordUnhidden = hashPassword(toggleTextField.getText(), assign.getSalt());
			if ((assign.getPassword().equals(passwordHidden) || assign.getPassword().equals(passwordUnhidden)) && assign.isAdmin()) {
				callMainController();
			}
			if ((assign.getPassword().equals(passwordHidden) || assign.getPassword().equals(passwordUnhidden)) && !assign.isAdmin()) {
				callClientController();
			}
			if (!assign.getPassword().equals(passwordHidden) && !assign.getPassword().equals(passwordUnhidden)) {
				showAlert("Neplatné prihlasovacie údaje!", "Heslo je nesprávne.");
			}
		} catch (NullPointerException e) {
			showAlert("Neplatné prihlasovacie údaje!", "Login je nesprávny.");
		}
	}

	@FXML
	void onToggleChanged(ActionEvent event) {
		if (toggleButton.isSelected()) {
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
	void initialize() {
		File file = new File("src/main/resources/sk/upjs/miesici/logo/logo.png");
		Image image = new Image(file.toURI().toString());
		loginImageView.setImage(image);

	}

	private void callMainController() {
		MainController controller = new MainController();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sk/upjs/miesici/admin/gui/Main.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage modalStage = new Stage();
			modalStage.setScene(scene);
			modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
			modalStage.setTitle("Admin");
			modalStage.setMinHeight(500);
			modalStage.setMinWidth(650);
			modalStage.show();
			loginButton.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void callClientController() {
		ClientController controller = new ClientController();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sk/upjs/miesici/klient/gui/Client.fxml"));
			fxmlLoader.setController(controller);
			controller.setCustomer(assign);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage modalStage = new Stage();
			modalStage.setScene(scene);
			modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
			modalStage.setMinWidth(800);
			modalStage.setMinHeight(500);
			modalStage.setHeight(600);
			modalStage.setWidth(500);
			modalStage.setTitle("Domov");
			controller.hideAll();
			controller.getHomeAnchorPane().setVisible(true);
			modalStage.show();
			loginButton.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		alert.setTitle("Neúspešné vykonanie príkazu");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}

}