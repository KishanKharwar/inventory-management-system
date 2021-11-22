package com.ims.main;

import java.io.IOException;

import com.ims.dao.ItemsDao;
import com.ims.services.CartServiceImpl;

public class IMSMain {

	public static void main(String[] args) throws IOException {
		ItemsDao.loadItemsFromFile("items.csv");
		CartServiceImpl cartService = new CartServiceImpl();
		cartService.processOrder("input.csv");
		ItemsDao.getAllCards();
	}

}
