package com.ims.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.dao.ItemsDao;
import com.ims.model.CardsVO;
import com.ims.model.InputItemVO;
import com.ims.model.ItemsVO;
import com.ims.reader.ReadFile;
import com.ims.writer.WriteIntoFile;

public class CardServiceImpl implements ICartService {

	private ReadFile reader = new ReadFile();
	private ItemsDao itemsDao = new ItemsDao();
	private static Map<String, Integer> thresholdMap = new HashMap<>();
	private static Map<String, Integer> inputCategoryMap = new HashMap<>();
	private static Map<String, Double> paymentMap = new HashMap<>();

	public void processOrder() {

		loadMasterData();

		boolean finalOutput = true;

		List<String> itemsWithIncorrectQty = validateInputItemQuantity();
		if (!itemsWithIncorrectQty.isEmpty() && finalOutput) {
			WriteIntoFile.writeIntoFile(itemsWithIncorrectQty,true);
			finalOutput = false;
		}

		String thresholdValue = validateCategoryThreshold();
		if (thresholdValue != null && finalOutput) {
			WriteIntoFile.writeIntoFile(thresholdValue,false);
			finalOutput = false;
		}

		if (finalOutput) {
			Double totalPrice = calculateTotalPrice();
			WriteIntoFile.writeIntoFile(totalPrice,false);
		}

	}

	private void loadMasterData() {
		List<ItemsVO> itemsList = reader.convertToDBItem();
		List<CardsVO> cardsList = reader.convertToDBCards();
		itemsDao.loadMasterData(itemsList, cardsList);
	}

	public List<String> validateInputItemQuantity() {
		List<InputItemVO> inputItemsList = reader.convertToInputItems();
		List<String> incorrectItemsList = new ArrayList<>();

		for (InputItemVO inputItem : inputItemsList) {
			if (inputItem.getQuantity() > itemsDao.getQuantityOfItem(inputItem.getItem())) {
				incorrectItemsList.add(inputItem.getItem());
			}
		}
		return incorrectItemsList;
	}

	public String validateCategoryThreshold() {
		List<InputItemVO> inputItemsList = reader.convertToInputItems();
		String fileData = ReadFile.readFile("config.txt");
		String statement = null;
		convertIntoThresholdValue(fileData);
		getCategoryOfInputItem(inputItemsList);
		for (Map.Entry<String, Integer> thresholdEntry : thresholdMap.entrySet()) {
			if (inputCategoryMap.containsKey(thresholdEntry.getKey())
					&& inputCategoryMap.get(thresholdEntry.getKey()) > thresholdEntry.getValue()) {
				statement = "Please see the max category which can be added to the cart...";
			}
		}
		return statement;
	}

	private void convertIntoThresholdValue(String fileData) {
		String[] items = fileData.split(" ");
		for (int i = 0; i < items.length; i++) {
			String[] data = items[i].split("=");
			if (data.length > 1) {
				thresholdMap.put(data[0], Integer.valueOf(data[1]));
			}

		}
	}

	public void getCategoryOfInputItem(List<InputItemVO> inputItems) {
		for (InputItemVO inputItem : inputItems) {
			String category = itemsDao.getCategory(inputItem.getItem());
			// here if validateCategoryThreshold method of ItemDao class return false that
			// means validation fails.
			if (inputCategoryMap.containsKey(category)) {
				inputCategoryMap.put(category, inputCategoryMap.get(category) + 1);
			} else {
				inputCategoryMap.put(category, 1);
			}
		}
	}

	public Double calculateTotalPrice() {
		addPaymentInfo();
		List<InputItemVO> inputItems = reader.convertToInputItems();
		double totalPrice = 0;

		for (Map.Entry<String, Double> entry : paymentMap.entrySet()) {
			totalPrice = totalPrice + entry.getValue();
			itemsDao.addCardDetail(entry.getKey());
		}

		System.out.println("Grand total of the items : " + totalPrice);
		return totalPrice;
	}

	public void addPaymentInfo() {
		List<InputItemVO> inputItems = reader.convertToInputItems();
		for (InputItemVO inputItem : inputItems) {
			if (!paymentMap.containsKey(inputItem.getCardNumber())) {
				paymentMap.put(inputItem.getCardNumber(),
						itemsDao.getItemPrice(inputItem.getItem()) * inputItem.getQuantity());
			} else {
				paymentMap.put(inputItem.getCardNumber(), paymentMap.get(inputItem.getCardNumber())
						+ itemsDao.getItemPrice(inputItem.getItem()) * inputItem.getQuantity());

			}
		}

	}
}
