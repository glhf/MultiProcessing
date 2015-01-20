package com.glhf.imageprocessing.clerks;

import com.glhf.imageprocessing.entity.filters.GrayscaleFilterElemet;
import com.glhf.imageprocessing.interfaces.Clerkable;
import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
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
public class ForkJoinClerk implements Clerkable {
    private static final Logger LOG = LogManager.getLogger(ThreadPoolClerk.class);

    private BufferedImage inImage;
    private BufferedImage outImage;
    private int[] pixelsArray;
    private OnePixelDependFilter filter;

    public ForkJoinClerk(BufferedImage in, BufferedImage out, int countOfTasks, OnePixelDependFilter filter) {
        this.inImage = in;
        this.outImage = out;
        this.pixelsArray = inImage.getRGB(inImage.getMinX(), inImage.getMinY(), inImage.getWidth(), inImage.getHeight(), null, 0, inImage.getWidth()); //new int[inImage.getHeight() * inImage.getWidth()];
        this.filter = filter;
    }

    /**
     * divide indexes of image for calculating with n process
     */
    @Override
    public void computeImage() {
        Worker worker = new Worker(pixelsArray, pixelsArray.length / Runtime.getRuntime().availableProcessors() * 16);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(worker);
    }

    @Override
    public int[] getPixelsArray() {
        return pixelsArray;
    }

    class Worker extends RecursiveAction {
        protected int THRESHOLD;

        private int[] pixelsArray;

        Worker(int[] pixelsArray, int length) {
            this.pixelsArray = pixelsArray;
            this.THRESHOLD = length;
        }

        private void computeDirectly() {
            //GrayscaleFilterElemet tmp = new GrayscaleFilterElemet();
            for (int i = 0; i < this.pixelsArray.length; i++) {
                this.pixelsArray[i] = filter.processing(this.pixelsArray[i]);
            }
        }

        @Override
        protected void compute() {
            if (pixelsArray.length <= THRESHOLD) {
                computeDirectly();
            } else {
                int split = pixelsArray.length / 2;
                int[] d1 = new int[split];
                int[] d2 = new int[split];
                System.arraycopy(pixelsArray, 0, d1, 0, d1.length);
                System.arraycopy(pixelsArray, d1.length, d2, 0, d2.length);
                invokeAll(new Worker(d1, THRESHOLD), new Worker(d2, THRESHOLD));
            }
        }

    }
}
