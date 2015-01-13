package com.glhf.multiprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Hello world!
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

        ConcurrentLinkedQueue<Data> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < data.getWidth(); i++) {
            for (int j = 0; j < data.getHeight(); j++){
                queue.add(new Data(i,j,new Color(img.getRGB(i,j))));
            }
        }

        try {
            File ouptut = new File("grayscale.jpg");
            ImageIO.write(newImg, "jpg", ouptut);
        } catch (IOException e) {
            LOG.error("Img load error ", e);
        }
    }

    Color getGray(Color col){
            int red = (int) (col.getRed() * 0.299);
            int green = (int) (col.getGreen() * 0.587);
            int blue = (int) (col.getBlue() * 0.114);
            return new Color(red + green + blue, red + green + blue, red + green + blue);
    }

    class Data{
        Point point = new Point(0,0);
        Color color;

        Data(int x, int y, Color color){
            this.point.setLocation(x,y);
            this.color = color;
        }

        int getX(){
            return (int)this.point.getX();
        }

        int getY(){
            return (int)this.point.getY();
        }
    }

    /**
     * extend thread for preparing queue
     */
    class Clerk{}

    /**
     * extend tread for count gray image
     */
}
