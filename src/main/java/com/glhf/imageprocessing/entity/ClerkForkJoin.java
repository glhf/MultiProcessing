package com.glhf.imageprocessing.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class ClerkForkJoin {
    private static final Logger LOG = LogManager.getLogger(Clerk.class);

    private BufferedImage inImage;
    private BufferedImage outImage;


    public ClerkForkJoin(BufferedImage in, BufferedImage out, int countOfTasks) {
        this.inImage = in;
        this.outImage = out;
    }

    /**
     * divide indexes of image for calculating with n process
     */
    public void computeImage() {
        //out.setRGB(i, j, new Data(i, j, new Color(img.getRGB(i, j))).getGray().getRGB())l
        long start = System.currentTimeMillis();
        int[] datas = inImage.getRGB(inImage.getMinX(), inImage.getMinY(), inImage.getWidth(), inImage.getHeight(), null, 0, inImage.getWidth()); //new int[inImage.getHeight() * inImage.getWidth()];

        System.out.println("Fork/Join array data init " + ((System.currentTimeMillis() - start)));

        Worker worker = new Worker(outImage, datas, datas.length / Runtime.getRuntime().availableProcessors() * 16);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(worker);

        outImage.setRGB(0, 0, inImage.getWidth(), inImage.getHeight(), datas, 0, inImage.getWidth());
    }

    class Worker extends RecursiveAction {
        protected int THRESHOLD;

        private BufferedImage outImage;
        private int[] datas;

        Worker(BufferedImage outImage, int[] datas, int length) {
            this.outImage = outImage;
            this.datas = datas;
            this.THRESHOLD = length;
        }

        private void computeDirectly() {
            Data tmp = new Data();
            long start = System.currentTimeMillis();
            for (int i = 0; i < this.datas.length; i++) {
                this.datas[i] = tmp.getGray(this.datas[i]);
            }
            System.out.println("Fork/Join array.aslist " + ((System.currentTimeMillis() - start)));
        }

        @Override
        protected void compute() {
            if (datas.length <= THRESHOLD) {
                computeDirectly();
            } else {
                int split = datas.length / 2;
                int[] d1 = new int[split];
                int[] d2 = new int[split];
                System.arraycopy(datas, 0, d1, 0, d1.length);
                System.arraycopy(datas, d1.length, d2, 0, d2.length);
                invokeAll(new Worker(this.outImage, d1, THRESHOLD), new Worker(this.outImage, d2, THRESHOLD));
            }
        }
    }
}
