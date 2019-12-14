package sk.upjs.miesici.admin.gui;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;
import sk.upjs.miesici.admin.storage.*;
import sk.upjs.miesici.login.gui.LoginController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static sk.upjs.miesici.admin.storage.MySQLEntranceDao.typeOfError;

public class MainController {

    private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
    private EntranceDao entranceDao = DaoFactory.INSTANCE.getEntranceDao();
    private ObservableList<Customer> customersModel;

    @FXML
    private TextField filterTextField;

    @FXML
    private TextField lockerTextField;

    @FXML
    private Button addCustomer;

    @FXML
    private Button entryCustomer;

    @FXML
    private Label textEntrance;

    @FXML
    private TableView<Customer> customerTableView;


    @FXML
    void addMouseEntered(MouseEvent event) {
        Tooltip tt = new Tooltip();
        tt.setText("Pridaj používateľa");
        addCustomer.setTooltip(tt);
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
            filterTableView();
        }
    }

    @FXML
    void entryCustomerButtonClick(ActionEvent event) {
        EntranceController controller = new EntranceController();
        showEntryWindow(controller, "Entry.fxml");
    }

    @FXML
    void arrivalButtonClick(ActionEvent event) {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        Entrance entrance = new Entrance();
        entrance.setKlient_id(selectedCustomer.getId());
        entrance.setName(selectedCustomer.getName());
        entrance.setSurname(selectedCustomer.getSurname());
        try {
            entrance.setLocker(Integer.parseInt(lockerTextField.getText()));
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            entrance.setArrival(format.format(ldt));
            entranceDao.saveArrival(entrance);
            if (typeOfError == 1) {
                textEntrance.setText("Vstup bol zaznamenaný!");
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e -> textEntrance.setText(""));
                pauseTransition.play();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Neplatný príchod");
                if (typeOfError == 0){
                    alert.setHeaderText("Zákazík už má zaznamenaný vstup.");
                    alert.setContentText("Prosím zaznačte odchod!");
                } else {
                    alert.setHeaderText("Číslo skrinky je obsadené.");
                    alert.setContentText("Vyberte, prosím, iné číslo!");
                }
                alert.show();
            }
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Neplatný príchod");
            alert.setHeaderText("Zadali ste nesprávny typ dát.");
            alert.setContentText("Prosím zadajte číselný údaj!");
            alert.show();
        }
        lockerTextField.setText("");
    }

    @FXML
    void exitButtonClick(ActionEvent event) throws ParseException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        Entrance entrance = findEntrance(selectedCustomer);
        if (entrance != null) {
            entrance.setKlient_id(selectedCustomer.getId());
            entrance.setName(selectedCustomer.getName());
            entrance.setSurname(selectedCustomer.getSurname());
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            entrance.setExit(format.format(ldt));
            entrance.setTime(getDifference(entrance));
            entranceDao.saveExit(entrance);
            if (typeOfError == 0) {
                textEntrance.setText("Odchod bol zaznamenaný!");
                PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
                pauseTransition.setOnFinished(e -> textEntrance.setText(""));
                pauseTransition.play();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Neplatný odchod");
                alert.setHeaderText("Zákazík nemá zaznamenaný vstup.");
                alert.setContentText("Prosím zaznačte vstup!");
                alert.show();
            }
        }


    }

    @FXML
    void logOutClick(ActionEvent event) {
        LoginController controller = new LoginController();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sk/upjs/miesici/login/gui/Login.fxml"));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
            modalStage.setTitle("Prihlásenie");
            modalStage.show();
            addCustomer.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        filterTableView();
    }

    private void onEdit() {
        if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

            CustomerEditController controller = new CustomerEditController();
            controller.setCustomer(selectedCustomer);
            showEditCustomerWindow(controller, "CustomerEdit.fxml");

            // refresh table
            customersModel = FXCollections.observableArrayList(customerDao.getAll());
            customerTableView.setItems(FXCollections.observableArrayList(customersModel));
            filterTableView();
        }

    }

    private String getDifference(Entrance entrance) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entrance.getArrival());
        String newString = new SimpleDateFormat("HH:mm:ss").format(date);
        Date arrival = new SimpleDateFormat("HH:mm:ss").parse(newString);

        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entrance.getExit());
        String newString2 = new SimpleDateFormat("HH:mm:ss").format(date2);
        Date exit = new SimpleDateFormat("HH:mm:ss").parse(newString2);

        long difference = exit.getTime() - arrival.getTime();
        long diffSeconds = difference / 1000 % 60;
        long diffMinutes = difference / (60 * 1000) % 60;
        long diffHours = difference / (60 * 60 * 1000) % 24;

        return diffHours + ":" + diffMinutes + ":" + diffSeconds;
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

    private void showEntryWindow(EntranceController controller, String nameOfFxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(nameOfFxml));
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage modalStage = new Stage();
            modalStage.setScene(scene);
            modalStage.setMinHeight(800);
            modalStage.setMinWidth(500);
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
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterTableView() {
        // https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Customer> filteredData = new FilteredList<>(customersModel, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                String surname = StringUtils.stripAccents(customer.getSurname().toLowerCase());
                if (surname.contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else return customer.getId().toString().contains(lowerCaseFilter);
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerTableView.setItems(sortedData);
    }


    private Entrance findEntrance(Customer selectedCustomer){
        List<Entrance> list = entranceDao.getAll();
        for (Entrance entrance1 : list) {
            if (entrance1.getKlient_id().equals(selectedCustomer.getId()) && entrance1.getExit() == null && entrance1.getArrival() != null) {
                typeOfError = 0;
                return entrance1;
            }
        }
        return null;
    }
}
