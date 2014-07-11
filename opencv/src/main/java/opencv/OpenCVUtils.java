package opencv;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

public class OpenCVUtils {
	public static void loadLibrary() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public static BufferedImage getImage(Mat mat) throws Exception {
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = ImageIO.read(in);
		
		return img;
	}		

	public static Image getJavaFXImage(Mat mat) throws Exception {
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		Image img = new Image(in);		
		return img;
	}		

	private Mat getMat(BufferedImage image) {
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
	            .getData();		
	    // Create a Matrix the same size of image
	    Mat imgMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    
	    // Fill Matrix with image values
	    imgMat.put(0, 0, pixels);
	    
		return imgMat;
	}
}
