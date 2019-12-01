package sk.upjs.miesici;

import java.io.IOException;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private ObservableList<Customer> customersModel;

    @FXML
    private TextField textField;


    @FXML
    private Button addCustomer;

    @FXML
    private Button editCustomer;

    @FXML
    private Button entryCustomer;

    @FXML
    private TableView<Customer> customerTableView;


    @FXML
    void addMouseEntered(MouseEvent event) {
        Tooltip tt = new Tooltip();
        tt.setText("Pridaj používateľa");
        addCustomer.setTooltip(tt);
    }

    @FXML
    void editMouseEntered(MouseEvent event) {
        Tooltip tt = new Tooltip();
        tt.setText("Edituj používateľa");
        editCustomer.setTooltip(tt);
    }

    @FXML
    void entryMouseEntered(MouseEvent event) {
        Tooltip tt = new Tooltip();
        tt.setText("Vstupy");
        entryCustomer.setTooltip(tt);
    }

    @FXML
    void addCustomerButtonClick(ActionEvent event) {
        CustomerAddController controller = new CustomerAddController();
        showAddCustomerAddWindow(controller, "CustomerAdd.fxml");
        if (controller.getSavedCustomer() != null) {
            customersModel = FXCollections.observableArrayList(customerDao.getAll());
            customerTableView.setItems(FXCollections.observableArrayList(customersModel));
        }
    }

    @FXML
    void editCustomerButtonClick(ActionEvent event) {
        CustomerEditController controller = new CustomerEditController();
        showEditCustomerWindow(controller, "CustomerEdit.fxml");
    }

    @FXML
    void entryCustomerButtonClick(ActionEvent event) {
        EntryController controller = new EntryController();
        showEntryWindow(controller, "Entry.fxml");
    }

    @FXML
    void initialize() {
        customersModel = FXCollections.observableArrayList(customerDao.getAll());
        customerTableView.setItems(FXCollections.observableArrayList(customersModel));

        TableColumn<Customer, String> idCol = new TableColumn<>("id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerTableView.getColumns().add(idCol);

        TableColumn<Customer, String> nameCol = new TableColumn<>("Meno");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerTableView.getColumns().add(nameCol);

        TableColumn<Customer, String> surnameCol = new TableColumn<>("Priezvisko");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        customerTableView.getColumns().add(surnameCol);

        TableColumn<Customer, String> addressCol = new TableColumn<>("Adresa");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerTableView.getColumns().add(addressCol);

        TableColumn<Customer, String> emailCol = new TableColumn<>("E-mail");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerTableView.getColumns().add(emailCol);

        TableColumn<Customer, Double> creditCol = new TableColumn<>("Kredit");
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        customerTableView.getColumns().add(creditCol);

        TableColumn<Customer, Date> permanentkaCol = new TableColumn<>("Permanentka");
        permanentkaCol.setCellValueFactory(new PropertyValueFactory<>("membershipExp"));
        customerTableView.getColumns().add(permanentkaCol);

        TableColumn<Customer, Date> adminCol = new TableColumn<>("Admin");
        adminCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        customerTableView.getColumns().add(adminCol);

        customerTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                onEdit();
            }
        });

    }

    // NEED TO FIX THIS
    public void onEdit() {
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            CustomerEditController controller = new CustomerEditController();
            showEditCustomerWindow(controller, "CustomerEdit.fxml");
            textField.setText(selectedCustomer.getName());
            controller.nameTextField.setText(selectedCustomer.getName());
            controller.surnameTextField.setText(selectedCustomer.getSurname());
            controller.addressTextField.setText(selectedCustomer.getAddress());
            controller.emailTextField.setText(selectedCustomer.getEmail());
            controller.creditTextField.setText(Double.toString(selectedCustomer.getCredit()));
            controller.expireTextField.setText(String.valueOf(selectedCustomer.getMembershipExp()));
            controller.passwordTextField.setText(selectedCustomer.getPassword());
            if (selectedCustomer.isAdmin())
                controller.isAdminCheckBox.setSelected(true);
        }
    }

    private void showAddCustomerAddWindow(CustomerAddController controller, String nameOfFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nameOfFxml));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
            modalStage.setTitle("Pridanie");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEntryWindow(EntryController controller, String nameOfFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nameOfFxml));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setMinHeight(300);
            modalStage.setMinWidth(370);
            modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
            modalStage.setTitle("Vstupy");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showEditCustomerWindow(CustomerEditController controller, String nameOfFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nameOfFxml));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
            modalStage.setTitle("Editácia");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
