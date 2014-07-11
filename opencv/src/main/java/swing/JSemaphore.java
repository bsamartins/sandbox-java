package swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class JSemaphore extends JPanel {
	
	public JSemaphore() {
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {		
		Graphics2D g2 = (Graphics2D)graphics;
		   g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                        RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.setOpaque(false);
		super.paintComponent(g2);		
		g2.fillOval(0, 0, this.getWidth(), this.getHeight());;
		g2.dispose();
	}
}