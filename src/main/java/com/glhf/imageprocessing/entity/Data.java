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

    private int x = 0;
    private int y = 0;
    private int color;

    public Data() {

    }

    public Data(int x, int y, int colorRGB) {
        this.x = x;
        this.y = y;
        this.color = colorRGB;
    }

    public Data(int colorRGB) {
        this.color = colorRGB;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getGray() {
        return (int) (((this.color >> 16) & 0x00ff0000) * 0.299 + ((this.color >> 8) & 0x0000ff00) * 0.587 + ((this.color) & 0x000000ff) * 0.114);
    }

    public static int getGray(int rgb) {
        return (int) (((rgb >> 16) & 0xff0000) * 0.299 + ((rgb >> 8) & 0x0000ff00) * 0.587 + ((rgb) & 0x000000ff) * 0.114);
    }
}
