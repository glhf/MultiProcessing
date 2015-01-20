package com.glhf.imageprocessing.interfaces;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface Clerkable {

    /**
     * method for implement computing
     *
     * @return
     */
    public void computeImage();

    /**
     * get array with color values
     *
     * @return
     */
    public int[] getPixelsArray();
}
