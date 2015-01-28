package com.glhf.imageprocessing.plugin.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "plugin"
})
@XmlRootElement(name = "plugins", namespace = "http://www.github.com/glhf/plugins")
public class Plugins {

    protected List<PluginType> plugin;

    public List<PluginType> getPluginsList() {
        if (plugin == null) {
            plugin = new ArrayList<PluginType>();
        }
        return this.plugin;
    }

}
