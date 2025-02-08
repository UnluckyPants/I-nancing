package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.CommonObjs;
import application.TransactionA;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchTransactionsController implements Initializable{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TableView<TransactionA> table;
	@FXML private TableColumn<TransactionA, String> nameColumn, descriptionColumn, typeColumn;
	@FXML private TableColumn<TransactionA, Button> buttonColumn;
	@FXML private TableColumn<TransactionA, LocalDate> dateColumn;
	@FXML private TableColumn<TransactionA, Double> amountColumn;
	@FXML private TextField searchBar;
	@FXML private Label EmptyFieldsError;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("textField"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

		table.setRowFactory(click -> {
			TableRow<TransactionA> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 1) {
					TransactionA clickedTransaction = row.getItem();
					openEditPage(clickedTransaction);
				}
			});
			return row;
		});
	}
	
	public void searchTransacsOp() {
		String descript = searchBar.getText().toLowerCase();
		if (descript.equals("")) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			ArrayList<TransactionA> show = new ArrayList<>();
			for (TransactionA t : commonObjs.getTransactions()) {
				if (t.getTextField().toLowerCase().contains(descript)) {
					show.add(t);
					//gets all transactions with description
				}
			}
			nameColumn.setSortType(TableColumn.SortType.DESCENDING);
			table.setItems(FXCollections.observableArrayList(show));
			table.getSortOrder().add(nameColumn);
		}
	}
	
	public void openEditPage(TransactionA transaction) {
		commonObjs.setEditTransac(transaction);
		commonObjs.displayNewWindow("view/EditTransactionPage.fxml");
	}
}
