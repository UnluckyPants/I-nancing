package application.controller;

import application.CommonObjs;
import application.TransactionA;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DueTransactionsController {

    @FXML private TableView<TransactionA> table;
    @FXML private TableColumn<TransactionA, String> nameColumn;
    @FXML private TableColumn<TransactionA, String> accountColumn;
    @FXML private TableColumn<TransactionA, String> typeColumn;
    @FXML private TableColumn<TransactionA, Double> paymentColumn;

    private final CommonObjs commonObjs = CommonObjs.getInstance();

    @FXML //Closes the DueTransaction Window Using UpperRight Button
    public void closeDueTransactions() {
        commonObjs.displayNewWindow("view/MainImage.fxml");
    }

    // Displaying the Due Transactions in the TableView when the window opens up.
    @FXML
    public void initialize() {
        commonObjs.dueTransactions.clear();
        commonObjs.checkDueTransactions();
        // Link TableColumn with ScheduledTransaction fields
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("textField"));
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Add data to the TableView
        table.setItems(commonObjs.dueTransactions);
    }
}
