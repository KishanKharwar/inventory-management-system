package com.ims.model;

public class PaymentVO {
	private String cardNumber;
	private double amount;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaymentVO [cardNumber=" + cardNumber + ", amount=" + amount + "]";
	}

}
