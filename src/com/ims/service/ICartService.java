package com.ims.service;

import java.util.List;

import com.ims.model.InputItemVO;

public interface ICartService {
	void processOrder();

	List<String> validateInputItemQuantity();

	String validateCategoryThreshold();

	void getCategoryOfInputItem(List<InputItemVO> inputItems);

	Double calculateTotalPrice();
}
