package com.glhf.imageprocessing.plugin.example;

import com.glhf.imageprocessing.plugin.api.ConfigObserver;
import com.glhf.imageprocessing.plugin.api.ImageProcessorConfigObservable;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Mykola Polonskyi
 *         on 28.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class BinarizationPanel extends JPanel implements ImageProcessorConfigObservable {
    private List<ConfigObserver> observerList = new LinkedList<>();
    private JLabel thresholdLabel = new JLabel("Threshold: ");
    private JTextField threshold = new JTextField();

    public BinarizationPanel() {
        initComponents();
    }

    private void initComponents() {
        add(thresholdLabel);
        threshold.setText("double value");
        threshold.addActionListener(el -> notifyObservers());
        add(threshold);
    }


    @Override
    public void addConfigObserver(ConfigObserver listener) {
        observerList.add(listener);
    }

    @Override
    public void notifyObservers() {
        observerList.forEach(el -> el.update(getParameters()));
    }

    private Map<String, String> getParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("threshold", threshold.getText().toString());
        return map;
    }
}
