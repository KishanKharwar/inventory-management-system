package com.ims.modal;

public class InputItem {
	private String item;
	private int quantity;
	private String cardNumber;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public String toString() {
		return "InputItem [item=" + item + ", quantity=" + quantity + ", cardNumber=" + cardNumber + "]";
	}

}
