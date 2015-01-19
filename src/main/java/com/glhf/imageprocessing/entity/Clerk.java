package com.glhf.imageprocessing.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
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
    private static final Logger LOG = LogManager.getLogger(Clerk.class);

    final BufferedImage img;
    final BufferedImage out;
    final int countOfTasks;

    public Clerk(BufferedImage img, BufferedImage out, int countOfTasks) {
        this.img = img;
        this.out = out;
        this.countOfTasks = countOfTasks;
    }

    /**
     * divide indexes of image for calculating with n process
     */
    public List<Thread> getTaskList() {
        List<Thread> tasks = new LinkedList<>();
        List<Point> list = getBounds(this.img.getHeight(), this.countOfTasks);
        list.forEach(el -> tasks.add(new Thread("thread #" + tasks.size()) {
            public void run() {
                Data tmp = new Data();
                LOG.debug("start thread " + el.x + " " + el.y + " " + img.getWidth());
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = el.x - 1; j < el.y; j++) {
                        out.setRGB(i, j, tmp.getGray(img.getRGB(i, j)));
                    }
                }
                LOG.debug("finished thread!");
            }
        }));
        return tasks;
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
                list.add(new Point((int) ((diff * i) + 1), (int) (diff * (i + 1))));
            }
        } else {
            int d = (int) diff;
            start = 0;
            end = d;
            while (end < x) {
                //put hear thread init
                list.add(new Point(start, end));
                start += d;
                end += d;
            }
            end = (int) x;
            list.add(new Point(start, end));
        }
        return list;
    }

}
