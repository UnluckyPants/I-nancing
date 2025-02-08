package application.controller;

import application.CommonObjs;
import application.TransactionA;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class TransactionReportFormController {

    public TextField accountName;
    public TextField description;
    public TextField type;
    public TextField date;
    public TextField amount;

    CommonObjs commonObjs = CommonObjs.getInstance();

    @FXML public void initialize() {
    	TransactionA reportedTransaction = commonObjs.getReportedTransaction();

        accountName.setText(reportedTransaction.getAccount());
        type.setText(reportedTransaction.getType());
        description.setText(reportedTransaction.getTextField());
        date.setText(reportedTransaction.getDate().toString());
        amount.setText(String.format("$%.2f", reportedTransaction.getAmount()));
    }

    @FXML public void returnToTable() {
        try {
            // Load the new FXML and retrieve its controller
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/TransactionReport.fxml"));
            AnchorPane newPage = loader.load();

            TransactionReportController controller = loader.getController();

            // Set up the table and combo box values in the new controller
            controller.restoreTableView(commonObjs.getFirstChoice(), commonObjs.getSecondChoice());

            // Replace the current view with the new one
            HBox homePage = commonObjs.getHomePage();
            if (homePage.getChildren().size() > 1) {
                homePage.getChildren().remove(1); // Remove the current child view
            }
            homePage.getChildren().add(newPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
