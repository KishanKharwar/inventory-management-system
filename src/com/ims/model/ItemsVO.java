package com.ims.model;

public class ItemsVO {

	private String category;
	private String item;
	private Integer quantity;
	private Double price;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ItemsVO [category=" + category + ", item=" + item + ", quantity=" + quantity + ", price=" + price + "]";
	}

}
