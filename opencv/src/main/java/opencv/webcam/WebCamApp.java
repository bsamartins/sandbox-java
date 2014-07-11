package opencv.webcam;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import opencv.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import swing.JImage;
import swing.JImage.ImageScale;

public class WebCamApp extends JFrame implements WindowListener {

	static {
		OpenCVUtils.loadLibrary();
	}
	
	private VideoCapture vc;
	private JImage image;
	private Thread thread;
	
	public static void main(String[] args) throws Exception  {
		
		getCallingClass();
		
		WebCamApp app = new WebCamApp();
		app.run();
	}

	final public void run(String ... args) throws Exception {		
		vc = new VideoCapture(0);		
		image = new JImage();
		image.setScale(ImageScale.SCALE_TO_FIT);
		image.setBackground(Color.BLACK);
		image.setSize(this.getSize());
		
		this.setLayout(new GridLayout(1,1));
		this.addWindowListener(this);
		this.add(image);
		
		start(args);
		
        startWebcam();        
	}
	
	public void start (String ... args) throws Exception {
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	private void startWebcam() throws Exception {
		System.out.println("Starting webcam");
		if(vc.isOpened()) {
			System.out.println("Camera up and running");
			streamWebcam();
		} else {
			System.out.println("oops");
		}
	}
	
	private void streamWebcam() {
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Grabbing cam");
		        
				while(!Thread.interrupted()){
					try {
						Mat grabMat = new Mat();
						if(vc.grab() && vc.read(grabMat)){
							image.setImage(OpenCVUtils.getImage(processMat(grabMat)));
							repaint();							
						}						        
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(Thread.interrupted()) {
					System.out.println("releasing");
					vc.release();
				}				
			}
			
		});
		thread.start();
		System.out.println("after");		
	}
	
	protected Mat processMat(Mat mat) throws Exception {
		return mat;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println(Thread.activeCount());
		if(thread != null) {
			thread.interrupt();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}	
	
	private static void getCallingClass() {
		StackTraceElement[] stack = new Exception().getStackTrace();
		for(StackTraceElement element : stack) {
			System.out.println(element);
		}
		System.out.println(stack);
	}	
}
