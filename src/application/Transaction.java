package application;

import java.time.LocalDate;

public class Transaction extends TransactionA{
	
	public Transaction(String accName, String transType, String date, String descript, String trans) {
		super(accName,transType,descript,date,Double.parseDouble(trans));
	}

	public LocalDate getDate() {
		return LocalDate.parse(super.date);
	}
	
	public int getDue() {
		return -1;
	}
}
