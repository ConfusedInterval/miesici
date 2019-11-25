package sk.upjs.miesici;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntryController {

	@FXML
	private TableView<Entrance> entryTableView;

	@FXML
    private TextField filterNameTextField;
	
	@FXML
	void initialize() {
		
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
	}
}
