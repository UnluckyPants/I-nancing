package application.controller;

import java.util.ArrayList;
import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EnterScheduledTransactionController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	
	@FXML private TextField ScheduledTransactionName, Payment, DueDate;
	@FXML private ChoiceBox<String> Account, TransactionType, Frequency;
	@FXML private Label PaymentError, EmptyFieldsError, DueDateError, TransacNameError;
	
	@FXML public void initialize() {
		Account.getItems().addAll(commonObjs.getAccountNames());
		Account.setValue(commonObjs.getAccountNames().get(0));
		TransactionType.getItems().addAll(commonObjs.getTransactionTypes());
		TransactionType.setValue(commonObjs.getTransactionTypes().get(0));
		Frequency.getItems().add("Monthly");
		Frequency.setValue("Monthly");
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
			if (names.contains(name)) {
				goodToGo = false;
				TransacNameError.setOpacity(0.5);
				lvlOfError++;
			}
			if (goodToGo) {
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
			String enteredScheduledTransaction = name + "~" + Account.getValue() + "~" + TransactionType.getValue()
										+ "~" + due + "~" + Frequency.getValue() + "~" + payment;
				
			//sets new scheduled transaction info to comma separated line
			commonObjs.addScheduledTransactionInformation(enteredScheduledTransaction.split("~"));
			//saving data into arraylists for checks and displaying		
			DAC.WriteData("resources/data/ScheduledTransactionsList.txt", enteredScheduledTransaction);
			//write the data into the txt file
			commonObjs.displayNewWindow("view/SuccessfullyAddedScheduledTransactionPage.fxml");
		}
	}
}
