package com.glhf.imageprocessing.clerks;

import com.glhf.imageprocessing.entity.Bounds;
import com.glhf.imageprocessing.entity.filters.GrayscaleFilterElemet;
import com.glhf.imageprocessing.interfaces.Clerkable;
import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class BaseThreadClerk implements Clerkable {
    private static final Logger LOG = LogManager.getLogger(ThreadPoolClerk.class);

    private BufferedImage inImage;
    private BufferedImage outImage;
    private int countOfTasks;
    private OnePixelDependFilter filter;
    private int[] pixelsArray;

    public BaseThreadClerk(BufferedImage inImage, BufferedImage outImage, int countOfTasks, OnePixelDependFilter filter) {
        this.inImage = inImage;
        this.outImage = outImage;
        this.countOfTasks = countOfTasks;
        this.filter = filter;
        this.pixelsArray = inImage.getRGB(inImage.getMinX(), inImage.getMinY(), inImage.getWidth(), inImage.getHeight(), null, 0, inImage.getWidth());
    }


    @Override
    public void computeImage() {
        List<Thread> tasks = getTaskList();
        tasks.forEach(el -> el.start());

        tasks.forEach(el -> {
            try {
                el.join();
            } catch (InterruptedException e) {
                LOG.error("Threads error ", e);
            }
        });
    }

    @Override
    public int[] getPixelsArray() {
        return pixelsArray;
    }

    /**
     * Initiate list of tasks from list if bounds
     */
    private List<Thread> getTaskList() {
        List<Thread> tasks = new LinkedList<>();
        List<Bounds> list = getBounds(this.pixelsArray.length, this.countOfTasks);
        list.forEach(el -> tasks.add(
                new GrayscaleWorker("Thread #" + tasks.size(), el)
        ));
        return tasks;
    }

    /**
     * Method split bounds of from 0 to maxIndexValue in countOfTasks parts;
     * <p/>
     * <p>
     * If image represents as int[1080] and input param countOfTasks=2
     * method will return
     * list ->
     * 0: 1..540
     * 1: 541..1080
     * for processing in for-loop use bound.getStart()-1. it will correctly work when numeration start from 0;
     * </p>
     *
     * @param maxIndexValue
     * @param countOfTasks
     * @return
     */
    private List<Bounds> getBounds(int maxIndexValue, int countOfTasks) {
        List<Bounds> list = new LinkedList<>();
        double n = countOfTasks;
        double x = maxIndexValue;
        double diff = x / n;
        int start = 0;
        int end = 0;
        if ((diff % 1) == 0) {
            for (int i = 0; i < n; i++) { // startIndex = diff*i  endIndex diff*(i+1)
                list.add(new Bounds((int) ((diff * i) + 1), (int) (diff * (i + 1))));
            }
        } else {
            int d = (int) diff;
            start = 0;
            end = d;
            while (end < x) {
                //put hear thread init
                list.add(new Bounds(start, end));
                start += d;
                end += d;
            }
            end = (int) x;
            list.add(new Bounds(start, end));
        }
        return list;
    }


    class GrayscaleWorker extends Thread {

        Bounds bounds;

        //OnePixelDependFilter filteringElemetn = new GrayscaleFilterElemet();
        GrayscaleWorker(String threadName, Bounds bounds) {
            super(threadName);
            this.bounds = bounds;
        }

        @Override
        public void run() {
            LOG.debug("start thread " + bounds.getStart() + " " + bounds.getEnd());
            for (int j = bounds.getStart() - 1; j < bounds.getEnd(); j++) {
                pixelsArray[j] = filter.processing(pixelsArray[j]);//
            }
            LOG.debug("finished thread!");
        }

    }
}