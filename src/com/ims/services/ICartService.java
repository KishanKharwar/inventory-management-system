package com.ims.services;

import java.util.List;

import com.ims.modal.InputItem;
import com.ims.modal.Items;

public interface ICartService {
	String validateCartItemsQuantity(List<InputItem> items);
	void processOrder(String fileName);
	double calculateTotalPrice(List<InputItem> requestedItems) ;
}
