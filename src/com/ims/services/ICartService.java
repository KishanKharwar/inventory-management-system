package com.ims.services;

import java.util.List;

import com.ims.modal.InputItemVO;
import com.ims.modal.ItemsVO;

public interface ICartService {
	String validateItemsQuantity(List<InputItemVO> items);

	void processOrder(String fileName);

	double calculateTotalPrice(List<InputItemVO> requestedItems);

	boolean validateCategoryThreshold();

	void getCategoryOfInputItem(List<InputItemVO> requestedItems);
}
