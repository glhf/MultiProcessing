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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ImgEngine {
    private static final Logger LOG = LogManager.getLogger(ImgEngine.class);

    public void print() {
        BufferedImage img = null;
        URL path = ImgEngine.class.getClassLoader().getResource("./bmp-img.bmp");
        try {
            img = ImageIO.read(path);
        } catch (IOException e) {
            LOG.error("Img load error ", e);
        }

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Clerk clerk = new Clerk(img, newImg);

        ExecutorService exe = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        clerk.start().forEach( el -> {
            exe.execute(el);
        });
        exe.shutdown();
        try {
          exe.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
          LOG.info(e);
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
    /*for (int i = 0; i < data.getWidth(); i++) {
        for (int j = 0; j < data.getHeight(); j++) {
            newImg.setRGB(i,j, new Data(i, j, new Color(img.getRGB(i, j))).getGray().getRGB());
        }
    }*/
}
