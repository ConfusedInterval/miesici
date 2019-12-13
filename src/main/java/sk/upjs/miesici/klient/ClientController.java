package sk.upjs.miesici.klient;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.security.auth.login.LoginContext;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import sk.upjs.miesici.admin.gui.MainController;
import sk.upjs.miesici.admin.storage.Customer;
import sk.upjs.miesici.admin.storage.CustomerDao;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.admin.storage.Entrance;
import sk.upjs.miesici.admin.storage.EntranceDao;
import sk.upjs.miesici.login.LoginController;

public class ClientController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane trainingAnchorPane;

	@FXML
	private AnchorPane contactAnchorPane;

	@FXML
	private AnchorPane entriesAnchorPane;

	@FXML
	private AnchorPane settingsAnchorPane;

	@FXML
	private AnchorPane homeAnchorPane;

	@FXML
	private TableView<Customer> clientTable;

	@FXML
	private TableView<Entrance> entriesTablieView;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField adressTextField;

	@FXML
	private PasswordField oldPasswordField;

	@FXML
	private PasswordField newPasswordField;

	@FXML
	private PasswordField repeatPasswordField;

	@FXML
	private ImageView clientImageView;

	@FXML
	private AnchorPane memberShipExtendAnchorPane;

	@FXML
	private RadioButton oneMonthRadioButton;

	@FXML
	private ToggleGroup tgMembershipExtend;

	@FXML
	private RadioButton threeMonthRadioButton;

	@FXML
	private Label membershipExtendedLabel;

	private EntranceDao entranceDao = DaoFactory.INSTANCE.getEntranceDao();
	private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
	private ObservableList<Entrance> entrancesModel;

	private ObservableList<Customer> customersModel;
	private Customer customer;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AnchorPane getMemberShipExtendAnchorPane() {
		return memberShipExtendAnchorPane;
	}

	public AnchorPane getSettingsAnchorPane() {
		return settingsAnchorPane;
	}

	public AnchorPane getTrainingAnchorPane() {
		return trainingAnchorPane;
	}

	public AnchorPane getContactAnchorPane() {
		return contactAnchorPane;
	}

	public AnchorPane getHomeAnchorPane() {
		return homeAnchorPane;
	}

	public AnchorPane getEntriesAnchorPane() {
		return entriesAnchorPane;
	}

	public void hideAll() {
		settingsAnchorPane.setVisible(false);
		trainingAnchorPane.setVisible(false);
		contactAnchorPane.setVisible(false);
		homeAnchorPane.setVisible(false);
		entriesAnchorPane.setVisible(false);
		memberShipExtendAnchorPane.setVisible(false);
	}

	@FXML
	void contactClick(ActionEvent event) {
		hideAll();
		Stage stage = (Stage) contactAnchorPane.getScene().getWindow();
		stage.setTitle("Kontakt");
		contactAnchorPane.setVisible(true);
	}

	// bude este upravene
	@FXML
	void entriesClick(ActionEvent event) {
		hideAll();
		Stage stage = (Stage) entriesAnchorPane.getScene().getWindow();
		stage.setTitle("Vstupy");
		entriesAnchorPane.setVisible(true);
	}

	@FXML
	void homeClick(ActionEvent event) {
		hideAll();
		Stage stage = (Stage) homeAnchorPane.getScene().getWindow();
		stage.setTitle("Domov");
		refreshHomeTable();
		homeAnchorPane.setVisible(true);

	}

	@FXML
	void settingClick(ActionEvent event) {
		hideAll();
		Stage stage = (Stage) settingsAnchorPane.getScene().getWindow();
		stage.setTitle("Nastavenia");
		emailTextField.setText(customer.getEmail());
		adressTextField.setText(customer.getAddress());
		settingsAnchorPane.setVisible(true);

	}

	@FXML
	void trainingClick(ActionEvent event) {
		hideAll();
		Stage stage = (Stage) trainingAnchorPane.getScene().getWindow();
		stage.setTitle("Tréning");
		trainingAnchorPane.setVisible(true);
	}

	@FXML
	void initialize() {
		File file = new File("src/main/resources/sk/upjs/miesici/logo/logo.png");
		Image image = new Image(file.toURI().toString());
		clientImageView.setImage(image);

		customersModel = FXCollections.observableArrayList(customer);
		clientTable.setItems(FXCollections.observableArrayList(customersModel));

		TableColumn<Customer, String> nameCol = new TableColumn<>("Meno");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		clientTable.getColumns().add(nameCol);

		TableColumn<Customer, String> surnameCol = new TableColumn<>("Priezvisko");
		surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
		clientTable.getColumns().add(surnameCol);

		TableColumn<Customer, String> addressCol = new TableColumn<>("Adresa");
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		clientTable.getColumns().add(addressCol);

		TableColumn<Customer, String> emailCol = new TableColumn<>("E-mail");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		clientTable.getColumns().add(emailCol);

		TableColumn<Customer, Double> creditCol = new TableColumn<>("Kredit");
		creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
		clientTable.getColumns().add(creditCol);

		TableColumn<Customer, Date> permanentkaCol = new TableColumn<>("Permanentka");
		permanentkaCol.setCellValueFactory(new PropertyValueFactory<>("membershipExp"));
		clientTable.getColumns().add(permanentkaCol);

		entrancesModel = FXCollections.observableArrayList(entranceDao.getByCustomerId(customer.getId()));
		entriesTablieView.setItems(FXCollections.observableArrayList(entrancesModel));

		TableColumn<Entrance, LocalDateTime> arrivalCol = new TableColumn<>("Príchod");
		arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrival"));
		entriesTablieView.getColumns().add(arrivalCol);

		TableColumn<Entrance, LocalDateTime> exitCol = new TableColumn<>("Odchod");
		exitCol.setCellValueFactory(new PropertyValueFactory<>("exit"));
		entriesTablieView.getColumns().add(exitCol);

	}

	void refreshHomeTable() {
		clientTable.getColumns().get(0).setVisible(false);
		clientTable.getColumns().get(0).setVisible(true);
	}

	@FXML
	void backToHomeAnchorPaneClick(ActionEvent event) {
		refreshHomeTable();
		homeClick(event);
	}

	void notEnoughCreditAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Nedostatok kreditu!");
		alert.setHeaderText("Nedostatok kreditu!");
		alert.setContentText("Pre nabitie kreditu sa zastavte na našej pobočke.");
		alert.show();
	}

	void membershipExtendedInfo(int n) {
		String month;
		if (n == 1) {
			month = "mesiac";
		} else {
			month = "mesiace";
		}
		membershipExtendedLabel.setText("Členstvo úspešne predĺžené" + " o " + n + " " + month);
		membershipExtendedLabel.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
		visiblePause.setOnFinished(e -> membershipExtendedLabel.setVisible(false));
		visiblePause.play();
	}

	@FXML
	void membershipExtendClick(ActionEvent event) {
		double credit = customer.getCredit();
		if (oneMonthRadioButton.isSelected() && credit >= 25) {
			customer.setCredit(credit - 20);
			LocalDate date = customer.getMembershipExp().toLocalDate();
			if (date.isBefore(LocalDate.now())) {
				LocalDate ldt = LocalDate.now().plusMonths(1);
				customer.setMembershipExp(Date.valueOf(ldt));
			} else {
				LocalDate ldt = date.plusMonths(1);
				customer.setMembershipExp(Date.valueOf(ldt));
			}
			membershipExtendedInfo(1);
			customerDao.edit(customer);
			refreshHomeTable();
			return;
		} else {
			if (credit <= 25) {
				notEnoughCreditAlert();
				return;
			}
		}

		if (threeMonthRadioButton.isSelected() && credit >= 70) {
			customer.setCredit(credit - 70);
			LocalDate date = customer.getMembershipExp().toLocalDate();
			if (date.isBefore(LocalDate.now())) {
				LocalDate ldt = LocalDate.now().plusMonths(3);
				customer.setMembershipExp(Date.valueOf(ldt));
			} else {
				LocalDate ldt = date.plusMonths(3);
				customer.setMembershipExp(Date.valueOf(ldt));
			}
			membershipExtendedInfo(3);
			customerDao.edit(customer);
			refreshHomeTable();
			return;
		} else {
			if (credit <= 70) {
				notEnoughCreditAlert();
				return;
			}
		}

	}

	@FXML
	void showMembershipExtendAnchodPaneClick(ActionEvent event) {
		Stage stage = (Stage) homeAnchorPane.getScene().getWindow();
		stage.setTitle("Predĺženie členstva");
		memberShipExtendAnchorPane.setVisible(true);
	}

	private String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		char[] passwordChars = password.toCharArray();
		byte[] saltBytes = salt.getBytes();

		PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, 2000, 512);
		SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hashedPassword = key.generateSecret(spec).getEncoded();
		return String.format("%x", new BigInteger(hashedPassword));
	}

	void incorrectOldPasswordAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Nesprávne heslo!");
		alert.setHeaderText("Nesprávne heslo!");
		alert.setContentText("Nesprávne heslo. Skúste to znova.");
		oldPasswordField.setText("");
		newPasswordField.setText("");
		repeatPasswordField.setText("");
		alert.show();
	}

	void incorrectPasswordsAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Heslá sa nezhodujú!");
		alert.setHeaderText("Heslá sa nezhodujú!");
		alert.setContentText("Vaše heslá sa nezhodujú. Skúste to znova.");
		oldPasswordField.setText("");
		newPasswordField.setText("");
		repeatPasswordField.setText("");
		alert.show();
	}

	void passwordNotLongEnoughAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Heslo nie je dostatočne dlhé!");
		alert.setHeaderText("Heslo nie je dostatočne dlhé!");
		alert.setContentText("Vaše nové heslo nie je dostatočne dlhé. Zvolťe aspoň 6 znakov");
		oldPasswordField.setText("");
		newPasswordField.setText("");
		repeatPasswordField.setText("");
		alert.show();
	}

	void changePassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String oldPassword = hashPassword(oldPasswordField.getText(), customer.getSalt());
		if (oldPassword.equals(customer.getPassword())) {
			String newPassword = newPasswordField.getText();
			if (newPassword.length() < 6) {
				passwordNotLongEnoughAlert();
				return;
			}
			if (newPassword.equals(repeatPasswordField.getText())) {
				String salt = UUID.randomUUID().toString();
				customer.setSalt(salt);
				customer.setPassword(hashPassword(newPassword, salt));
				customerDao.edit(customer);
				oldPasswordField.setText("");
				newPasswordField.setText("");
				repeatPasswordField.setText("");
				return;
			} else {
				incorrectPasswordsAlert();
				return;

			}
		}
		incorrectOldPasswordAlert();
	}

	@FXML
	void saveClick(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {
		customer.setAddress(adressTextField.getText());
		customer.setEmail(emailTextField.getText());
		if (!newPasswordField.getText().equals("")) {
			changePassword();
		}
		customerDao.edit(customer);
	}

	@FXML
	void logOutClick(ActionEvent event) {
		LoginController controller = new LoginController();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sk/upjs/miesici/login/Login.fxml"));
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage modalStage = new Stage();
			modalStage.setScene(scene);
			modalStage.setResizable(false);
			modalStage.getIcons().add(
					new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
			modalStage.setTitle("Prihlásenie");
			modalStage.show();
			homeAnchorPane.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
