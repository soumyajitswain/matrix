package com.matrix.image.preprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoDArrayImageConverter extends ImagePixelConverter {
	
	private static final Logger logger =
				LoggerFactory.getLogger(TwoDArrayImageConverter.class);
	

	@Test
	public void twoDImageConverter() throws IOException {
        int[][] arr = ImagePixelConverter.convertTo2DWithoutUsingGetRGB(image);
		int xLenght = arr.length;
		int yLength = arr[0].length;
		BufferedImage b = new BufferedImage(xLenght, yLength, 3);

		for(int x = 0; x < xLenght; x++) {
		    for(int y = 0; y < yLength; y++) {
		        int rgb = (int)arr[x][y]<<16 | (int)arr[x][y] << 8 | (int)arr[x][y];
		        b.setRGB(x, y, rgb);
		    }
		}
		
		File file = new File("copy.png");
		OutputStream out = new FileOutputStream(file);
		ImageIO.write(b, "PNG", out);
		out.flush();
        out.close();
		logger.debug("end "+b.getData());
	}

}
