package com.ims.model;

public class InputItemVO {
	private String item;
	private Integer quantity;
	private String cardNumber;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
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
