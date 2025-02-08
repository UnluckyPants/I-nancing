package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommonObjs {
	
	// This object is used to share the HBox and account list across the controllers

	private static CommonObjs commonObjs = new CommonObjs();
	
	private HBox homePage;
	
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<String> transactionTypes = new ArrayList<>();
	private ArrayList<Transaction> transactions = new ArrayList<>();
	private ArrayList<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
	private TransactionA editTransac;
	private int editTransacIndex;
	

	private TransactionA reportedTransaction;
	private String firstChoice, secondChoice;
	
	private CommonObjs() {}
	
	public static CommonObjs getInstance() {
		return commonObjs;
	}
	public HBox getHomePage() {
		return homePage;
	}
	public void setHomePage(HBox homePage) {
		this.homePage = homePage;
	}	
	
	public ArrayList<String> getAccountNames(){
		ArrayList<String> names = new ArrayList<>();
		for (Account a : accounts) names.add(a.getName());
		return names;
	}
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
	public void addAccInformation(String[] data) {
		accounts.add(new Account(data[0],LocalDate.parse(data[1]), Double.parseDouble(data[2])));
	}
	public ObservableList<Account> getObservAccounts(){
		return FXCollections.observableArrayList(this.getAccounts());
	}
	
	public ArrayList<String> getTransactionTypes() {
		return this.transactionTypes;
	}
	public void addTransactionType(String data) {
		transactionTypes.add(data);
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void addTransactionInformation(String[] data) {
		transactions.add(new Transaction(data[0], data[1], data[2], data[3], data[4]));
	}
	public void setTransactionInformation(String[] data) {
		transactions.set(editTransacIndex, new Transaction(data[0], data[1], data[2], data[3], data[4]));
	}
	public ObservableList<TransactionA> getObservTransactions() {
		return FXCollections.observableArrayList(this.getTransactions());
	}

	public ArrayList<String> getScheduledTransactionNames() {
		ArrayList<String> names = new ArrayList<>();
		for (ScheduledTransaction s : scheduledTransactions) {
			names.add(s.getTextField());
		}
		return names;
	}
	public ObservableList<TransactionA> getObservScheduledTransactions() {
		return FXCollections.observableArrayList(this.getScheduledTransactions());
	}
	public void addScheduledTransactionInformation(String[] data) {
		scheduledTransactions.add(new ScheduledTransaction(data[0], data[1], data[2], data[3], data[4], data[5]));
	}
	public void setScheduledTransactionInformation(String[] data) {
		scheduledTransactions.set(editTransacIndex, new ScheduledTransaction(data[0],data[1], data[2], data[3], data[4], data[5]));
	}	
	public ArrayList<ScheduledTransaction> getScheduledTransactions() {
		return scheduledTransactions;
	}
	
	public TransactionA getEditTransac() {
		return editTransac;
	}
	public int getEditTransacIndex() {
		return editTransacIndex;
	}
	public void setEditTransac(TransactionA transac) {
		editTransac = transac;
		if (transac.getDue() == -1) editTransacIndex = transactions.indexOf(transac);
		else editTransacIndex = scheduledTransactions.indexOf(transac);
		
	}

	public TransactionA getReportedTransaction() {
		return reportedTransaction;
	}

	public void setReportedTransaction(TransactionA transac) {
        reportedTransaction = transac;
    }

	public void setSelections(String first, String second) {
		firstChoice = first;
		secondChoice = second;
	}

	public String getFirstChoice() {
        return firstChoice;
    }

	public String getSecondChoice() {
        return secondChoice;
    }
	
	public void displayNewWindow(String urlString) {
		URL url = getClass().getClassLoader().getResource(urlString);
		try {
			AnchorPane enterTransaction = FXMLLoader.load(url);
		
			HBox homePage = commonObjs.getHomePage();
		
			if (homePage.getChildren().size() > 1)
				homePage.getChildren().remove(1); 
			// If the homepage had another window open, close it
		
			homePage.getChildren().add(enterTransaction);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObservableList<TransactionA> dueTransactions = FXCollections.observableArrayList();

	public void checkDueTransactions() {
		dueTransactions.clear();
		int today = LocalDate.now().getDayOfMonth();

		if (commonObjs.getScheduledTransactions() != null) {
			for (ScheduledTransaction transac : scheduledTransactions) {
				if (transac.getDue() == today) {
					dueTransactions.add(transac);
				}
			}
		}
	}

	//Quick check to prevent null errors
	public boolean dueTransactionsExist() {
		checkDueTransactions();
		return !dueTransactions.isEmpty();
	}

	public boolean firstTime = true;

}
