package com.glhf.imageprocessing;

import com.glhf.imageprocessing.gui.swing.MainStageView;
import com.glhf.imageprocessing.settings.Settings;
import com.glhf.imageprocessing.settings.SettingsFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 15.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class Main {
    private final static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //init application settings
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            LOG.error("Look&Feel error!", ex);
        }
        Settings settings = null;
        try {
            settings = SettingsFactory.loadSettings("settings.xml");
        } catch (JAXBException ex) {
            LOG.error("JAXB exception!", ex);
        } catch (IOException ex) {
            LOG.error("IO exception!", ex);
        }

        // enter to main window
        new MainStageView(settings).setVisible(true);
    }
}