package com.glhf.imageprocessing.processing.nondepend.clerks;

import com.glhf.imageprocessing.processing.nondepend.entity.filters.GrayscaleFilterElemet;
import com.glhf.imageprocessing.processing.nondepend.interfaces.Clerkable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class NonThreadClerk implements Clerkable {
    private static final Logger LOG = LogManager.getLogger(ThreadPoolClerk.class);

    final BufferedImage inImage;
    final BufferedImage outImage;
    int[] pixelsArray;

    public NonThreadClerk(BufferedImage inImage, BufferedImage outImage) {
        this.inImage = inImage;
        this.outImage = outImage;
        this.pixelsArray = inImage.getRGB(inImage.getMinX(), inImage.getMinY(), inImage.getWidth(), inImage.getHeight(), null, 0, inImage.getWidth());
    }

    @Override
    public void computeImage() {
        GrayscaleFilterElemet tmp = new GrayscaleFilterElemet();
        for (int i = 0; i < inImage.getWidth(); i++) {
            for (int j = 0; j < inImage.getHeight(); j++) {
                pixelsArray[i + (j * inImage.getWidth())] = tmp.processing(pixelsArray[i + (j * inImage.getWidth())]);
            }
        }
    }

    @Override
    public int[] getPixelsArray() {
        return pixelsArray;
    }
}
