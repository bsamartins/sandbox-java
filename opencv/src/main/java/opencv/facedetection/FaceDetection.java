package opencv.facedetection;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
	

	private static final String FACE_CASCADE_NAME = "facade/haarcascade_frontalface_alt.xml";
	private static final String EYES_CASCADE_NAME = "facade/haarcascade_eye_tree_eyeglasses.xml";
	private static final String WINDOW_NAME = "Detect Face";
	
	private static CascadeClassifier faceCascade;
	private static CascadeClassifier eyesCascade;

	static {
		faceCascade = new CascadeClassifier(FACE_CASCADE_NAME);
		eyesCascade = new CascadeClassifier(EYES_CASCADE_NAME);
	}
	
	public static Mat detect(Mat frame) throws Exception {
	        Mat grayFrame = new Mat();
	        MatOfRect faces = new MatOfRect();

	        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGRA2GRAY);
	        Imgproc.equalizeHist(grayFrame, grayFrame);
	        
//	        //-- Detect faces
	        faceCascade.detectMultiScale(grayFrame,faces);

	        Rect[] facesArray = faces.toArray();
	        for (int i = 0; i < facesArray.length; i++)
	        {
	            Rect face = facesArray[i];
				Point center = new Point(face.x + face.width * 0.5, face.y + face.height * 0.5);

	            Core.ellipse(frame, center, new Size(face.width * 0.5, face.height * 0.5), 0, 0, 360, new Scalar(255, 0, 255), 4, 8, 0);

	            Mat faceROI = grayFrame.submat(face);		        

	            MatOfRect eyes = new MatOfRect();	            

	            //-- In each face, detect eyes
	            eyesCascade.detectMultiScale(faceROI, eyes, 2, 2, 0,new Size(1, 1), new Size());
//	            eyesCascade.detectMultiScale(faceROI, eyes);

	            Rect[] eyesArray = eyes.toArray();	    
	            for (int j = 0; j < eyesArray.length; j++)
	            {
	                Rect eye = eyesArray[j];
					Point center1 = new Point(face.x + eye.x + eye.width * 0.5,   face.y + eye.y + eye.height * 0.5);
	                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
	                Core.circle(frame, center1, radius, new Scalar(255, 0, 0), 4, 8, 0);
	            }
	        }
	        
	        return frame;
	}
}
