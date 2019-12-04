package sk.upjs.miesici.admin.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import sk.upjs.miesici.admin.storage.DaoFactory;
import sk.upjs.miesici.admin.storage.Entrance;
import sk.upjs.miesici.admin.storage.EntranceDao;
import java.time.LocalDateTime;

public class EntranceController {

    private EntranceDao entranceDao = DaoFactory.INSTANCE.getEntranceDao();
    private ObservableList<Entrance> entrancesModel;

    @FXML
    private TableView<Entrance> entryTableView;

    @FXML
    private TextField filterNameTextField;

    @FXML
    void initialize() {
        entrancesModel = FXCollections.observableArrayList(entranceDao.getAll());
        entryTableView.setItems(FXCollections.observableArrayList(entrancesModel));

        TableColumn<Entrance, String> klientIdCol = new TableColumn<>("Id");
        klientIdCol.setCellValueFactory(new PropertyValueFactory<>("klient_id"));
        entryTableView.getColumns().add(klientIdCol);

        TableColumn<Entrance, String> nameCol = new TableColumn<>("Meno");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        entryTableView.getColumns().add(nameCol);

        TableColumn<Entrance, String> surnameCol = new TableColumn<>("Priezvisko");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        entryTableView.getColumns().add(surnameCol);

        TableColumn<Entrance, LocalDateTime> arrivalCol = new TableColumn<>("Príchod");
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        entryTableView.getColumns().add(arrivalCol);

        TableColumn<Entrance, LocalDateTime> exitCol = new TableColumn<>("Odchod");
        exitCol.setCellValueFactory(new PropertyValueFactory<>("exit"));
        entryTableView.getColumns().add(exitCol);

        filerTableView();
    }

    private void filerTableView(){
        // https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Entrance> filteredData = new FilteredList<>(entrancesModel, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entrance -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                String surname = StringUtils.stripAccents(entrance.getSurname().toLowerCase());
                if (surname.contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else return entrance.getKlient_id().toString().contains(lowerCaseFilter);
                // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Entrance> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(entryTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        entryTableView.setItems(sortedData);
    }
}
