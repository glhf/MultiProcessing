package com.glhf.imageprocessing.settings;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlRegistry;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.github.glhf.settings package.
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
public class SettingsFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github.glhf.settings
     */
    public SettingsFactory() {
    }

    /**
     * Create an instance of {@link Settings }
     */
    public Settings createSettings() {
        return new Settings();
    }

    /**
     * Factory method for unmarshalling settings from disk
     *
     * @param path path to settings file on disk
     */
    public static Settings loadSettings(String path) throws IOException, JAXBException {
        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Settings settings = (Settings) jaxbUnmarshaller.unmarshal(file);
        return settings;
    }

    /**
     * Method for saving settings from program
     * marshall to xml file on disk
     *
     * @param path     path to settings file on disk
     * @param settings settings entity
     */
    public static void saveSettings(String path, Settings settings) throws IOException, JAXBException {
        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(settings, file);
    }

}
