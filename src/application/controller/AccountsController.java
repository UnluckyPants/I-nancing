package application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Account;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class AccountsController implements Initializable{
	
	private CommonObjs commonObjs = CommonObjs.getInstance();

	@FXML private TableView<Account> table;
	@FXML private TableColumn<Account, String> namesColumn;
	@FXML private TableColumn<Account, LocalDate> dateColumn;
	@FXML private TableColumn<Account, Double> balanceColumn;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//set up the separate columns
		namesColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("Name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Account, LocalDate>("Date"));
		balanceColumn.setCellValueFactory(new PropertyValueFactory<Account, Double>("Balance"));
		
		//load data and initially sorting by the most recent date
		dateColumn.setSortType(TableColumn.SortType.DESCENDING);
		table.setItems(commonObjs.getObservAccounts());
		table.getSortOrder().add(dateColumn);
	}
}
