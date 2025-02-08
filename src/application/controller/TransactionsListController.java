package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import application.CommonObjs;
import application.TransactionA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransactionsListController implements Initializable{

	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TableView<TransactionA> table;
	@FXML private TableColumn<TransactionA, String> namesColumn, typeColumn, descriptionColumn;
	@FXML private TableColumn<TransactionA, LocalDate> dateColumn;
	@FXML private TableColumn<TransactionA, Double> amountColumn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//set up the separate columns
		namesColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("textField"));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		
		//load data and initially sorting by the most recent date
		dateColumn.setSortType(TableColumn.SortType.DESCENDING);
		table.setItems(commonObjs.getObservTransactions());
		table.getSortOrder().add(dateColumn);

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

	public void openEditPage(TransactionA transaction) {
		//commonObjs.setEditTransac(transaction);
		//commonObjs.displayNewWindow("view/EditTransactionPage.fxml");
	}
}
