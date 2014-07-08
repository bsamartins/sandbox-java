package opencv.facedetection;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Savepoint;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class TestFaceDetection {
	
	private static final String FACE_CASCADE_NAME = "facade/haarcascade_frontalface_alt.xml";
	private static final String EYES_CASCADE_NAME = "facade/haarcascade_eye_tree_eyeglasses.xml";
	
	private static CascadeClassifier faceCascade;
	private static CascadeClassifier eyesCascade;
	
	public static void main(String[] args) throws Exception {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		faceCascade = new CascadeClassifier(FACE_CASCADE_NAME);
		eyesCascade = new CascadeClassifier(EYES_CASCADE_NAME);
		
		BufferedImage image = ImageIO.read(new File("m(01-32)_gr.jpg"));
		
		Mat imgMat = getMatFromImage(image);
		ImageIO.write(getImage(imgMat), "jpeg", new FileOutputStream("original.jpg"));
		
		detect(imgMat);
	}

	private static Mat getMatFromImage(BufferedImage image) {
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
	            .getData();

		
	    // Create a Matrix the same size of image
	    Mat imgMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    
	    // Fill Matrix with image values
	    imgMat.put(0, 0, pixels);
		return imgMat;
	}
	
	private static void detect(Mat frame) throws Exception {
		MatOfRect faces = new MatOfRect();
		faceCascade.detectMultiScale(frame, faces, 1.1, 2,  0, new Size(0, 0), new Size(20, 20));

		for(Rect rectFace : faces.toList()) {
			
//			Mat convertedFaceFrame = new Mat(rectFace.height, rectFace.width, CvType.CV_8UC3);
			
			Mat faceFrame = new MatOfRect(rectFace);
			
			BufferedImage image = getImage(faceFrame);
			ImageIO.write(image, "jpeg", new FileOutputStream("test.jpg"));
			
//			faceFrame.convertTo(convertedFaceFrame, CvType.CV_8U);
//						
//			Mat greyFaceFrame = new Mat();			
//						
//			Imgproc.cvtColor(convertedFaceFrame, greyFaceFrame, CvType.CV_8UC3);			
//
//			MatOfRect eyes = new MatOfRect();
//			eyesCascade.detectMultiScale(greyFaceFrame, eyes, 1.1, 2, 1, new Size(0, 0), new Size(2000, 2000));
//			
//			System.out.println(eyes.toList());
		}
	}
	
	private static BufferedImage getImage(Mat mat) throws Exception {
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = ImageIO.read(in);
		
		return img;
	}	
}
