package opencv.webcam;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import opencv.OpenCVUtils;

import org.opencv.highgui.VideoCapture;

public class WebCamApp extends JFrame implements WindowListener {

	static {
		OpenCVUtils.loadLibrary();
	}
	
	VideoCapture vc;
	
	@Override
	public void windowOpened(WindowEvent e) {		
		vc = new VideoCapture();
		if(vc.isOpened()) {
			System.out.println("Camera up and running");
		} else {
			System.out.println("Camera down :(");
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println(e);
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println(e);
		System.exit(0);
	}

	@Override
	public void windowIconified(WindowEvent e) {	
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	public static void main(String[] args) {		
		System.out.println("Starting app");
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				WebCamApp app = new WebCamApp();
				app.setBounds(0, 0, 200, 200);
				app.setResizable(false);
				app.addWindowListener(app);								
				app.setVisible(true);
				Toolkit tk = app.getToolkit();
			}
		});		
	}
}
