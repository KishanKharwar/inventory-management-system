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
import com.ims.helper.FileToObjectHelper;
import com.ims.modal.InputItemVO;
import com.ims.modal.ItemsVO;

public class CartServiceImpl implements ICartService {

	private ItemsDao itemsDao = new ItemsDao();

	public void processOrder(String fileName) {
		System.out.println("Inside processOrder method of CartServiceImpl class with file name as input : " + fileName);
		FileToObjectHelper helper = new FileToObjectHelper();

		// converting the input request to InputItemVO object
		List<InputItemVO> requestedItems = helper.convertIntoObject(fileName);
		System.out.println("Input Items in object : " + requestedItems);

		// validating the input item qty
		String incorrectItemQty = validateItemsQuantity(requestedItems);
		System.out.println("Checking the incorrect item : " + incorrectItemQty);

		// if the input item qty is greater than the item qty in db the throwing error
		// in a file
		if (incorrectItemQty != null) {
			System.out.println(">>>>>>> Incorrect Item Quantity found : " + incorrectItemQty);
			writeIntoErrorFile(incorrectItemQty);
		} else {
			// double totalPrice = calculateTotalPrice(requestedItems);
			System.out.println("Writing the total price to the output file ");
			writeIntoOutputFile(requestedItems);
			System.out.println("Successfully written to the output file ");
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Successfully returning >>>>>>>>>>>>>>>>>>>>>>>>>>");
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

	private void writeIntoErrorFile(String item) {
		System.out.println("Inside method writeIntoErrorFile of CartServiceImpl class with input as incorrect item  : " + item);
		
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
			System.out.println("Started writting in the output file ");
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
