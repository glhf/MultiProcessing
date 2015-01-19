package com.glhf.imageprocessing.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
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
        Data[] datas = new Data[inImage.getHeight() * inImage.getWidth()];
        int k = 0;
        for (int i = 0; i < inImage.getWidth(); i++) {
            for (int j = 0; j < inImage.getHeight(); j++) {
                datas[k] = new Data(i, j, new Color(inImage.getRGB(i, j)));
                k++;
            }
        }
        System.out.println("Fork/Join array data init " + ((System.currentTimeMillis() - start)));
        Worker worker = new Worker(outImage, datas, datas.length / Runtime.getRuntime().availableProcessors() * 16);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(worker);

    }

    class Worker extends RecursiveAction {
        protected int THRESHOLD;

        private BufferedImage outImage;
        private Data[] datas;

        Worker(BufferedImage outImage, Data[] datas, int length) {
            this.outImage = outImage;
            this.datas = datas;
            this.THRESHOLD = length;
        }

        private void computeDirectly() {
            long start = System.currentTimeMillis();
            Arrays.asList(this.datas).forEach(el -> outImage.setRGB(el.getX(), el.getY(), el.getGray().getRGB()));
            System.out.println("Fork/Join array.aslist " + ((System.currentTimeMillis() - start)));
        }

        @Override
        protected void compute() {
            if (datas.length <= THRESHOLD) {
                computeDirectly();
            } else {
                int split = datas.length / 2;
                Data[] d1 = new Data[split];
                Data[] d2 = new Data[split];
                System.arraycopy(datas, 0, d1, 0, d1.length);
                System.arraycopy(datas, d1.length, d2, 0, d2.length);
                invokeAll(new Worker(this.outImage, d1, THRESHOLD), new Worker(this.outImage, d2, THRESHOLD));
            }
        }
    }
}
