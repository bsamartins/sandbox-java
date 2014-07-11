package opencv.facedetection;

import javax.swing.JFrame;

import org.opencv.core.Mat;

import opencv.webcam.WebCamApp;

public class FaceDetectionApp extends WebCamApp {

	public static void main(String[] args) throws Exception {
		FaceDetectionApp app = new FaceDetectionApp();
		app.run();
	}
	
	@Override
	protected Mat processMat(Mat mat) throws Exception {		
		return FaceDetection.detect(mat);
	}

	@Override
	public void start(String... args) throws Exception {
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
