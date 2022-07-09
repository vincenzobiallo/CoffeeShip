package it.vincenzobiallo.coffeeship.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Classe che consente l'utilizzo dei PDF all'interno del sistema
 */
public class PDFApi {
	
	/**
	 * Crea un documento pdf all'interno di un file
	 * 
	 * @param file
	 * @return instanza del documento pdf scrivibile
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public static Document createPdf(FileOutputStream file) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, file);

		return document;
	}
	
	/**
	 * Genera una Tabella
	 * @param columns
	 * @return
	 */
	public static PdfPTable createTable(int columns) {
		
		PdfPTable table = new PdfPTable(columns);
		
		return table;
	}
	
	/**
	 * Aggiunge un'intestazione ad un istanza di tabella
	 * 
	 * @param table
	 * @param headers
	 */
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
	
	/**
	 * Aggiunge una Riga ad un'istanza di tabella
	 * 
	 * @param table
	 * @param elements
	 */
	public static void addRow(PdfPTable table, String[] elements) {
		
	    for (String element : elements)
	    	table.addCell(element);
	}

}
