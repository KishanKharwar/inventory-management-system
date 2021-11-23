package com.ims.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.dao.ItemsDao;
import com.ims.helper.FileToObjectHelper;
import com.ims.modal.InputItemVO;

public class CartServiceImpl implements ICartService {

	private ItemsDao itemsDao = new ItemsDao();
	private static Map<String, Integer> countMap = new HashMap<>();

	public void processOrder(String fileName) {
		System.out.println("Inside processOrder method of CartServiceImpl class with file name as input : " + fileName);
		FileToObjectHelper helper = new FileToObjectHelper();

		// converting the input request to InputItemVO object
		List<InputItemVO> requestedItems = helper.convertIntoObject(fileName);
		System.out.println("Input Items in object : " + requestedItems);

		// getting the category of all the requested item
		getCategoryOfInputItem(requestedItems);

		// validating the input item qty
		String incorrectItemQty = validateItemsQuantity(requestedItems);

		// validating the allowed category of the quantity
		boolean validation = validateCategoryThreshold();
		System.out.println("Checking the incorrect item : " + incorrectItemQty);

		// if the input item qty is greater than the item qty in db the throwing error
		// in a file
		if (incorrectItemQty != null) {
			System.out.println(">>>>>>> Incorrect Item Quantity found : " + incorrectItemQty);
			writeIntoErrorFile(incorrectItemQty);
		} else if (!validation) {
			System.out.println(
					"Please correct the Category, The allowed category is Essentials to a maximum of 3, Luxury to 4 and Misc to 6");
		} else {

			// double totalPrice = calculateTotalPrice(requestedItems);
			System.out.println("Writing the total price to the output file ");
			writeIntoOutputFile(requestedItems);
			System.out.println("Successfully written to the output file ");
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Returning  from processOrder >>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	// this method will be validating the quantity of items from the input file, if
	// no incorrect item qty found then returning null
	public String validateItemsQuantity(List<InputItemVO> requestedItems) {
		System.out.println(
				"Inside method validateItemsQuantity of CartServiceImpl class with input as : " + requestedItems);
		for (InputItemVO requestedItem : requestedItems) {

			// checking if the requested qty is greater than db then returning the incorrect
			// item name
			if (requestedItem.getQuantity() > itemsDao.getItemQuantity(requestedItem.getItem())) {
				System.out.println("Found incorrect item qyt : " + requestedItem.getItem());
				return requestedItem.getItem();
			}
		}
		return null;
	}

	// This method is for performing the order validation based on category
	public void getCategoryOfInputItem(List<InputItemVO> requestedItems) {
		for (InputItemVO requestedItem : requestedItems) {
			String category = itemsDao.getCategory(requestedItem.getItem());
			// here if validateCategoryThreshold method of ItemDao class return false that
			// means validation fails.
			if (countMap.containsKey(category)) {
				countMap.put(category, countMap.get(category) + 1);
			} else {
				countMap.put(category, 1);
			}
		}
	}

	// This method is for performing the order validation based on category
	public boolean validateCategoryThreshold() {

		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			if ("Essentials".equalsIgnoreCase(entry.getKey()) && countMap.get(entry.getKey()) > 3) {
				return false;
			}
			if ("Luxury".equalsIgnoreCase(entry.getKey()) && countMap.get(entry.getKey()) > 4) {
				return false;
			}

			if ("Misc".equalsIgnoreCase(entry.getKey()) && countMap.get(entry.getKey()) > 6) {
				return false;
			}
		}

		return true;
	}

	public void getCardDetails() {
		itemsDao.getAllCards();
	}
	
	private void writeIntoErrorFile(String item) {
		System.out.println(
				"Inside method writeIntoErrorFile of CartServiceImpl class with input as incorrect item  : " + item);

		try {
			System.out.println("Strated writing in the error file ");
			BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"));
			writer.write("Please correct quantities for Item : " + item);
			writer.newLine();

			writer.close();
			System.out.println("Successfully written in the error file");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// this method is for writing the output of the cart to the csv file
	private void writeIntoOutputFile(List<InputItemVO> items) {
		System.out.println("Inside method writeIntoOutputFile of CartServiceImpl class having input as requested item");
		try {
			// create a writer
			System.out.println("Started writting in the output file");
			BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.csv"));

			// write header record
			writer.write("Items,Quantity,TotalPriceOfItem");
			writer.newLine();

			// write all records
			for (InputItemVO item : items) {

				writer.write(item.getItem() + "," + item.getQuantity() + ","
						+ itemsDao.getItemPrice(item.getItem()) * item.getQuantity());
				writer.newLine();
			}

			writer.write("Grand Total : " + calculateTotalPrice(items));
			// close the writer
			writer.close();

			System.out.println("successfully written in the file ");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public double calculateTotalPrice(List<InputItemVO> requestedItems) {
		System.out.println("Inside method writeIntoOutputFile of CartServiceImpl class having input as requested item");
		double totalPrice = 0;
		for (InputItemVO requestedItem : requestedItems) {
			totalPrice = totalPrice + itemsDao.getItemPrice(requestedItem.getItem()) * requestedItem.getQuantity();
		}
		System.out.println("Grand total of the items : " + totalPrice);
		return totalPrice;
	}

}
