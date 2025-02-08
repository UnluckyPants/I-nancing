package application;

import java.time.LocalDate;

public class Account {
	private String name;
	private LocalDate date;
	private double balance;
	
	public Account(String name, LocalDate date, double balance) {
		this.name = name;
		this.date = date;
		this.balance = balance;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void changeBalance(double trans) {
		this.balance += trans;
	}
}
