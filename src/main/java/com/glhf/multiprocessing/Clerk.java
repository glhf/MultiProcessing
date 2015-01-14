package com.glhf.multiprocessing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class Clerk {
    private static final Logger LOG = LogManager.getLogger(ImgEngine.class);

    final BufferedImage img;
    final BufferedImage out;

    Clerk(BufferedImage img, BufferedImage out) {
        this.img = img;
        this.out = out;
    }

    /**
     * divide indexes of image for calculating with n process
     */
    public List<Runnable> start() {
        List<Runnable> tasks = new LinkedList<>();
        java.util.List<Point> list = new LinkedList<>();
        list = getBounds(img.getHeight(), Runtime.getRuntime().availableProcessors());
        list.forEach(el -> {
            LOG.info(el.toString());
            tasks.add(new Thread() {
                public void run() {
                    LOG.info("start thread " + el.x + " " + el.y + " " + img.getWidth());
                    for (int i = 0; i < img.getWidth(); i++) {
                        for (int j = el.x; j < el.y; j++) {
                            out.setRGB(i, j, new Data(i, j, new Color(img.getRGB(i, j))).getGray().getRGB());
                        }
                    }
                }
            });
        });
        return tasks;
    }

    private java.util.List<Point> getBounds(int maxIndexValue, int count) {
        java.util.List<Point> list = new LinkedList<>();
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
                list.add(new Point(start, end));
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
