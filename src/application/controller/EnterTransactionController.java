package application.controller;

import java.time.LocalDate;
import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterTransactionController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	
	@FXML private ChoiceBox<String> Account;
	@FXML private ChoiceBox<String> TransactionType;
	@FXML private DatePicker TransactionDate;
	@FXML private TextField Description;
	@FXML private TextField Payment;
	@FXML private TextField Deposit;
	@FXML private Label EmptyFieldsError;
	@FXML private Label InvalidPayOrDepInputError;
	
	@FXML public void initialize() {
		Account.getItems().addAll(commonObjs.getAccountNames());
		Account.setValue(commonObjs.getAccountNames().get(0));
		TransactionType.getItems().addAll(commonObjs.getTransactionTypes());
		TransactionType.setValue(commonObjs.getTransactionTypes().get(0));
		TransactionDate.setValue(LocalDate.now());
		//sets default date to current date
	}
	
	@FXML public void saveEnteredTransactionOp() {
	
		int lvlOfError = 2;
		double totalmoney = 0;
		EmptyFieldsError.setOpacity(0);
		InvalidPayOrDepInputError.setOpacity(0);
		//Hiding error message
		
		String descript = Description.getText();
		String pay = Payment.getText();
		String dep = Deposit.getText();
		//Reading values from input fields
		if (descript.equals("")|| (pay.equals("") && dep.equals(""))) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			lvlOfError--;	
			if (!dep.equals("")) {
				try {
					totalmoney += Double.parseDouble(dep);
					lvlOfError--;
				} catch (NumberFormatException e) {
					lvlOfError++;
					InvalidPayOrDepInputError.setOpacity(0.5);
				}	
			}
			if (!pay.equals("")) {
				try {
					totalmoney -= Double.parseDouble(pay);
					lvlOfError--;
				} catch (NumberFormatException e) {
					lvlOfError++;
					InvalidPayOrDepInputError.setOpacity(0.5);
				}
			}
		}
		if (lvlOfError < 1) {
			String enteredTransaction = Account.getValue() + "~" + TransactionType.getValue() + "~" 
										+ TransactionDate.getValue() + "~" + descript + "~" + totalmoney;
				
			//sets new account info to comma separated line
			commonObjs.addTransactionInformation(enteredTransaction.split("~"));
			//saving data into arraylists for checks and displaying
			DAC.WriteData("resources/data/TransactionsList.txt", enteredTransaction);
			//write the data into the txt file
			commonObjs.displayNewWindow("view/successfullyAddedTransactionPage.fxml");
		}
	}
}