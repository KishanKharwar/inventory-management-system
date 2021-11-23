package com.ims.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ims.modal.ItemsVO;

/**
 * 
 * @author author-name this class is following 2 design patterns 1) DAO 2) Cache
 *         DP for storing the data to the map.
 *
 */
public class ItemsDao {

	// this hashmap is acting as database to store pre-populated items and cards
	private static Map<String, ItemsVO> itemMap = new HashMap<>();
	private static List<String> cardList = new ArrayList();

	// this method is meant for loading the csv file from the relative file path
	// i.e., prooject-dir/src/filename
	public static void loadItemsFromFile(String fileName) throws IOException {
		ItemsDao itemsDao = new ItemsDao();
		String line = null;
		int count = 0;
		try {
			URL url = ItemsDao.class.getClassLoader().getResource(fileName);
			BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
			while ((line = br.readLine()) != null) {

				if (count == 0) {
					count++;
					continue;
				}
				String[] itemsArray = line.split(",");
				ItemsVO item = itemsDao.convertIntoItem(itemsArray);
				itemMap.put(itemsArray[1], item);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// this method will print the list of items present in the hashmap(db)
	public void getAllItems() {
		for (Map.Entry<String, ItemsVO> entry : itemMap.entrySet()) {
			System.out.println(entry.getKey() + "-----" + entry.getValue());
		}
	}

	// this method will return the quantity of the item present in the db
	public int getItemQuantity(String item) {
		return itemMap.get(item).getQuantity();
	}

	// this method will return the quantity of the item present in the db
	public double getItemPrice(String item) {
		return itemMap.get(item).getPrice();
	}

	// this method will return the category of the input item
	public String getCategory(String item) {
		return itemMap.get(item).getCategory();
	}

	// this method is for adding the card information to the database if not present
	public void addCardDetail(String cardNo) {
		if (!cardList.contains(cardNo)) {
			cardList.add(cardNo);
		}
	}

	// this method is for getting all the card of the db
	public void getAllCards() {
		System.out.println(cardList);
	}

	// this method is used by loadItemCSVFile method for converting the items array
	// to items object
	// input should be items array from the buffered reader.
	private ItemsVO convertIntoItem(String[] itemArray) {
		ItemsVO item = new ItemsVO();

		item.setCategory(itemArray[0]);
		item.setItem(itemArray[1]);
		item.setQuantity(Integer.valueOf(itemArray[2]));
		item.setPrice(Double.valueOf(itemArray[3]));
		return item;
	}

}
