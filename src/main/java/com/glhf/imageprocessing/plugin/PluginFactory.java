package com.glhf.imageprocessing.plugin;

import com.glhf.imageprocessing.plugin.xml.Plugins;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRegistry;
import java.io.*;
import java.net.MalformedURLException;
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
    private static String directory = "";
    private Plugins plugins = new Plugins();

    /**
     * In the constructor we scan directory with plugins
     * All valid plugins adding to the CLASSPATH
     * when program exit remove all of them from CLASSPATH
     */
    public PluginFactory(String path) {
        try {
            LOG.info(path);
            plugins = loadPluginsList(path);
        } catch (IOException e) {
            LOG.info("Load err", e);
        } catch (JAXBException e) {
            LOG.info("JAXB err", e);
        }
    }

    public static Plugins loadPluginsList(String path) throws IOException, JAXBException {
        Reader file = new FileReader(path);

        File f = new File(path);
        if (f.getParentFile().isDirectory()) {
            directory = f.getParent();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(Plugins.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Plugins plugins = (Plugins) jaxbUnmarshaller.unmarshal(file);
        file.close();
        return plugins;
    }

    public List<PluginsInstance> getPluginsInstanseList() {
        List<PluginsInstance> pluginsInstances = new LinkedList<>();
        plugins.getPluginsList().forEach(el -> {
            try {
                pluginsInstances.add(new PluginsInstance(el, directory));
            } catch (MalformedURLException ex) {
                LOG.info("Directory path URL err", ex);
            } catch (ClassNotFoundException ex) {
                LOG.info("Class not found err", ex);
            } catch (IllegalAccessException ex) {
                LOG.info("Load err", ex);
            } catch (InstantiationException ex) {
                LOG.info("Load err", ex);
            } catch (NullPointerException ex) {
                LOG.info("NullPointerException", ex);
            }
        });
        return pluginsInstances;
    }

}
