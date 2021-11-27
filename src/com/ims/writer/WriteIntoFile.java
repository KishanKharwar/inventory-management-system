package com.ims.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WriteIntoFile {

	public static void writeIntoFile(Object data, boolean overwriteFile) {
		try {
			String fileName = "output.txt";
			String path = System.getProperty("user.dir") + "/" + fileName;
			File file = new File(path);
			BufferedWriter bw = null;

			if (data instanceof Double || data instanceof Integer) {
				// total amount
				bw = new BufferedWriter(new FileWriter(file.getName(), overwriteFile));
				bw.write("Toal Price : " + data);
			}

			if (data instanceof String) {
				// failed in threshold
				bw = new BufferedWriter(new FileWriter(file.getName(), overwriteFile));
				bw.write(String.valueOf(data));
			}

			if (data instanceof List) {
				// item qty not matching
				bw = new BufferedWriter(new FileWriter(file.getName(), overwriteFile));
				bw.write("Please correct quantities : ");
				List<String> strList = (List) data;
				for (String s : strList) {
					bw.write("\n");
					bw.write(s);
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
