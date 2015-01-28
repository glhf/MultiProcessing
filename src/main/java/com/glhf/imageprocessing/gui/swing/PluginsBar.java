package com.glhf.imageprocessing.gui.swing;

import com.glhf.imageprocessing.plugin.PluginFactory;
import com.glhf.imageprocessing.plugin.PluginsInstance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class PluginsBar extends JPanel {
    MainStageView.ImageUpdater updater;

    private List<PluginsInstance> plugins;

    private JComboBox pluginsNamesList;
    private JPanel processorConfigContainer;
    private JButton applyButton;

    public PluginsBar(MainStageView.ImageUpdater updater) {
        this.updater = updater;
        PluginFactory fac = new PluginFactory("plugins.xml");
        plugins = fac.getPluginsInstanseList();
        System.out.println(plugins.size());
        String[] plugNameList = new String[plugins.size()];

        for (int i = 0; i < plugins.size(); i++) {
            plugNameList[i] = plugins.get(i).getPluginName();
        }

        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(bl);

        pluginsNamesList = new JComboBox(plugNameList);
        //without this code combobox have 1/3 screen place
        Dimension m = pluginsNamesList.getMaximumSize();
        m.height = pluginsNamesList.getPreferredSize().height;
        pluginsNamesList.setMaximumSize(m);
        pluginsNamesList.addActionListener(evt -> comboboxChangeItem(evt));
        //
        this.add(pluginsNamesList);


        processorConfigContainer = new JPanel();
        this.add(processorConfigContainer);

        applyButton = new JButton("Apply");
        applyButton.addActionListener(evt -> buttonClickAction(evt));
        this.add(applyButton);

    }

    public void setPluginsInstances(List<PluginsInstance> instances) {
        this.plugins = instances;
        this.pluginsNamesList.removeAllItems();
        this.plugins.forEach(el -> pluginsNamesList.addItem(el.getPluginName()));
    }

    /**
     * Setting jpanel from plugin instance when combobox change chosen element
     */
    private void setProcessorConfig(JPanel panel) {
        processorConfigContainer.removeAll();
        panel.setPreferredSize(processorConfigContainer.getSize());
        processorConfigContainer.add(panel);
        processorConfigContainer.validate();
        processorConfigContainer.repaint();
    }

    private void buttonClickAction(ActionEvent evt) {
        System.out.println("Button click = " + pluginsNamesList.getSelectedIndex());
        updater.set(plugins.get(pluginsNamesList.getSelectedIndex()).getImageProcessor().process(updater.get()));
    }

    private void comboboxChangeItem(ActionEvent evt) {
        setProcessorConfig((JPanel) plugins.get(pluginsNamesList.getSelectedIndex()).getImageProcessorConfig());

    }
}
