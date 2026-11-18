package com.programmers.programmers_community.util;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.programmers.programmers_community.model.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// Helper class that exports a list of users to a PDF file (it uses the OpenPDF library)
public class PdfGenerator {
	// Creates "Programmers.pdf" in the folder where the app is running. If it already exists, it is replaced
	// TODO: use try-with-resources so the file is always closed, even if something fails
	public static void generate(List<User> users) throws IOException {
		// The PDF document, it is still empty
		Document doc = new Document();

		// The file where the PDF will be written
		FileOutputStream fos = new FileOutputStream("Programmers.pdf");

		// Links the document with the file, everything we add to the document goes to the file
		PdfWriter.getInstance(doc, fos);

		// The document must be opened before adding any content
		doc.open();

		// Title and a blank space under it
		doc.add(new Paragraph("Programmers List"));
		doc.add(new Paragraph("\n\n"));

		// Table with 3 columns
		PdfPTable table = new PdfPTable(3);

		// The first three cells are the header of the table
		table.addCell("Name");
		table.addCell("Gender");
		table.addCell("Language");
		// Then we add one row for each user: username, gender and language
		for (User u : users) {
			table.addCell(u.getUsername());
			table.addCell(u.getGender());
			table.addCell(u.getLanguage());
		}
		doc.add(table);

		// Closing the document finishes writing the PDF
		doc.close();
	}
}