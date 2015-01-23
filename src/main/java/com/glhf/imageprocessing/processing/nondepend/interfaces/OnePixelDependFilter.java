package com.glhf.imageprocessing.processing.nondepend.interfaces;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface OnePixelDependFilter {

    /**
     * Implement some processing of int color value that came
     * to class constructor etc. and return int color representation
     *
     * @return
     */
    public int processing();

    /**
     * Default processing method
     * Need to be overrided for each filter subclass
     */
    default int processing(int color) {
        return color;
    }
}