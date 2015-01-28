package com.glhf.imageprocessing.plugin.example;

import com.glhf.imageprocessing.plugin.api.ImageProcessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

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
public class GrayscaleFilter implements ImageProcessor {

    private double rCoefficient = 0.299;
    private double gCoefficient = 0.587;
    private double bCoefficient = 0.114;

    public GrayscaleFilter() {

    }

    private int processing(int rgb) {
        Color c = new Color(rgb);
        int red = (int) (c.getRed() * this.rCoefficient);
        int green = (int) (c.getGreen() * this.gCoefficient);
        int blue = (int) (c.getBlue() * this.bCoefficient);
        int rez = red + green + blue;
        return new Color(rez, rez, rez).getRGB();
    }

    @Override
    public BufferedImage process(BufferedImage in) {
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), in.getType());
        for (int i = 0; i < in.getWidth(); i++) {
            for (int j = 0; j < in.getHeight(); j++) {
                out.setRGB(i, j, processing(in.getRGB(i, j)));
            }
        }
        return out;
    }

    @Override
    public void update(Map<String, String> map) {
        this.rCoefficient = Double.parseDouble(map.get("rCoefficient"));
        this.gCoefficient = Double.parseDouble(map.get("gCoefficient"));
        this.bCoefficient = Double.parseDouble(map.get("bCoefficient"));
    }
}
