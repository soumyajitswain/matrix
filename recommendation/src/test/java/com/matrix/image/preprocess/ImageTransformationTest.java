package com.matrix.image.preprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

public class ImageTransformationTest {
	private Image image;

    @Before
    public void readPinup() throws Exception {
        InputStream in = ImageTransformationTest.class.getResourceAsStream("/images.jpg");
        if (in == null)
            throw new RuntimeException("For this tests you need a file pinup.jpg available in the classpath!");
        image = Image.build(ImageIO.read(in));
        in.close();
    }

    @Test
    public void testNoOp() throws Exception {
        filterToFile("noop.png", new int[]{
                0, 0, 0,
                0, 1, 0,
                0, 0, 0,
        });
    }

    @Test
    public void testEdgeDetect() throws Exception {
        filterToFile("edge-detect.png", new int[]{
                1,  1, 1,
                1, -5, 1,
                1,  1, 1,
        });
    }

    @Test
    public void testBlur() throws Exception {
        filterToFile("blurred.png", new int[]{
                1, 1, 1, 1, 1, 1, 1,
                1, 2, 2, 2, 2, 2, 1,
                1, 2, 4, 4, 4, 2, 1,
                1, 2, 4, 8, 4, 2, 1,
                1, 2, 4, 4, 4, 2, 1,
                1, 2, 2, 2, 2, 2, 1,
                1, 1, 1, 1, 1, 1, 1,
        });
    }

    private void filterToFile(String filename, int[] convolutionMatrix) throws Exception {
        Filter filter = new Filter((int) Math.sqrt(convolutionMatrix.length), convolutionMatrix);
        filter.applyToImage(image).saveToFile(filename);
    }

    public static class Matrix {

        private final int width, height;
        private final int[] data;

        public Matrix(int width, int height, int[] data) {
            if (data.length != width * height)
                throw new RuntimeException("Length should be "
                        + width * height + " (" + width + " x " + height
                        + "), given an array with "
                        + data.length + " elements");
            this.width = width;
            this.height = height;
            this.data = data;
        }
        
        public int width() {
            return width;
        }
        
        public int height() {
            return height;
        }

        public int at(int col, int row) {
            return data[row * width + col];
        }

        public boolean hasElementAt(int col, int row) {
            return row >= 0 && row < height && col >= 0 && col < width;
        }

        public void forEachElement(ForEachMatrixElement callback) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    callback.apply(this, col, row);
                }
            }
        }
    }

    public interface ForEachMatrixElement {
        void apply(Matrix matrix, int col, int row);
    }

    public static class Image extends Matrix {

        private Image(int width, int height, int[] imageData) {
            super(width, height, imageData);
        }

        private static Image build(BufferedImage image) {
            int w = image.getWidth();
            int h = image.getHeight();
            int[] rgb = new int[w * h];
            image.getRGB(0, 0, w, h, rgb, 0, w);
            return new Image(w, h, rgb);
        }

        public void saveToFile(String fileName) throws Exception {
            BufferedImage image = new BufferedImage(width(), height(), BufferedImage.TYPE_INT_RGB);
            forEachElement((data, col, row) -> {
                image.setRGB(col, row, data.at(col, row));
            });
            File file = new File(fileName);
            OutputStream out = new FileOutputStream(file);
            ImageIO.write(image, "PNG", out);
            out.flush();
            out.close();
        }
    }

    public static class Pixel {

        private int red = 0;
        private int green = 0;
        private int blue = 0;
        private int weight = 0;

        public void addWeightedColor(int color, int weight) {
            red += weight * (color >> 16 & 0xFF);
            green += weight * (color >> 8 & 0xFF);
            blue += weight * (color & 0xFF);
            this.weight += Math.abs(weight);
        }

        public int rgb() {
            int rgb = 0;
            if (weight != 0) {
                rgb |= (red / weight) << 16;
                rgb |= (green / weight) << 8;
                rgb |= blue / weight;
            }
            return rgb;
        }
    }

    public static class Filter extends Matrix {

        private final int size;

        public Filter(int size, int[] filterData) {
            super(size, size, filterData);
            this.size = size;
        }

        public Image applyToImage(Image srcImage) {
            int[] dstImage = new int[srcImage.width() * srcImage.height()];

            srcImage.forEachElement((image, imageCol, imageRow) -> {
                Pixel pixel = new Pixel();

                forEachElement((filter, col, row) -> {
                    int neighbourCol = imageCol + col - (size / 2);
                    int neighbourRow = imageRow + row - (size / 2);
                    if (srcImage.hasElementAt(neighbourCol, neighbourRow)) {
                        pixel.addWeightedColor(srcImage.at(neighbourCol, neighbourRow), filter.at(col, row));
                    }
                });

                dstImage[(imageRow * srcImage.width() + imageCol)] = pixel.rgb();
            });

            return new Image(srcImage.width(), srcImage.height(), dstImage);
        }
    }
}
