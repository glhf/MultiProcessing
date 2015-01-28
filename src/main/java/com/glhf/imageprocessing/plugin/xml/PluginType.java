package com.glhf.imageprocessing.plugin.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pluginType", propOrder = {
        "name",
        "accessClassName",
        "panelClassName"
})
public class PluginType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String accessClassName;
    @XmlElement(required = true)
    protected String panelClassName;

    /**
     * Gets the value of the name property.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the accessClassName property.
     */
    public String getAccessClassName() {
        return accessClassName;
    }

    /**
     * Sets the value of the accessClassName property.
     */
    public void setAccessClassName(String value) {
        this.accessClassName = value;
    }

    /**
     * Gets the value of the panelClassName property.
     */
    public String getPanelClassName() {
        return panelClassName;
    }

    /**
     * Sets the value of the panelClassName property.
     */
    public void setPanelClassName(String value) {
        this.panelClassName = value;
    }

}
