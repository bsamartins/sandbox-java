package opencv.facedetection;

import javax.swing.JFrame;

import opencv.webcam.VideoApp;

import org.opencv.core.Mat;

public class FaceDetectionApp extends VideoApp {

	public static void main(String[] args) throws Exception {
		FaceDetectionApp app = new FaceDetectionApp();
		app.run(args);
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
		setTitle("Face Detection");
	}
}
