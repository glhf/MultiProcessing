package com.glhf.imageprocessing.entity.filters;

import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;

import java.awt.*;

/**
 * Element that using for pixels processing with filters that
 * implements processing in which every single output pixel depends
 * only from appropriate input pixel.
 * <p/>
 * Implements OnePixelDependFilter
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 *         <p/>
 *         <p/>
 *         Color c = new Color(image.getRGB(j, i));
 *         int red = (int)(c.getRed() * 0.299);
 *         int green = (int)(c.getGreen() * 0.587);
 *         int blue = (int)(c.getBlue() *0.114);
 *         Color newColor = new Color(red+green+blue, red+green+blue,red+green+blue);
 *         <p/>
 *         image.setRGB(j,i,newColor.getRGB());
 */
public class GrayscaleFilterElemet implements OnePixelDependFilter {

    private int x = 0;
    private int y = 0;
    private int color;
    private double rCoefficient = 0.299;
    private double gCoefficient = 0.587;
    private double bCoefficient = 0.114;

    public GrayscaleFilterElemet() {
    }

    public GrayscaleFilterElemet(int colorRGB) {
        this.color = colorRGB;
    }

    public GrayscaleFilterElemet(int x, int y, int colorRGB) {
        this.x = x;
        this.y = y;
        this.color = colorRGB;
    }

    public GrayscaleFilterElemet(int x, int y, int colorRGB, double rCoefficient, double gCoefficient, double bCoefficient) {
        this.x = x;
        this.y = y;
        this.color = colorRGB;
        this.rCoefficient = rCoefficient;
        this.gCoefficient = gCoefficient;
        this.bCoefficient = bCoefficient;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public int processing() {
        Color c = new Color(this.color);
        int red = (int) (c.getRed() * rCoefficient);
        int green = (int) (c.getGreen() * gCoefficient);
        int blue = (int) (c.getBlue() * bCoefficient);
        int rez = red + green + blue;
        return new Color(rez, rez, rez).getRGB();
    }

    @Override
    public int processing(int rgb) {
        Color c = new Color(rgb);
        int red = (int) (c.getRed() * rCoefficient);
        int green = (int) (c.getGreen() * gCoefficient);
        int blue = (int) (c.getBlue() * bCoefficient);
        int rez = red + green + blue;
        return new Color(rez, rez, rez).getRGB();
    }
}
