package com.glhf.imageprocessing.plugin;

import com.glhf.imageprocessing.plugin.api.ImageProcessor;
import com.glhf.imageprocessing.plugin.api.ImageProcessorConfigObservable;
import com.glhf.imageprocessing.plugin.xml.PluginType;

import java.util.Arrays;

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
        Class classTemp = Class.forName(pt.getAccessClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessor.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessor = (ImageProcessor) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessor.class);
        }

        classTemp = this.getClass().getClassLoader().loadClass(pt.getPanelClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessorConfigObservable.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessorConfig = (ImageProcessorConfigObservable) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessorConfigObservable.class);
        }

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
