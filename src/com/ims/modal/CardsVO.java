package com.ims.modal;

public class CardsVO {
	private String cardNumber;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "Cards [cardNumber=" + cardNumber + "]";
	}

}