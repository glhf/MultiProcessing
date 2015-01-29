package com.glhf.imageprocessing.plugin.example;

import com.glhf.imageprocessing.plugin.api.ConfigObserver;
import com.glhf.imageprocessing.plugin.api.ImageProcessorConfigObservable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class GrayscalePanelOld extends JPanel implements ImageProcessorConfigObservable {
    private final static Logger LOG = LogManager.getLogger(GrayscalePanelOld.class);

    private List<ConfigObserver> observerList = new LinkedList<>();
    private JLabel rCoefficientLabel = new JLabel("Red Coefficient: ");
    private JLabel gCoefficientLabel = new JLabel("Green Coefficient: ");
    private JLabel bCoefficientLabel = new JLabel("Blue Coefficient: ");
    private JTextField rCoefficient = new JTextField("0.299");
    private JTextField gCoefficient = new JTextField("0.587");
    private JTextField bCoefficient = new JTextField("0.114");

    public GrayscalePanelOld() {
        JPanel rPanel = new JPanel();
        rPanel.add(rCoefficientLabel);
        rPanel.add(rCoefficient);
        rCoefficient.addActionListener(el -> notifyObservers());
        JPanel gPanel = new JPanel();
        gPanel.add(gCoefficientLabel);
        gPanel.add(gCoefficient);
        gCoefficient.addActionListener(el -> notifyObservers());
        JPanel bPanel = new JPanel();
        bPanel.add(bCoefficientLabel);
        bPanel.add(bCoefficient);
        bCoefficient.addActionListener(el -> notifyObservers());
        BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(bl);
        this.add(rPanel);
        this.add(gPanel);
        this.add(bPanel);
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
        LOG.info("send update r = " + rCoefficient + "g = " + gCoefficient + "b = " + bCoefficient);
        Map<String, String> map = new HashMap<String, String>();
        map.put("rCoefficient", rCoefficient.getText().toString());
        map.put("gCoefficient", gCoefficient.getText().toString());
        map.put("bCoefficient", bCoefficient.getText().toString());
        return map;
    }
}
