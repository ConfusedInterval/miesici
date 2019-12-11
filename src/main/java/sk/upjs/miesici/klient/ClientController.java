package sk.upjs.miesici.klient;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javax.security.auth.login.LoginContext;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    
    private EntranceDao entranceDao = DaoFactory.INSTANCE.getEntranceDao();
    private ObservableList<Entrance> entrancesModel;

    private ObservableList<Customer> customersModel;
    private Customer customer;

    

	public void setCustomer(Customer customer) {
		this.customer = customer;
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
		getSettingsAnchorPane().setVisible(false);
		getTrainingAnchorPane().setVisible(false);
		getContactAnchorPane().setVisible(false);
		getHomeAnchorPane().setVisible(false);
		getEntriesAnchorPane().setVisible(false);
	}
	
    @FXML
    void contactClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getContactAnchorPane().getScene().getWindow();
    	stage.setTitle("Kontakt");
    	getContactAnchorPane().setVisible(true);
    }

    
    // bude este upravene
    @FXML
    void entriesClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getEntriesAnchorPane().getScene().getWindow();
    	stage.setTitle("Vstupy");
    	entrancesModel = FXCollections.observableArrayList(entranceDao.getByCustomerId(customer.getId()));
    	entriesTablieView.setItems(FXCollections.observableArrayList(entrancesModel));

        TableColumn<Entrance, LocalDateTime> arrivalCol = new TableColumn<>("Príchod");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        entriesTablieView.getColumns().add(arrivalCol);

        TableColumn<Entrance, LocalDateTime> exitCol = new TableColumn<>("Odchod");
        exitCol.setCellValueFactory(new PropertyValueFactory<>("exit"));
        entriesTablieView.getColumns().add(exitCol);
    	getEntriesAnchorPane().setVisible(true);
    }

    @FXML
    void homeClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getHomeAnchorPane().getScene().getWindow();
    	stage.setTitle("Domov");
    	getHomeAnchorPane().setVisible(true);

    }

    @FXML
    void settingClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getSettingsAnchorPane().getScene().getWindow();
    	stage.setTitle("Nastavenia");
    	getSettingsAnchorPane().setVisible(true);

    }

    @FXML
    void trainingClick(ActionEvent event) {
    	hideAll();
    	Stage stage = (Stage) getTrainingAnchorPane().getScene().getWindow();
    	stage.setTitle("Tréning");
    	getTrainingAnchorPane().setVisible(true);
    }
    
    @FXML
    void initialize() {
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
              modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
              modalStage.show();
              getHomeAnchorPane().getScene().getWindow().hide();
          } catch (IOException e) {
              e.printStackTrace();
          }
    }


    
}
