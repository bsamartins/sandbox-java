package opencv.webcam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import opencv.OpenCVUtils;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import swing.JImage;
import swing.JImage.ImageScale;
import swing.JSemaphore;

public class VideoApp extends JFrame implements WindowListener {

	static {
		OpenCVUtils.loadLibrary();
	}
	
	private static final String[] ACCEPTED_FILES = { "mpeg", "avi", "wmv" };
	private static final Color COLOR_STATUS_BAR_BG = new Color(69, 97, 162);
	private static final Color COLOR_WHITE_ALPHA = new Color(255, 255, 255, 100);	
	
	private VideoCapture vc;
	private JImage image = new JImage();
	private Thread thread;
	private JPanel statusBar = new JPanel();
	private JSemaphore statusSemaphore = new JSemaphore();
	
	public static void main(String[] args) throws Exception  {			
		VideoApp app = new VideoApp();
		app.run();
	}

	final public void run(String ... args) throws Exception {	
		
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());		 
		
		 JLabel labelStatusBar = new JLabel("Started");
		 labelStatusBar.setForeground(Color.WHITE);		 
		 
		 statusSemaphore.setForeground(COLOR_WHITE_ALPHA);
		 
		 statusBar.setBackground(COLOR_STATUS_BAR_BG);
		 statusBar.add(statusSemaphore);
		 statusBar.add(labelStatusBar);
		 statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		 statusBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		 
		 image.setScale(ImageScale.SCALE_TO_FIT);
		 image.setBackground(Color.BLACK);
		 image.setSize(this.getSize());
		
		 initializeMenu();		
		 addWindowListener(this);
		 add(image, BorderLayout.CENTER);
		 add(statusBar, BorderLayout.SOUTH);		
		
		 start(args);
	}

	private void initializeMenu() {
		JMenuItem webcamSourceMenuItem = new JMenuItem("Webcam");
		webcamSourceMenuItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				stopStream();
				vc = new VideoCapture(0);
				startStream();
			}
		});
		
		JMenuItem fileSourceMenuItem = new JMenuItem("File...");
		fileSourceMenuItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new AllowedFilesFilter("Video Files", ACCEPTED_FILES, true));
				int result = fileChooser.showOpenDialog(null);
				
				if(JFileChooser.APPROVE_OPTION == result) {
					File file = fileChooser.getSelectedFile();
					System.out.println(file);
					stopStream();
					vc = new VideoCapture(file.getAbsolutePath());
					startStream();					
				}
			}
		});
		
		JMenu sourceMenu = new JMenu("Source");
		sourceMenu.add(fileSourceMenuItem);
		sourceMenu.add(webcamSourceMenuItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(sourceMenu);
		
		setJMenuBar(menuBar);
	}
	
	public void start (String ... args) throws Exception {
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setTitle("Video Streaming");
	}	

	private void startStream() {
		System.out.println("Starting stream");
		if(vc != null) {
			this.statusSemaphore.setForeground(Color.WHITE);
			if(vc.isOpened()) {				
				System.out.println("Stream opened");
				stream();
			} else {
				System.out.println("Stream not opened");
			}
		}
	}
	
	private void stopStream() {
		if(thread != null && !thread.isInterrupted()) {
			thread.interrupt();
			this.statusSemaphore.setForeground(COLOR_WHITE_ALPHA);
		}		
	}
	
	private void stream() {
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				System.out.println("Grabbing video");
		        
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
		stopStream();
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
	
	private final class AllowedFilesFilter extends FileFilter {
		
		private String[] allowedFileExtensions;
		private String description;
		private boolean appendExtensions;
				
		public AllowedFilesFilter (String description, String[] allowedfileExtensions) {
			this.allowedFileExtensions = allowedfileExtensions;
			this.description = description;
			this.appendExtensions = false;
		}

		public AllowedFilesFilter (String description, String[] allowedfileExtensions, boolean appendExtensions) {
			this.allowedFileExtensions = allowedfileExtensions;
			this.description = description;
			this.appendExtensions = true;
		}

		@Override
		public String getDescription() {			
			StringBuffer sb = new StringBuffer(description);		
			if(appendExtensions && this.allowedFileExtensions != null && this.allowedFileExtensions.length > 0) {
				sb.append(" (");
				for(int i=0; i<this.allowedFileExtensions.length; i++) {
					sb.append("*.").append(this.allowedFileExtensions[i]);
					if(i < (this.allowedFileExtensions.length-1)) {
						sb.append(", ");
					}
				}
				sb.append(")");
			}			
			return sb.toString();
		}

		@Override
		public boolean accept(File f) {
			String extension = getExtension(f);
			if(f.isFile()){
				if(extension != null && this.allowedFileExtensions != null) {
					for(String filteredExtension : allowedFileExtensions) {
						if(filteredExtension.equals(extension)) {
							return true;
						}
					}
				}				
			} else if(f.isDirectory()) {
				return true;
			}
			return false;
		}

		/*
		 * Get the extension of a file.
		 */  
		public String getExtension(File f) {
		    String ext = null;
		    String s = f.getName();
		    int i = s.lastIndexOf('.');

		    if (i > 0 &&  i < s.length() - 1) {
		        ext = s.substring(i+1).toLowerCase();
		    }
		    return ext;
		}
	}
}
