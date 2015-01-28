package com.glhf.imageprocessing.gui.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Mykola Polonskyi
 *         on 23.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class StatusBar extends JPanel {

    private JLabel height;
    private JLabel wigtht;
    private JLabel colorModel;
    private JLabel startMillisecondTime;
    private JLabel endMillisecondTime;
    private JLabel time;

    private JLabel titleImg;
    private JLabel titleHeight;
    private JLabel titleWigtht;
    private JLabel titleColorModel;
    private JLabel titleAlg;
    private JLabel titleStartMillisecondTime;
    private JLabel titleEndMillisecondTime;
    private JLabel titleTime;

    /**
     * base constructor with left align of text information
     */
    public StatusBar() {
        FlowLayout l = new FlowLayout(FlowLayout.LEFT);
        setLayout(l);
        initComponents();
    }

    /**
     * Init status bar base information
     */
    private void initComponents() {
        titleHeight = new JLabel("H:");
        titleWigtht = new JLabel("W:");
        titleColorModel = new JLabel("CM:");
        titleStartMillisecondTime = new JLabel("Start:");
        titleEndMillisecondTime = new JLabel("End:");
        titleTime = new JLabel("Time:");
        height = new JLabel("0");
        wigtht = new JLabel("0");
        colorModel = new JLabel("none");
        startMillisecondTime = new JLabel("0");
        endMillisecondTime = new JLabel("0");
        time = new JLabel("0");
        titleAlg = new JLabel("Algorithm info ");
        titleImg = new JLabel("Img info ");

        this.add(titleImg);
        this.add(titleColorModel);
        this.add(colorModel);
        this.add(titleHeight);
        this.add(height);
        this.add(titleWigtht);
        this.add(wigtht);

        this.add(titleAlg);
        this.add(titleStartMillisecondTime);
        this.add(startMillisecondTime);
        this.add(titleEndMillisecondTime);
        this.add(endMillisecondTime);
        this.add(titleTime);
        this.add(time);
    }

    /**
     * Set information about buffered image into status bar
     *
     * @param img
     */
    public void setImageStatusInfo(BufferedImage img) {
        height.setText(String.valueOf(img.getHeight()));
        wigtht.setText(String.valueOf(img.getWidth()));

        colorModel.setText(castBufferedImageColorMode(img.getType()));

        // set ALG data to default/empty
        startMillisecondTime.setText("0");
        endMillisecondTime.setText("0");
        time.setText("0");
    }

    /**
     * set information about image processing algorithm work time
     * @param s
     * @param e
     * @param d
     */
    public void setAlgorithmWorkInfo(int s, int e, int d) {
        startMillisecondTime.setText("11");
        endMillisecondTime.setText("");

    }

    private String castBufferedImageColorMode(int type) {
        switch (type) {
            case 0:
                return "TYPE_CUSTOM";
            case 1:
                return "TYPE_INT_RGB";
            case 2:
                return "TYPE_INT_ARGB";
            case 3:
                return "TYPE_INT_ARGB_PRE";
            case 4:
                return "TYPE_INT_BGR";
            case 5:
                return "TYPE_3BYTE_BGR";
            case 6:
                return "TYPE_4BYTE_ABGR";
            case 7:
                return "TYPE_4BYTE_ABGR_PRE";
            case 8:
                return "TYPE_USHORT_565_RGB";
            case 9:
                return "TYPE_USHORT_555_RGB";
            case 10:
                return "TYPE_BYTE_GRAY";
            case 11:
                return "TYPE_USHORT_GRAY";
            case 12:
                return "TYPE_BYTE_BINARY";
            case 13:
                return "TYPE_BYTE_INDEXED";
            default:
                return "unknown";
        }
    }
}
