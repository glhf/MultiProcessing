package com.glhf.imageprocessing.entity;

import java.awt.*;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class Data {

    Point point = new Point(0, 0);
    Color color;

    public Data(int x, int y, Color color) {
        this.point.setLocation(x, y);
        this.color = color;
    }

    public Data(Color color) {
        this.color = color;
    }

    public int getX() {
        return (int) this.point.getX();
    }

    public int getY() {
        return (int) this.point.getY();
    }

    public Color getGray() {
        int red = (int) (this.color.getRed() * 0.299);
        int green = (int) (this.color.getGreen() * 0.587);
        int blue = (int) (this.color.getBlue() * 0.114);
        return new Color(red + green + blue, red + green + blue, red + green + blue);
    }
}
