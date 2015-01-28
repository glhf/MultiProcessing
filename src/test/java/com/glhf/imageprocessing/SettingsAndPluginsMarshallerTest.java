package com.glhf.imageprocessing;

import com.glhf.imageprocessing.plugin.PluginFactory;
import com.glhf.imageprocessing.plugin.xml.Plugins;
import com.glhf.imageprocessing.settings.Settings;
import com.glhf.imageprocessing.settings.SettingsFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mykola Polonskyi
 *         on 26.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class SettingsAndPluginsMarshallerTest {

    @Test
    public void marshallerSettingsTest() throws Exception {
        Settings sett = new Settings();
        sett.setPluginsDirectory("./");
        SettingsFactory.saveSettings("settings.xml", sett);
    }

    @Test
    public void unmarshallerSettingsTest() throws Exception {
        Settings settings = SettingsFactory.loadSettings("settings.xml");
        Assert.assertTrue("./".equals(settings.getPluginsDirectory().intern()));
    }

    @Test
    public void marshallerPluginsTest() throws Exception {
        Plugins plugins = PluginFactory.loadPluginsList("plugins.xml");
        Assert.assertEquals(plugins.getPluginsList().size(), 5);
        plugins.getPluginsList().forEach(el -> System.out.println(el.getName()));
    }
}
