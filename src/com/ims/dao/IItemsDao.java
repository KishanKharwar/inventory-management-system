package com.ims.dao;

public interface IItemsDao {
	void getAllItems();

	int getQuantityOfItem(String item);

	double getItemPrice(String item);

	String getCategory(String item);

	void addCardDetail(String cardNo);

	void getAllCards();

	void writeIntoCardsFile(String cardNo);
}
