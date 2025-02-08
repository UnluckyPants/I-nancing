package application.controller;

import java.time.LocalDate;
import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddAccountController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	
	@FXML private TextField AccountName;
	@FXML private DatePicker OpeningDate;
	@FXML private TextField OpeningBalance;
	@FXML private Label EmptyFieldsError;
	@FXML private Label InvalidBalanceInputError;
	@FXML private Label DuplicateNameError;
	
	@FXML public void initialize() {
		OpeningDate.setValue(LocalDate.now());
		//sets default date to current date
	}
	
	@FXML public void saveAccountDataOp() {
		
		int lvlOfError = 3;
		EmptyFieldsError.setOpacity(0);
		InvalidBalanceInputError.setOpacity(0);
		DuplicateNameError.setOpacity(0);
		//Hiding error messages
		
		String name = AccountName.getText();
		LocalDate date = OpeningDate.getValue();	
		String balance = OpeningBalance.getText();
		//Reading values from input fields
		    
		if (date == null || name.equals("") || balance.equals("")) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			lvlOfError--;
			try {
				Double.parseDouble(balance);
				lvlOfError--;
			} catch (NumberFormatException e) {
				InvalidBalanceInputError.setOpacity(0.5);
			}
			//checking if opening balance is a number or not
			//showing message as appropriate
			
			if (commonObjs.getAccountNames().contains(name)) {
				DuplicateNameError.setOpacity(0.5);
			} else lvlOfError--;
			//checking if input name is a duplicate
			//showing message as appropriate
		}
	
		if (lvlOfError == 0) {
			String newAccount = name + "," + date + "," + Double.parseDouble(balance);		
			//sets new account info to comma separated line
	
			commonObjs.addAccInformation(newAccount.split(","));
			//saving data into arraylists for checks and displaying
	
			DAC.WriteData("resources/data/AccountList.txt", newAccount);
			//write the data into the txt file
			
			commonObjs.displayNewWindow("view/successfullyAddedAccountPage.fxml");
		}
	}
}
