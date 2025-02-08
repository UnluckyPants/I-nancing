package application.controller;

import java.io.IOException;

import application.CommonObjs;
import application.DataAccessClass;
import javafx.fxml.FXML;

public class MainController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	private DataAccessClass DAC = DataAccessClass.getInstance();

	//Initializes Main Page with Appropriate Window
	@FXML public void initialize() throws IOException {
		try {
			DAC.initialization();
			if(commonObjs.firstTime) showDueTransactions();
			else javafx.application.Platform.runLater(() -> commonObjs.displayNewWindow("view/MainImage.fxml"));
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	//Displays Home Image
	@FXML public void returnToHomePage() {
		commonObjs.displayNewWindow("view/MainImage.fxml");
	}

	@FXML public void showAddAccountOp() {
		commonObjs.displayNewWindow("view/addAccountPage.fxml");
	}
	
	@FXML public void showAccountsOp() {
		commonObjs.displayNewWindow("view/AccountsPage.fxml");
	}
	
	@FXML public void showTransactionTypeOp() {
		commonObjs.displayNewWindow("view/DefineNewTransactionTypePage.fxml");
	}
	
	@FXML public void showEnterTransactionOp() {
		if (commonObjs.getAccounts().isEmpty() || commonObjs.getTransactionTypes().isEmpty()) {
			commonObjs.displayNewWindow("view/EmptyListsError.fxml");
		} else {
			commonObjs.displayNewWindow("view/EnterTransactionPage.fxml");
		}
	}

	@FXML public void showTransactionsListOp() {
		commonObjs.displayNewWindow("view/TransactionsList.fxml");
	}

	@FXML public void showEnterScheduledTransactionOp() {
		if (commonObjs.getAccounts().isEmpty() || commonObjs.getTransactionTypes().isEmpty()) {
			commonObjs.displayNewWindow("view/EmptyListsError.fxml");
		} else {
			commonObjs.displayNewWindow("view/EnterScheduledTransactionPage.fxml");
		}
	}

	@FXML public void showScheduledTransactionsOp() {
		commonObjs.displayNewWindow("view/ScheduledTransactionsList.fxml");
	}	
	
	@FXML public void showSearchTransactionsOp() {
		commonObjs.displayNewWindow("view/SearchTransactionsPage.fxml");
	}
	@FXML public void showSearchScheduledTransactionsOp() {
		commonObjs.displayNewWindow("view/SearchScheduledTransactionsPage.fxml");
	}

	//Checks and displays due transactions, if any. If no due transactions exist, it displays the appropriate message.
	@FXML public void showDueTransactions() {
		if (commonObjs.dueTransactionsExist())  javafx.application.Platform.runLater(() -> commonObjs.displayNewWindow("view/DueTransactions.fxml"));
		else if (commonObjs.firstTime) {
			javafx.application.Platform.runLater(() -> commonObjs.displayNewWindow("view/MainImage.fxml"));
			commonObjs.firstTime = false;
		}
		else javafx.application.Platform.runLater(() -> commonObjs.displayNewWindow("view/NoDueTransactions.fxml"));
	}

	@FXML public void showTransactionReport() {
		commonObjs.displayNewWindow("view/TransactionReport.fxml");
	}
}
