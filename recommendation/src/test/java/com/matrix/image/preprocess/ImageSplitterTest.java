package com.matrix.image.preprocess;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageSplitterTest {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(ImageSplitterTest.class); 
	
	private BufferedImage image;
	
	private BufferedImage[] subimages;
	
	private static final int x = 100, y = x;
	
	
	@Before
	public void init() throws IOException {
	    image = ImageIO.read(ImageSplitterTest.class.getResourceAsStream("/images.jpg"));	
	}

	@Test
	public void splitImage() throws IOException {
		int col = image.getWidth() / x;
		int row = image.getHeight() / y;
		subimages = new BufferedImage[col*row];
		
		int s=0;
		for(int i = 0 ; i < row ; i ++) {
			for( int j = 0 ; j < col ; j ++) {
				subimages[s++] = image.getSubimage(j*x, i*y, x, y);
				//Helper.saveImage(subimages[s], s+"");
			}
		}
		for(BufferedImage bi : subimages) {
			String name = UUID.randomUUID().toString()+".png";
			Helper.saveImage(bi, name);
		}
	}
	
	public static class Helper {
		 
		public static void saveImage(BufferedImage bi, String fileName) throws IOException {
			if( bi == null ) return;
			
			File file = new File(fileName);
	        OutputStream out = new FileOutputStream(file);
	        ImageIO.write(bi, "PNG", out);
	        out.flush();
	        out.close();
			
		}
	}
}
