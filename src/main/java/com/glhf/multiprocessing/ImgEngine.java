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
        clerk.start(Runtime.getRuntime().availableProcessors()).forEach(el -> exe.execute(el));

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
}
