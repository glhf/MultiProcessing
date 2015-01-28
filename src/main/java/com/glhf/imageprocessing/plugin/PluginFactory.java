package com.glhf.imageprocessing.plugin;

import com.glhf.imageprocessing.plugin.xml.Plugins;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRegistry;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.github.glhf package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class PluginFactory {
    private static final Logger LOG = LogManager.getLogger(PluginFactory.class);
    private Plugins plugins = new Plugins();

    /**
     * In the constructor we scan directory with plugins
     * All valid plugins adding to the CLASSPATH
     * when program exit remove all of them from CLASSPATH
     */
    public PluginFactory(String directoryPath) {
        // 1 - scanning directory for jar files with plugins.xml
        try {
            plugins = loadPluginsList(directoryPath);
            LOG.info(plugins.getPluginsList().size());
        } catch (IOException e) {
            LOG.info("Load err", e);
        } catch (JAXBException e) {
            LOG.info("JAXB err", e);
        }
        // 2 - for pluginList in plugins.xml check valid state
        // 3 - for valid list load classes and instance them to controllers
    }

    /**
     * for each jar check including of pluginInfo.xml
     * and matches of JPanel and Processing classes
     * <p/>
     * Files inside JAR:
     * pluginInfo.xml
     * <pluginInfo.xml->accessClassName>.class
     * <pluginInfo.xml->panelClassName>.class
     *
     * @return
     */
    private boolean validPluginJar() {
        if (true) {
            return true;
        }
        return false;
    }


    public static Plugins loadPluginsList(String path) throws IOException, JAXBException {
        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Plugins.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Plugins plugins = (Plugins) jaxbUnmarshaller.unmarshal(file);
        return plugins;
    }

    public List<PluginsInstance> getPluginsInstanseList() {
        List<PluginsInstance> pluginsInstances = new LinkedList<>();
        plugins.getPluginsList().forEach(el -> {
            try {
                pluginsInstances.add(new PluginsInstance(el));
            } catch (ClassNotFoundException e) {
                LOG.info("Load err", e);
            } catch (IllegalAccessException e) {
                LOG.info("Load err", e);
            } catch (InstantiationException e) {
                LOG.info("Load err", e);
            }
        });
        return pluginsInstances;
    }

}
