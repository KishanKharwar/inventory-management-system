package com.ims.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ims.model.CardsVO;
import com.ims.model.InputItemVO;
import com.ims.model.ItemsVO;

public class ReadFile {

	public static String readFile(String fileName) {
		System.out.println("Reading file : " + fileName);
		String path = System.getProperty("user.dir") + "/" + fileName;
		File f = new File(path);
		String line = null;
		String data = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			while ((line = reader.readLine()) != null) {
				if (data.equals("")) {
					data = line;
					continue;
				}
				data = data + "  " + line;
			}

			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static List<ItemsVO> convertToDBItem() {
		List<ItemsVO> itemsList = new ArrayList<>();
		String fileData = readFile("items.csv");
		String[] items = fileData.split(" ");

		for (int i = 1; i < items.length; i++) {
			String[] data = items[i].split(",");
			if (data.length > 1) {
				ItemsVO item = new ItemsVO();
				item.setCategory(data[0]);
				item.setItem(data[1]);
				item.setQuantity(Integer.valueOf(data[2]));
				item.setPrice(Double.valueOf(data[3]));
				itemsList.add(item);
			}
		}
		return itemsList;

	}
	
	public static List<InputItemVO> convertToInputItems(){
		List<InputItemVO> itemsList = new ArrayList<>();
		String fileData = readFile("input.csv");
		String[] items = fileData.split(" ");

		for (int i = 1; i < items.length; i++) {
			String[] data = items[i].split(",");
			if (data.length > 1) {
				InputItemVO item = new InputItemVO();
				item.setItem(data[0]);
				item.setQuantity(Integer.valueOf(data[1]));
				item.setCardNumber(data[2]);
				itemsList.add(item);
			}
		}
		return itemsList;
	}
	public static List<CardsVO> convertToDBCards(){
		List<CardsVO> cardsList = new ArrayList<>();
		String fileData = readFile("cards.csv");
		String[] cards = fileData.split(" ");

		for (int i = 1; i < cards.length; i++) {
			if (cards.length > 1) {
				CardsVO card = new CardsVO();
				card.setCardNumber(cards[0]);
				cardsList.add(card);
			}
		}
		return cardsList;
	}
	
}
