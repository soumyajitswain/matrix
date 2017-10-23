package com.matrix.image.preprocess;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageResizer extends ImagePixelConverter {
	
	@Test
	public void resizeImage() throws IOException {
		resize(image, 20, 20);
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

		File file = new File("resize.png");
		OutputStream out = new FileOutputStream(file);
		ImageIO.write(dimg, "PNG", out);
		out.flush();
        out.close();
        
	    return dimg;
	}  
}
