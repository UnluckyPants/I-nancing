package application.controller;

import application.CommonObjs;
import application.TransactionA;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionReportController {

    public TableView<TransactionA> table;
    public TableColumn<TransactionA,String> accountColumn,descriptionColumn, typeColumn;
    public TableColumn<TransactionA, LocalDate> dateColumn;
    public TableColumn<TransactionA, Double> amountColumn;

    private CommonObjs commonObjs = CommonObjs.getInstance();
    private String firstChoice, secondChoice;

    @FXML private ComboBox<String> firstBox;
    @FXML private ComboBox<String> secondBox;

    private final ObservableList<TransactionA> allTransactions = commonObjs.getObservTransactions();

    //Determines what prompt text to display depending on whether the first box was selected or set by going back
    //True for ComboBox, false for BackButton
    private boolean comboBoxOrBackButton = true;

    @FXML public void initialize(){
        //Adds Options for Account or Type Report
        firstBox.getItems().addAll("Account","Type");
        //Checks to see which option user selects
        firstBox.valueProperty().addListener((observable, oldValue, newValue) -> handleFirstComboBoxSelection(newValue));

        // Initialize the TableView columns
        accountColumn.setCellValueFactory(new PropertyValueFactory<>("account"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("textField"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        dateColumn.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(dateColumn);

        table.sort();

        table.setRowFactory(click -> {
            TableRow<TransactionA> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                	TransactionA clickedTransaction = row.getItem();
                    handleRowClick(clickedTransaction);
                }
            });
            return row;
        });
    }

    //Handles first combo box selection
    @FXML public void handleFirstComboBoxSelection(String selectedOption){
        comboBoxOrBackButton = true;
        // Enable the second combo box and populate it based on the first combo box selection
        secondBox.setDisable(false);
        secondBox.getItems().clear();

        firstChoice = selectedOption;

        //Displays "Select..." in second combo box
        secondBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem (String item,boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Select..."); // Display prompt text manually

                } else {
                    setText(item); // Display the selected item
                }
            }
        });

        List<String> items = null;

        if (selectedOption.equals("Account")) {
            //Collects all distinct account names and adds them to second box
            items = allTransactions.stream().map(TransactionA::getAccount).distinct().toList();
        } else if (selectedOption.equals("Type")) {
            items = allTransactions.stream().map(TransactionA::getType).distinct().toList();
        }

        if (items != null) {
            secondBox.getItems().addAll(items);

            int itemCount = items.size();
            secondBox.setVisibleRowCount(itemCount);
        } else {
            secondBox.setDisable(true);
        }

        secondBox.valueProperty().addListener((observable, oldValue, newValue) -> handleSecondBoxSelection(selectedOption, newValue));
    }

    @FXML public void handleSecondBoxSelection(String selectedOptionOne, String selectedOptionTwo){
        //Filters Transactions based on selected option and displays them in table

        ObservableList<TransactionA> filteredTransactions = null;
        secondChoice = selectedOptionTwo;

        if ("Account".equals(selectedOptionOne)) {
            filteredTransactions = allTransactions.stream()
                    .filter(transaction -> transaction.getAccount().equals(selectedOptionTwo))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            
            accountColumn.setVisible(false);
            typeColumn.setVisible(true);
        } else if ("Type".equals(selectedOptionOne)) {
            filteredTransactions = allTransactions.stream()
                    .filter(transaction -> transaction.getType().equals(selectedOptionTwo))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            // Hide the Type column
            typeColumn.setVisible(false);
            accountColumn.setVisible(true);
        }
        
        table.setItems(filteredTransactions);
    }

    private void handleRowClick(TransactionA clickedTransaction) {
        commonObjs.setReportedTransaction(clickedTransaction);
        commonObjs.setSelections(firstChoice, secondChoice);

        commonObjs.displayNewWindow("view/TransactionReportForm.fxml");
    }

    void restoreTableView(String firstChoice, String secondChoice) {
        comboBoxOrBackButton = false;

        commonObjs.displayNewWindow("view/TransactionReport.fxml");
        firstBox.setValue(firstChoice);

        handleFirstComboBoxSelection(firstChoice);
        handleSecondBoxSelection(firstChoice,secondChoice);

        secondBox.setValue(secondChoice);

        firstBox.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem (String item,boolean empty){
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(firstChoice); // Display prompt text manually
                } else {
                    setText(item); // Display the selected item
                }
            }
        });

    }
}
