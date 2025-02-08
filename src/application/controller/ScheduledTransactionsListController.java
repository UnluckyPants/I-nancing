package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.CommonObjs;
import application.TransactionA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduledTransactionsListController implements Initializable{

	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TableView<TransactionA> table;
	@FXML private TableColumn<TransactionA, String> namesColumn, typeColumn, accountColumn, frequencyColumn;
	@FXML private TableColumn<TransactionA, Integer> dueDateColumn;
	@FXML private TableColumn<TransactionA, Double> paymentColumn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//set up the separate columns
		namesColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, String>("textField"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, String>("type"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, Integer>("due"));
		accountColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, String>("account"));
		frequencyColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, String>("frequency"));
		paymentColumn.setCellValueFactory(new PropertyValueFactory<TransactionA, Double>("amount"));

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

		//load data and initially sorting by the most recent date
		dueDateColumn.setSortType(TableColumn.SortType.ASCENDING);
		table.setItems(commonObjs.getObservScheduledTransactions());
		table.getSortOrder().add(dueDateColumn);
	}

	public void openEditPage(TransactionA transaction) {
		commonObjs.setEditTransac(transaction);
		commonObjs.displayNewWindow("view/EditScheduledTransactionPage.fxml");
	}
}
