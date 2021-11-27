package com.ims.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ims.model.CardsVO;
import com.ims.model.InputItemVO;
import com.ims.model.ItemsVO;

public class ItemsDao implements IItemsDao {

	// this hashmap is acting as database to store pre-populated items and cards
	private static Map<String, ItemsVO> itemMap = new HashMap<>();
	private static Set<String> cardsSet = new HashSet<>();
	private static Map<String, Double> paymentMap = new HashMap<>();

	public static void loadMasterData(List<ItemsVO> itemsList, Set<String> inputCardsSet) {
		for (ItemsVO item : itemsList) {
			itemMap.put(item.getItem(), item);
		}
		for (String card : inputCardsSet) {
			cardsSet.add(card);
		}
		System.out.println("initializing cards with " + cardsSet);
	}

	// this method will print the list of items present in the hashmap(db)
	public void getAllItems() {
		for (Map.Entry<String, ItemsVO> entry : itemMap.entrySet()) {
			System.out.println(entry.getKey() + "-----" + entry.getValue());
		}
	}

	// this method will return the quantity of the item present in the db
	public int getQuantityOfItem(String item) {
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
		System.out.println("Checking >>>>>>>>>>>>>>>> " + cardsSet.contains(cardNo) + "---cardv " + cardNo);
		if (cardsSet.contains(cardNo) == false) {
			System.out.println("Writing this card number   : " + cardNo);
			cardsSet.add(cardNo);
			writeIntoCardsFile(cardNo);
		}
	}

	// this method is for getting all the card of the db
	public void getAllCards() {
		System.out.println(cardsSet);
	}

	public void removeAllCards() {
		cardsSet = null;
	}

	public void writeIntoCardsFile(String cardNo) {
		try {
			String path = System.getProperty("user.dir") + "/cards.csv";
			File file = new File(path);

			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName(), true));
			bw.write("\n");
			bw.write(cardNo);
			bw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
