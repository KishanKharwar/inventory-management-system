package com.ims.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ims.dao.ItemsDao;
import com.ims.modal.InputItemVO;

public class FileToObjectHelper {
	
	private ItemsDao itemsDao = new ItemsDao();
	
	//this method is reading the input file and converting it into the input object
	public List<InputItemVO> convertIntoObject(String fileName) {
		String line = "";
		List<InputItemVO> items = new ArrayList<>();
		try {
			URL url = ItemsDao.class.getClassLoader().getResource(fileName);
			BufferedReader br = new BufferedReader(new FileReader(url.getPath()));
			while ((line = br.readLine()) != null) {
				String[] itemsArray = line.split(",");
				InputItemVO item = convertIntoInputItem(itemsArray);
				itemsDao.addCardDetail(item.getCardNumber());
				items.add(item);
			}
			return items;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public InputItemVO convertIntoInputItem(String[] itemArray) {
		InputItemVO item = new InputItemVO();

		item.setItem(itemArray[0]);
		item.setQuantity(Integer.valueOf(itemArray[1]));
		item.setCardNumber(itemArray[2]);
		return item;
	}

}
