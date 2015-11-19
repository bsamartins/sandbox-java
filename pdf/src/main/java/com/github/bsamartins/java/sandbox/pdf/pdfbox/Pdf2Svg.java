package com.github.bsamartins.java.sandbox.pdf.pdfbox;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class Pdf2Svg {

	public static void main(String[] args) throws Exception {
		
		Path path = Files.createTempDirectory(null);		
		
		InputStream is = Pdf2Svg.class.getResourceAsStream("/153311-flyers-sample-papers-volume-2.pdf");
		PDDocument document = PDDocument.load(is);
		PDFRenderer pdfRenderer = new SvgPDFRenderer(document);
		
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
	
	private static class SvgPDFRenderer extends PDFRenderer {

		public SvgPDFRenderer(PDDocument document) {
			super(document);
		}

		@Override
		public BufferedImage renderImage(int pageIndex, float scale, ImageType imageType) throws IOException {
	        PDPage page = document.getPage(pageIndex);

	        PDRectangle cropbBox = page.getCropBox();
	        float widthPt = cropbBox.getWidth();
	        float heightPt = cropbBox.getHeight();
	        int widthPx = Math.round(widthPt * scale);
	        int heightPx = Math.round(heightPt * scale);
	        int rotationAngle = page.getRotation();

	        // swap width and height
	        BufferedImage image;
	        if (rotationAngle == 90 || rotationAngle == 270)
	        {
	            image = new BufferedImage(heightPx, widthPx, toBufferedImageType(imageType));
	        }
	        else
	        {
	            image = new BufferedImage(widthPx, heightPx, toBufferedImageType(imageType));
	        }

	        // use a transparent background if the imageType supports alpha
	        Graphics2D g = image.createGraphics();
	        if (imageType == ImageType.ARGB)
	        {
	            g.setBackground(new Color(0, 0, 0, 0));
	        }
	        else
	        {
	            g.setBackground(Color.WHITE);
	        }

	        renderPage(page, g, image.getWidth(), image.getHeight(), scale, scale);
	        g.dispose();

	        return image;
		}
		
		   // renders a page to the given graphics
	    private void renderPage(PDPage page, Graphics2D graphics, int width, int height, float scaleX, float scaleY) throws IOException {
	        graphics.clearRect(0, 0, width, height);

	        graphics.scale(scaleX, scaleY);
	        // TODO should we be passing the scale to PageDrawer rather than messing with Graphics?

	        PDRectangle cropBox = page.getCropBox();
	        int rotationAngle = page.getRotation();
	        
	        if (rotationAngle != 0)
	        {
	            float translateX = 0;
	            float translateY = 0;
	            switch (rotationAngle)
	            {
	                case 90:
	                    translateX = cropBox.getHeight();
	                    break;
	                case 270:
	                    translateY = cropBox.getWidth();
	                    break;
	                case 180:
	                    translateX = cropBox.getWidth();
	                    translateY = cropBox.getHeight();
	                    break;
	            }
	            graphics.translate(translateX, translateY);
	            graphics.rotate((float) Math.toRadians(rotationAngle));
	        }

	        PageDrawer drawer = createPageDrawer(this, page);
	        drawer.drawPage(graphics, cropBox);
	    }
		
		private PageDrawer createPageDrawer(PDFRenderer pdfRenderer, PDPage page) throws IOException {
			Class<PageDrawerParameters> cl = PageDrawerParameters.class;
			try {
				Constructor<PageDrawerParameters> cons = cl.getDeclaredConstructor(PDFRenderer.class, PDPage.class);
				cons.setAccessible(true);
				PageDrawerParameters parameters = cons.newInstance(pdfRenderer, page);			
				return new PageDrawer(parameters);										
			} catch(Exception e) {
				throw new IOException(e);
			}
		}

		private int toBufferedImageType(ImageType imageType) {
			switch(imageType) {
				case ARGB: return BufferedImage.TYPE_INT_ARGB;
				case BINARY: return BufferedImage.TYPE_BYTE_BINARY;
				case GRAY: return BufferedImage.TYPE_BYTE_GRAY;
				case RGB: return BufferedImage.TYPE_INT_RGB;
				default: return -1;
			}
		}		
	}		 
}
