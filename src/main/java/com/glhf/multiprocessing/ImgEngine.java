package com.glhf.multiprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 */
public class ImgEngine {
    private static final Logger LOG = LogManager.getLogger(ImgEngine.class);

    public void print() {
        //LOG.info(Runtime.getRuntime().availableProcessors());//get count of processors for creating threads
        BufferedImage img = null;
        URL path = ImgEngine.class.getClassLoader().getResource("./bmp-img.bmp");
        LOG.info(path.toString());

        try {
            img = ImageIO.read(path);
        } catch (IOException e) {
            LOG.error("Img load error ", e);
        }

        Raster data = img.getData();
        BufferedImage newImg = new BufferedImage(data.getWidth(), data.getHeight(), BufferedImage.TYPE_INT_RGB);
        new Clerk(img, newImg).start();

        /*for (int i = 0; i < data.getWidth(); i++) {
            for (int j = 0; j < data.getHeight(); j++) {
                newImg.setRGB(i,j, new Data(i, j, new Color(img.getRGB(i, j))).getGray().getRGB());
            }
        }*/
        long i=0;
        while (i < 1000000000){
            i++;
        }

        try {
            File ouptut = new File("grayscale.jpg");
            ImageIO.write(newImg, "jpg", ouptut);
        } catch (IOException e) {
            LOG.error("Img load error ", e);
        }
    }

    Color getGray(Color col) {
        int red = (int) (col.getRed() * 0.299);
        int green = (int) (col.getGreen() * 0.587);
        int blue = (int) (col.getBlue() * 0.114);
        return new Color(red + green + blue, red + green + blue, red + green + blue);
    }

    class Data {
        Point point = new Point(0, 0);
        Color color;

        Data(int x, int y, Color color) {
            this.point.setLocation(x, y);
            this.color = color;
        }

        Data(Color color) {
            this.color = color;
        }

        int getX() {
            return (int) this.point.getX();
        }

        int getY() {
            return (int) this.point.getY();
        }

        Color getGray() {
            int red = (int) (this.color.getRed() * 0.299);
            int green = (int) (this.color.getGreen() * 0.587);
            int blue = (int) (this.color.getBlue() * 0.114);
            return new Color(red + green + blue, red + green + blue, red + green + blue);
        }
    }

    /**
     * extend thread for preparing queue
     */
    class Clerk {
        final BufferedImage img;
        final BufferedImage out;

        Clerk(BufferedImage img, BufferedImage out) {
            this.img = img;
            this.out = out;
        }

        /**
         * divide indexes of image for calculating with n process
         */
        private void start() {
            List<Point> list = new LinkedList<>();
            list = getBounds(img.getHeight(), Runtime.getRuntime().availableProcessors());
            list.forEach(el -> {
                LOG.info(el.toString());
                new Thread() {
                    public void run() {
                        LOG.info("start thread "+el.x+" "+el.y+" "+img.getWidth());
                        for (int i = 0; i < img.getWidth(); i++) {
                            for (int j = el.x; j < el.y; j++) {
                                out.setRGB(i,j, new Data(i, j, new Color(img.getRGB(i, j))).getGray().getRGB());
                            }
                        }
                    }
                }.start();
            });
        }

        private List<Point> getBounds(int maxIndexValue, int count) {
            List<Point> list = new LinkedList<>();
            double n = count;
            double x = maxIndexValue;
            double diff = x / n;
            int start = 0;
            int end = 0;
            if ((diff % 1) == 0) {
                for (int i = 0; i < n; i++) { // startIndex = diff*i  endIndex diff*(i+1)
                    if (i == 0) {//start index = 0;
                        list.add(new Point((int) (diff * i), (int) (diff * (i + 1))));
                    } else {
                        list.add(new Point((int) ((diff * i) + 1), (int) (diff * (i + 1))));
                    }
                }
            } else {
                int d = (int) diff;
                start = 0;
                end = d;
                while (end < x) {
                    //put hear thread init
                    list.add(new Point(start,end));
                    if (start == 0) {
                        start++;
                    }
                    start += d;
                    end += d;
                }
                end = (int) x;
                list.add(new Point(start, end));
            }
            return list;
        }
    }
}
