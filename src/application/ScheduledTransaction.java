package application;

import java.time.LocalDate;

public class ScheduledTransaction extends TransactionA{
	
	public ScheduledTransaction(String name, String accName, String type, String due, String frequency, String payment) {
		super(accName, type, name, due, Double.parseDouble(payment));
	}
	
	public int getDue() {
		return Integer.parseInt(super.date);
	}
	
	public LocalDate getDate() {
		return null;
	}
}