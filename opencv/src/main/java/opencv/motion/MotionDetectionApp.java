package opencv.motion;

import javax.swing.JFrame;

import opencv.webcam.VideoApp;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MotionDetectionApp extends VideoApp {

	private Mat lastImageMat;
	private Mat lastDiffMat;
	
	public static void main(String[] args) throws Exception {
		MotionDetectionApp app = new MotionDetectionApp();
		app.run();		
	}
	
	@Override
	public void start(String ... args) throws Exception {
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected Mat processMat(Mat mat) throws Exception {		
		Mat result = getDifferenceMat(mat);
		int numberChanges = 0;
		
		Integer minX = null, maxX = null, minY = null, maxY = null;
		for(int row = 0; row < result.rows(); row++) {
			for(int col = 0; col < result.cols(); col++) {
				// check if at pixel (j,i) intensity is equal to 255
		        // this means that the pixel is different in the sequence
		        // of images (prev_frame, current_frame, next_frame)
				if(checkPixel(result.get(col, row), 150)) {
					numberChanges++;
		            if(minX == null || minX>col) minX = col;
		            if(maxX == null || maxX<col) maxX = col;
		            if(minY == null || minY>row) minY = row;
		            if(maxY == null || maxY<row) maxY = row;
				}
			}			
		}
		
		if(numberChanges > 0) {
			Point min = new Point(minX, minY);
			Point max = new Point(maxX, maxY);
			Core.rectangle(mat, min, max, new Scalar(255, 0, 0));
		}
		
		return mat;
	}

	private Mat getDifferenceMat(Mat mat) {
		Mat result = new Mat();		
		if(lastImageMat != null) {					
			Mat diffMat = new Mat();
			Core.subtract(mat, lastImageMat, diffMat);
			if(lastDiffMat != null) {
				Mat temp = new Mat();
				Core.bitwise_and(diffMat, lastDiffMat, temp);	
				Imgproc.threshold(temp, result, 35, 255, Imgproc.THRESH_BINARY);				
			}
			lastDiffMat = diffMat;			
		}
		lastImageMat = mat;
		return result;
	}
	
	private boolean checkPixel(double array[], double threshold) {
		if(array != null){
			for(double d : array) {
				if(d > threshold) {
					return true;
				}	
			}			
		} 
		return false;
	}
}
