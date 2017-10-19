package com.matrix.image.preprocess;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImagePixelConverter {

	private static final Logger logger = 
			LoggerFactory.getLogger(ImagePixelConverter.class); 

	protected BufferedImage image;

	@Before
	public void init() throws IOException {
		image = ImageIO.read(ImageSplitterTest.class.getResourceAsStream("/images.jpg"));	
	}

	@Test
	public void convertToPixel() throws InterruptedException {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] pixel = new int[width * height];

		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, pixel, 0, width);

		pg.grabPixels();

	}

	@Test
	public void convertToPixelIRGB() {
		logger.debug("Matrix " +Arrays.deepToString(convertTo2DWithoutUsingGetRGB(image)));
	}
	
	protected static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		  final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		  final int width = image.getWidth();
		  final int height = image.getHeight();
		  final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		  int[][] result = new int[height][width];
		  if (hasAlphaChannel) {
		     final int pixelLength = 4;
		     for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
		        int argb = 0;
		        argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
		        argb += ((int) pixels[pixel + 1] & 0xff); // blue
		        argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
		        argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
		        result[row][col] = argb;
		        col++;
		        if (col == width) {
		           col = 0;
		           row++;
		        }
		     }
		  } else {
		     final int pixelLength = 3;
		     for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
		        int argb = 0;
		        argb += -16777216; // 255 alpha
		        argb += ((int) pixels[pixel] & 0xff); // blue
		        argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
		        argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
		        result[row][col] = argb;
		        col++;
		        if (col == width) {
		           col = 0;
		           row++;
		        }
		     }
		  }

		  return result;
		 }
}
