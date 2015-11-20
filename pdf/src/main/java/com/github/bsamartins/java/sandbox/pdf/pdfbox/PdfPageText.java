package com.github.bsamartins.java.sandbox.pdf.pdfbox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.graphics.state.PDGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.PDTextState;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfPageText {

	public static void main(String[] args) throws Exception {
		
		InputStream is = PdfPageText.class.getResourceAsStream("/153311-flyers-sample-papers-volume-2.pdf");
		PDDocument document = PDDocument.load(is);

		TestStripper stripper = new TestStripper();		
		StringWriter sw = new StringWriter();		
		stripper.writeText(document, sw);		
		//System.out.println(sw.toString());
		
//		for(int i=0; i < document.getNumberOfPages(); i++) {
//			PDPage page = document.getPage(i);
//			System.out.println(page.getThreadBeads().size());
//			page.getThreadBeads().forEach( bead -> {
//				System.out.println(bead.getNextBead());
//			});
//		}		
	}	
	

	private static class TestStripper extends PDFTextStripper {

		public TestStripper() throws IOException {
			super();
		}
		
		@Override
		public void showTextString(byte[] string) throws IOException {
	        PDGraphicsState state = getGraphicsState();
	        PDTextState textState = state.getTextState();
	        
	        // get the current font
	        PDFont font = textState.getFont();
	        if (font == null) {
	            font = PDFontFactory.createDefaultFont();
	        }
	        
	        InputStream in = new ByteArrayInputStream(string);
	        while (in.available() > 0) {
	            // decode a character
	            int code = font.readCode(in);
	            String unicode = font.toUnicode(code);
	            
	            System.out.print(unicode);
	        }
	        
			System.out.println();
			super.showTextString(string);
		}
	}
}
