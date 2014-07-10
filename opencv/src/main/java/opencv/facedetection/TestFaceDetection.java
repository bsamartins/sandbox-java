package opencv.facedetection;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import opencv.OpenCVUtils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class TestFaceDetection extends Application {
	
	static {
		OpenCVUtils.loadLibrary();
	}

	private static final String FACE_CASCADE_NAME = "facade/haarcascade_frontalface_alt.xml";
	private static final String EYES_CASCADE_NAME = "facade/haarcascade_eye_tree_eyeglasses.xml";
	private static final String WINDOW_NAME = "Detect Face";
	
	private static CascadeClassifier faceCascade;
	private static CascadeClassifier eyesCascade;
	
	private BufferedImage image;	
	
	private ImagePanel panel = new ImagePanel();
	private Button detectButton = new Button("Detect");
	private BorderLayout layout = new BorderLayout();
	
	public static void main(String[] args) throws Exception {
		
		faceCascade = new CascadeClassifier(FACE_CASCADE_NAME);
		eyesCascade = new CascadeClassifier(EYES_CASCADE_NAME);
		
		launch(args);
	}

	private Mat getMatFromImage(BufferedImage image) {
		byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer())
	            .getData();		
	    // Create a Matrix the same size of image
	    Mat imgMat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    
	    // Fill Matrix with image values
	    imgMat.put(0, 0, pixels);
	    
		return imgMat;
	}
	
	private Mat detect(Mat frame) throws Exception {
	        Mat grayFrame = new Mat();
	        MatOfRect faces = new MatOfRect();

	        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGRA2GRAY);
	        Imgproc.equalizeHist(grayFrame, grayFrame);

	        
//	        //-- Detect faces
	        faceCascade.detectMultiScale(grayFrame,faces);

	        Rect[] facesArray = faces.toArray();
	        System.out.println(facesArray.length);
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
	            System.out.println(eyesArray.length);
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

	private BufferedImage getImage(Mat mat) throws Exception {
		MatOfByte bytemat = new MatOfByte();

		Highgui.imencode(".jpg", mat, bytemat);

		byte[] bytes = bytemat.toArray();

		InputStream in = new ByteArrayInputStream(bytes);

		BufferedImage img = ImageIO.read(in);
		
		return img;
	}		
	
	private class ImagePanel extends JPanel {
		
		Image source;				
		
		public Image getSource() {
			return source;
		}

		public void setSource(Image source) {
			this.source = source;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println();
			if(source != null) {
				g.drawImage(source, getBounds().x, getBounds().y, null);
			}						
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
        detectButton.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
//				try {
//					image = ImageIO.read(new File("GroupPic.jpg"));
//					Mat mat = getMatFromImage(image);
//					Mat bwMat = new Mat();
//					Imgproc.cvtColor(mat, bwMat, Imgproc.COLOR_BGR2GRAY);
//					image = getImage(detect(mat));			
//					panel.setSource(image);
//					repaint();
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}											
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(detectButton);

        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
//		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		this.setSize(200, 200);
//		this.add(detectButton, BorderLayout.NORTH);
//		this.add(panel);
//		
//		detectButton.addActionListener(new ActionListener() {			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//			}
//		});				
	}
}
