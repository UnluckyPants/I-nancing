package application;

import java.time.LocalDate;

public abstract class TransactionA {
	String account, type, textField, date, frequency = "Monthly";
	double amount;
	
	public TransactionA(String account, String type, String textField, String date, double amount) {
		this.account = account;
		this.type = type;
		this.textField = textField;
		this.date = date;
		this.amount = amount;
	}
	
	public String getType() {
		return type;
	}
	public String getTextField() {
		return textField;
	}
	public double getAmount() {
		return amount;
	}
	public String getAccount() {
		return account;
	}
	public String getFrequency() {
		return frequency;
	}
	public abstract int getDue();
	public abstract LocalDate getDate();
	
}
