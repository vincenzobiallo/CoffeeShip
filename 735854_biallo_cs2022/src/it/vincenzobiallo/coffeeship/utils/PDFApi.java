package it.vincenzobiallo.coffeeship.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFApi {
	
	public static Document createPdf(FileOutputStream file) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, file);

		return document;
	}
	
	public static PdfPTable createTable(int columns) {
		
		PdfPTable table = new PdfPTable(columns);
		
		return table;
	}
	
	public static void addTableHeader(PdfPTable table, String[] headers) {
	    Stream.of(headers)
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.ORANGE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	public static void addRow(PdfPTable table, String[] elements) {
		
	    for (String element : elements)
	    	table.addCell(element);
	}
	
	public static Chunk addText(String text, BaseColor color) {
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, color);
		Chunk chunk = new Chunk(text, font);
		
		return chunk;
	}

}
