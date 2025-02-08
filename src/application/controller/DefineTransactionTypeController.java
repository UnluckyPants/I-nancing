package application.controller;

import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class DefineTransactionTypeController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();
	@FXML private TextField TransactionTypeName;
	@FXML private Label EmptyFieldsError;
	@FXML private Label DuplicateNameError;
	
	@FXML public void saveTransactionTypeOp() {
		int lvlOfError = 2;
		EmptyFieldsError.setOpacity(0);
		DuplicateNameError.setOpacity(0);
		//Hiding error messages
			
		String name = TransactionTypeName.getText();
			
		if (name.equals("")) {
			EmptyFieldsError.setOpacity(0.5);
			//checking for empty fields, showing message depending
			//if the fields are empty or not
		} else {
			lvlOfError--;
			if (commonObjs.getTransactionTypes().contains(name)) {
				DuplicateNameError.setOpacity(0.5);
			} else lvlOfError--;
			//checking if input name is a duplicate
			//showing message as appropriate
		}
			
		if (lvlOfError == 0) {
			commonObjs.addTransactionType(name);
			//add transaction types to common objects
			DAC.WriteData("resources/data/TransactionTypeList.txt", name);
			//write the data into the txt file
			commonObjs.displayNewWindow("view/SuccessfullyAddedTransactionType.fxml");
		}
	}
	
}
