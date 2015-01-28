package com.glhf.imageprocessing.plugin.api;

import java.util.Map;

/**
 * Must implements method for realization Observer pattern
 * <p/>
 * ImageProcessorConfig subclasses would be Observable part representation with JPanelExtends
 *
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface ImageProcessorConfigObservable {

    /**
     * @param listener ConfigObserver instance
     */
    public void addConfigObserver(ConfigObserver listener);

    /**
     * param map options that stored in <String,String> map
     * generated inside of implementation
     */
    public void notifyObservers();
}
