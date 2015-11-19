package com.github.bsamartins.java.sandbox.pdf.pdfbox;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;

public class Pdf2Html {

	public static void main(String[] args) throws Exception {
		
		Path path = Files.createTempDirectory(null);		
		
		InputStream is = Pdf2Html.class.getResourceAsStream("/153311-flyers-sample-papers-volume-2.pdf");
		PDDocument document = PDDocument.load(is);
		PDFTextStripper stripper = new PDFText2HTML();

		String pagePath = path.toString() + "/153311-flyers-sample-papers-volume-2.html";
		
	    FileOutputStream fos = new FileOutputStream(new File(pagePath));
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		
	    stripper.setStartPage(0);
	    stripper.setEndPage(document.getNumberOfPages());
	    stripper.writeText(document, osw);
	    fos.close();
		
		if (Desktop.isDesktopSupported()) {
		    Desktop.getDesktop().open(new File(pagePath));
		} else {
			System.out.println("html saved to: " + pagePath);
		}
	}	
}
