package application.controller;

import java.util.ArrayList;
import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditScheduledTransactionController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	
	@FXML private TextField ScheduledTransactionName, Payment, DueDate;
	@FXML private ChoiceBox<String> Account, TransactionType, Frequency;
	@FXML private Label PaymentError, EmptyFieldsError, DueDateError, TransacNameError;
	
	@FXML public void initialize() {
		Account.getItems().addAll(commonObjs.getAccountNames());
		Account.setValue(commonObjs.getEditTransac().getAccount());
		TransactionType.getItems().addAll(commonObjs.getTransactionTypes());
		TransactionType.setValue(commonObjs.getEditTransac().getType());
		Frequency.getItems().add("Monthly");
		Frequency.setValue("Monthly");
		ScheduledTransactionName.setText(commonObjs.getEditTransac().getTextField());
		DueDate.setText(Integer.toString(commonObjs.getEditTransac().getDue()));
		Payment.setText(Double.toString(commonObjs.getEditTransac().getAmount()));
		
		//monthly is hard coded as per instructions
	}
	
	@FXML public void saveEnteredScheduledTransactionOp() {
	
		int lvlOfError = 4;
		EmptyFieldsError.setOpacity(0);
		PaymentError.setOpacity(0);
		DueDateError.setOpacity(0);
		TransacNameError.setOpacity(0);
		//Hiding error message
			
		String name = ScheduledTransactionName.getText();
		String pay = Payment.getText();
		double payment = 0;
		String date = DueDate.getText();
		int due = 0;
		//Reading values from input fields
		
		if (name.equals("")|| pay.equals("") || date.equals("")) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			lvlOfError--;
			ArrayList<String> names = commonObjs.getScheduledTransactionNames();
			boolean goodToGo = true;
			if (names.indexOf(name) != commonObjs.getEditTransacIndex() && names.indexOf(name) != -1) {
				goodToGo = false;
				TransacNameError.setOpacity(0.5);
				lvlOfError++;
			}
			if (goodToGo == true) {
				lvlOfError--;
				try {
					payment = Double.parseDouble(pay);	//checks if payment is a double
					lvlOfError--;
					try {
						due = Integer.parseInt(date);	//checks if due date is an integer
						if (due < 0 || due > 31) throw new NumberFormatException();
						lvlOfError--;
					} catch (NumberFormatException e) {
						lvlOfError++;
						DueDateError.setOpacity(0.5);
					}
				} catch (NumberFormatException e) {
					lvlOfError++;
					PaymentError.setOpacity(0.5);
				}
			}
		}
			
			
		if (lvlOfError < 1) {
			String editedScheduledTransaction = name + "~" + Account.getValue() + "~" + TransactionType.getValue()
										+ "~" + due + "~" + Frequency.getValue() + "~" + payment;
				
			//sets new scheduled transaction info to comma separated line
			commonObjs.setScheduledTransactionInformation(editedScheduledTransaction.split("~"));
			//saving data into arraylists for checks and displaying		
			DAC.rewriteData("resources/data/ScheduledTransactionsList.txt",commonObjs.getEditTransacIndex(),editedScheduledTransaction);
			//write the data into the txt file
			commonObjs.displayNewWindow("view/SuccessfullyEditedScheduledTransactionPage.fxml");
		}
	}
}
