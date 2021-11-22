package com.ims.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ims.dao.ItemsDao;
import com.ims.modal.InputItem;
import com.ims.modal.Items;

public class CartServiceImpl implements ICartService {

	private ItemsDao itemsDao = new ItemsDao();

	@Override
	public void processOrder(String fileName) {
		CartServiceImpl cartService = new CartServiceImpl();

		List<InputItem> requestedItems = cartService.convertIntoObject(fileName);
		String incorrectItemQty = validateCartItemsQuantity(requestedItems);

		if (incorrectItemQty != null) {
			writeIntoErrorFile(incorrectItemQty);
		}else {
			
			double totalPrice = calculateTotalPrice(requestedItems);
			writeIntoOutputFile();
		}

		
	}

	public double calculateTotalPrice(List<InputItem> requestedItems) {
		double totalPrice = 0;
		for (InputItem requestedItem : requestedItems) {
			totalPrice = totalPrice + itemsDao.getItemPrice(requestedItem.getItem()) * requestedItem.getQuantity();
		}

		return totalPrice;
	}

	private void writeIntoErrorFile(String item) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"));
			writer.write("Please correct quantities for Item : " + item);
			writer.newLine();

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<InputItem> convertIntoObject(String fileName) {
		String line = "";
		List<InputItem> items = new ArrayList<>();
		try {
			URL url = ItemsDao.class.getClassLoader().getResource(fileName);
			BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
			while ((line = br.readLine()) != null) {
				String[] itemsArray = line.split(",");
				InputItem item = convertIntoInputItem(itemsArray);
				itemsDao.addCardDetail(item.getCardNumber());
				items.add(item);
			}
			return items;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public InputItem convertIntoInputItem(String[] itemArray) {
		InputItem item = new InputItem();

		item.setItem(itemArray[0]);
		item.setQuantity(Integer.valueOf(itemArray[1]));
		item.setCardNumber(itemArray[2]);
		return item;
	}

	@Override
	// this method will be validating the quantity of items from the input file
	public String validateCartItemsQuantity(List<InputItem> requestedItems) {
		for (InputItem requestedItem : requestedItems) {
			if (requestedItem.getQuantity() > itemsDao.getItemQuantity(requestedItem.getItem())) {
				return requestedItem.getItem();
			}
		}
		return null;
	}

	// this method is for writing the output of the cart to the csv file
	public boolean writeIntoOutputFile() {
		try {
			// create a list of objects
			CartServiceImpl cartService = new CartServiceImpl();
			List<InputItem> items = cartService.convertIntoObject("input.csv");
			// create a writer
			BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.csv"));

			// write header record
			writer.write("Items,Quantity,Total Price of item");
			writer.newLine();

			// write all records
			for (InputItem item : items) {

				writer.write(item.getItem() + "," + item.getQuantity()+ "," + itemsDao.getItemPrice(item.getItem()) * item.getQuantity());
				writer.newLine();
			}

			writer.write("Grand Total : " + calculateTotalPrice(items));
			// close the writer
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return true;
	}

}
