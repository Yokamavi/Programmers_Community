package com.programmers.programmers_community.util;

import com.programmers.programmers_community.model.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// Helper class that exports a list of users to an Excel file (it uses the Apache POI library)
public class ExcelGenerator {
	// Creates "Programmers.xlsx" in the folder where the app is running. If it already exists, it is replaced
	// TODO: use try-with-resources so the file is always closed, even if something fails
	public static void generate(List<User> users) throws IOException {
		// Row 0 is the header, so the users start in row 1
		int rowNum = 1;


		// The file where the Excel will be written
		FileOutputStream fos = new FileOutputStream("Programmers.xlsx");

		// SXSSFWorkbook is the streaming version of the .xlsx workbook, it doesn't keep all the rows in memory
		Workbook book = new SXSSFWorkbook();
		Sheet sheet = book.createSheet("Programmers_Table");

		// Header row with the names of the columns
		Row head = sheet.createRow(0);
		head.createCell(0).setCellValue("Name");
		head.createCell(1).setCellValue("Gender");
		head.createCell(2).setCellValue("Language");

		// One row for each user: username, gender and language
		for (User u : users) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(u.getUsername());
			row.createCell(1).setCellValue(u.getGender());
			row.createCell(2).setCellValue(u.getLanguage());

		}


		// Writes the workbook into the file and closes everything
		book.write(fos);
		fos.close();
		book.close();
	}
}