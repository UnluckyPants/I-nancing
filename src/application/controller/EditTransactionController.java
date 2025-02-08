package application.controller;

import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditTransactionController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	
	@FXML private ChoiceBox<String> Account, TransactionType;
	@FXML private DatePicker TransactionDate;
	@FXML private TextField Description, Amount;
	@FXML private Label EmptyFieldsError, InvalidAmountInputError;
	
	@FXML public void initialize() {
		Account.getItems().addAll(commonObjs.getAccountNames());
		Account.setValue(commonObjs.getEditTransac().getAccount());
		TransactionType.getItems().addAll(commonObjs.getTransactionTypes());
		TransactionType.setValue(commonObjs.getEditTransac().getType());
		TransactionDate.setValue(commonObjs.getEditTransac().getDate());
		Description.setText(commonObjs.getEditTransac().getTextField());
		Amount.setText(Double.toString(commonObjs.getEditTransac().getAmount()));
		//sets all fields to chosen transaction's
	}
	
	@FXML public void saveEditedTransactionOp() {
	
		int lvlOfError = 2;
		double totalmoney = 0;
		EmptyFieldsError.setOpacity(0);
		InvalidAmountInputError.setOpacity(0);
		//Hiding error message
		
		String descript = Description.getText();
		String amount = Amount.getText();
		//Reading values from input fields
		if (descript.equals("")|| (amount.equals(""))) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			lvlOfError--;	
			if (!amount.equals("")) {
				try {
					totalmoney = Double.parseDouble(amount);
					lvlOfError--;
				} catch (NumberFormatException e) {
					lvlOfError++;
					InvalidAmountInputError.setOpacity(0.5);
				}	
			}
		}
		if (lvlOfError < 1) {
			String editedTransaction = Account.getValue() + "~" + TransactionType.getValue() + "~" + TransactionDate.getValue()
										+ "~" + descript + "~" + totalmoney;
			//sets new account info to comma separated line
			commonObjs.setTransactionInformation(editedTransaction.split("~"));
			//saving data into arraylists for checks and displaying
			DAC.rewriteData("resources/data/TransactionsList.txt",commonObjs.getEditTransacIndex(), editedTransaction);
			//write the data into the txt file
	
			commonObjs.displayNewWindow("view/successfullyEditedTransactionPage.fxml");
		}
	}
}
