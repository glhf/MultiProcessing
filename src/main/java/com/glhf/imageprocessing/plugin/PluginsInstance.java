package com.glhf.imageprocessing.plugin;

import com.glhf.imageprocessing.plugin.api.ImageProcessor;
import com.glhf.imageprocessing.plugin.api.ImageProcessorConfigObservable;
import com.glhf.imageprocessing.plugin.xml.PluginType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
    private final static Logger LOG = LogManager.getLogger(PluginsInstance.class);

    private String pluginName;
    private ImageProcessorConfigObservable imageProcessorConfig;
    private ImageProcessor imageProcessor;

    /**
     * Creating instance of plugin from the class that is in current class path
     *
     * @param pluginType information about plugin loaded from plugins.xml config file for creating instance
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public PluginsInstance(PluginType pluginType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.pluginName = pluginType.getName();
        Class classTemp = Class.forName(pluginType.getAccessClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessor.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessor = (ImageProcessor) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessor.class);
        }

        classTemp = this.getClass().getClassLoader().loadClass(pluginType.getPanelClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessorConfigObservable.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessorConfig = (ImageProcessorConfigObservable) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessorConfigObservable.class);
        }

        this.imageProcessorConfig.addConfigObserver(this.getImageProcessor());
        LOG.info(this.imageProcessorConfig.getClass() + "add to list " + this.imageProcessor.getClass());
    }

    /**
     * Creating instance of plugin with URLClassLoader class and with using directory path as parameter
     *
     * @param pluginType information about plugin loaded from plugins.xml config file for creating instance
     * @param directory  directory that would using as classpath for URLClassLoader
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws MalformedURLException  from directory parameter created instance of URL with java.io.File.
     */
    public PluginsInstance(PluginType pluginType, String directory) throws ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException {
        if (directory == null) {
            throw new NullPointerException("Directory var not set");
        }
        this.pluginName = pluginType.getName();
        URLClassLoader loader = new URLClassLoader(new URL[]{new File(directory).toURI().toURL()});
        LOG.info(loader.loadClass("BinarizationFilter").getCanonicalName());
        Class classTemp = loader.loadClass(pluginType.getAccessClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessor.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessor = (ImageProcessor) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessor.class);
        }

        classTemp = loader.loadClass(pluginType.getPanelClassName());
        if (Arrays.asList(classTemp.getInterfaces()).contains(ImageProcessorConfigObservable.class)) {
            Object objectInstace = classTemp.newInstance();
            this.imageProcessorConfig = (ImageProcessorConfigObservable) objectInstace;
        } else {
            throw new ClassCastException("Not " + ImageProcessorConfigObservable.class);
        }
        try {
            loader.close();
        } catch (IOException e) {
            LOG.error("URL Loader closing error!", e);
        }
        this.imageProcessorConfig.addConfigObserver(this.getImageProcessor());
        LOG.info(this.imageProcessorConfig.getClass() + "add to list " + this.imageProcessor.getClass());
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
