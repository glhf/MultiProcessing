package com.glhf.imageprocessing.interfaces;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 16.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface ImageEngine {


    /**
     * Method for load image from file
     */
    public void read(String path);

    public void read();

    /**
     * Method that must do some calculating with image
     */
    public void convert();

    /**
     * Method for write image to file
     */
    public void write();
}
