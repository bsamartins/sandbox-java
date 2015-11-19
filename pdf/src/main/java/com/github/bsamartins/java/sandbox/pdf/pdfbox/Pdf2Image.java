package com.github.bsamartins.java.sandbox.pdf.pdfbox;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Pdf2Image {

	public static void main(String[] args) throws Exception {
		
		Path path = Files.createTempDirectory(null);		
		
		InputStream is = Pdf2Image.class.getResourceAsStream("/153311-flyers-sample-papers-volume-2.pdf");
		PDDocument document = PDDocument.load(is);
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		
		int pageCounter = 0;
		for (@SuppressWarnings("unused") PDPage page : document.getPages()) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB);
			
			// suffix in filename will be used as the file format
			String pagePath = path.toString() + "/153311-flyers-sample-papers-volume-2" + "-" + (pageCounter+1) + ".png";
			System.out.println("Saving page " + (pageCounter+1) + ", " + pagePath);
		    ImageIOUtil.writeImage(bim, pagePath, 300);
		    pageCounter++;
		}
		
		if (Desktop.isDesktopSupported()) {
		    Desktop.getDesktop().open(path.toFile());
		} else {
			System.out.println("images saved to: " + path.toString());
		}
	}	
}
