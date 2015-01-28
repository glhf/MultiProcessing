package com.glhf.imageprocessing.plugin.api;

import java.awt.image.BufferedImage;

/**
 * Subclasses would implements method that do some processing operations with image
 *
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface ImageProcessor extends ConfigObserver {
    /**
     * Method of imageProcessing
     *
     * @param in input buffered image
     * @return output buffered image
     */
    BufferedImage process(BufferedImage in);
}
