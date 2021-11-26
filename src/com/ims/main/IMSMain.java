package com.ims.main;

import com.ims.service.CardServiceImpl;

public class IMSMain {
	public static void main(String[] args) {
		CardServiceImpl cardService = new CardServiceImpl();
		cardService.processOrder();
	}
}
