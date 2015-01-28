package com.glhf.imageprocessing.plugin;

import com.glhf.imageprocessing.plugin.api.ImageProcessor;
import com.glhf.imageprocessing.plugin.api.ImageProcessorConfigObservable;
import com.glhf.imageprocessing.plugin.xml.PluginType;

/**
 * Class for warp plugin elements like
 * -name
 * -panel class that implement ImageProcessorConfigObservable
 * -processor class
 * <p/>
 * Inner classes must be created from loaded plugin classes
 *
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class PluginsInstance {
    private String pluginName;
    private ImageProcessorConfigObservable imageProcessorConfig;
    private ImageProcessor imageProcessor;

    public PluginsInstance(PluginType pt) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.pluginName = pt.getName();
        Class ip = Class.forName(pt.getAccessClassName());
        Object ob = ip.newInstance();
        this.imageProcessor = (ImageProcessor) ob;

        Class ipc = this.getClass().getClassLoader().loadClass(pt.getPanelClassName());
        Object obc = ipc.newInstance();
        this.imageProcessorConfig = (ImageProcessorConfigObservable) obc;
        this.imageProcessorConfig.addConfigObserver(this.getImageProcessor());
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }

    public void setImageProcessor(ImageProcessor imageProcessor) {
        this.imageProcessor = imageProcessor;
    }

    public ImageProcessorConfigObservable getImageProcessorConfig() {
        return imageProcessorConfig;
    }

    public void setImageProcessorConfig(ImageProcessorConfigObservable imageProcessorConfig) {
        this.imageProcessorConfig = imageProcessorConfig;
    }
}
