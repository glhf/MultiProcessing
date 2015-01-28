package com.glhf.imageprocessing.plugin.api;

import java.util.Map;

/**
 * For updating options map of observers subclass
 *
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public interface ConfigObserver {

    /**
     * @param map map of options that must be updated
     */
    void update(Map<String, String> map);
}
