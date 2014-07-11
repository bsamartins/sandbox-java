package swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JImage extends JPanel {
	
	public enum ImageScale { STRETCH, SCALE_TO_FIT }
	
	private Image image;
	private ImageScale scale;
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if(image != null) {
			Image img = image;			
			switch (scale) {
			case SCALE_TO_FIT:
				img = getScaledInstanceToFit(image, this.getSize());
				break;
			case STRETCH:
				img = getScaledInstanceToFill(image, this.getSize());
				break;
			default:
				break;
			}
			
			int x = (this.getWidth() - img.getWidth(null)) / 2;
		    int y = (this.getHeight() - img.getHeight(null)) / 2;
			graphics.drawImage(img, x, y, null);
			graphics.dispose();
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setScale(ImageScale scale) {
		this.scale = scale;
	}

	public ImageScale getScale() {
		return scale;
	}

	public Image getScaledInstanceToFit(Image original, Dimension size) {
        Dimension masterSize = new Dimension(original.getWidth(this), original.getHeight(this));
        return getScaledInstance(
                        toBufferedImage(original),
                        getScaleFactorToFit(masterSize, size),
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR,
                        true);
    }

    public Image getScaledInstanceToFill(Image original, Dimension size) {
        Dimension masterSize = new Dimension(original.getWidth(this), original.getHeight(this));
        return getScaledInstance(
                        toBufferedImage(original),
                        getScaleFactorToFill(masterSize, size),
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR,
                        true);
    }
    
    public Dimension getSizeToFit(Dimension original, Dimension toFit) {
        double factor = getScaleFactorToFit(original, toFit);
        Dimension size = new Dimension(original);
        size.width *= factor;
        size.height *= factor;
        return size;
    }

    public Dimension getSizeToFill(Dimension original, Dimension toFit) {
        double factor = getScaleFactorToFill(original, toFit);
        Dimension size = new Dimension(original);
        size.width *= factor;
        size.height *= factor;
        return size;
    }
    
    public double getScaleFactor(int iMasterSize, int iTargetSize) {
        return (double) iTargetSize / (double) iMasterSize;
    }

    public double getScaleFactorToFit(Dimension original, Dimension toFit) {
        double dScale = 1d;
        if (original != null && toFit != null) {
            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);
            dScale = Math.min(dScaleHeight, dScaleWidth);
        }
        return dScale;
    }

    public double getScaleFactorToFill(Dimension masterSize, Dimension targetSize) {
        double dScaleWidth = getScaleFactor(masterSize.width, targetSize.width);
        double dScaleHeight = getScaleFactor(masterSize.height, targetSize.height);

        return Math.max(dScaleHeight, dScaleWidth);
    }
    
    protected BufferedImage toBufferedImage(Image original) {
        Dimension masterSize = new Dimension(original.getWidth(this), original.getHeight(this));
        BufferedImage image = createCompatibleImage(masterSize);
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(original, 0, 0, this);
        g2d.dispose();
        return image;
    }
    
    protected BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor, Object hint, boolean bHighQuality) {
        BufferedImage imgScale = img;
        int iImageWidth = (int) Math.round(img.getWidth() * dScaleFactor);
        int iImageHeight = (int) Math.round(img.getHeight() * dScaleFactor);

        if (dScaleFactor <= 1.0d) {
            imgScale = getScaledDownInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
        } else {
            imgScale = getScaledUpInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
        }

        return imgScale;
    }
    
    protected BufferedImage getScaledDownInstance(BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint,
            boolean higherQuality) {

		int type = (img.getTransparency() == Transparency.OPAQUE)
		                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		
		BufferedImage ret = (BufferedImage) img;
		
		if (targetHeight > 0 || targetWidth > 0) {
		    int w, h;
		    if (higherQuality) {
		        // Use multi-step technique: start with original size, then
		        // scale down in multiple passes with drawImage()
		        // until the target size is reached
		        w = img.getWidth();
		        h = img.getHeight();
		    } else {
		        // Use one-step technique: scale directly from original
		        // size to target size with a single drawImage() call
		        w = targetWidth;
		        h = targetHeight;
		    }
		
		    do {
		        if (higherQuality && w > targetWidth) {
		            w /= 2;
		            if (w < targetWidth) {
		                w = targetWidth;
		            }
		        }
		        if (higherQuality && h > targetHeight) {
		            h /= 2;
		            if (h < targetHeight) {
		                h = targetHeight;
		            }
		        }
		
		        BufferedImage tmp = new BufferedImage(Math.max(w, 1), Math.max(h, 1), type);
		        Graphics2D g2 = tmp.createGraphics();
		        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		        g2.drawImage(ret, 0, 0, w, h, null);
		        g2.dispose();
		
		        ret = tmp;
		    } while (w != targetWidth || h != targetHeight);
		} else {
		    ret = new BufferedImage(1, 1, type);
		}
		
		return ret;
	}

	protected BufferedImage getScaledUpInstance(BufferedImage img,
	            int targetWidth,
	            int targetHeight,
	            Object hint,
	            boolean higherQuality) {
	
		int type = BufferedImage.TYPE_INT_ARGB;
		
		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
		    // Use multi-step technique: start with original size, then
		    // scale down in multiple passes with drawImage()
		    // until the target size is reached
		    w = img.getWidth();
		    h = img.getHeight();
		} else {
		    // Use one-step technique: scale directly from original
		    // size to target size with a single drawImage() call
		    w = targetWidth;
		    h = targetHeight;
		}
		
		do {
		    if (higherQuality && w < targetWidth) {
		        w *= 2;
		        if (w > targetWidth) {
		            w = targetWidth;
		        }
		    }
		
		    if (higherQuality && h < targetHeight) {
		        h *= 2;
		        if (h > targetHeight) {
		            h = targetHeight;
		        }
		    }
		
		    BufferedImage tmp = new BufferedImage(w, h, type);
		    Graphics2D g2 = tmp.createGraphics();
		    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		    g2.drawImage(ret, 0, 0, w, h, null);
		    g2.dispose();
		
		    ret = tmp;
		    tmp = null;
		} while (w != targetWidth || h != targetHeight);
		return ret;
	}    
	
	public BufferedImage createCompatibleImage(Dimension size) {
        return createCompatibleImage(size.width, size.height);
    }

    public BufferedImage createCompatibleImage(int width, int height) {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        }

        BufferedImage image = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        image.coerceData(true);
        return image;
    }
}