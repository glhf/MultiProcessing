package com.glhf.imageprocessing.entity.filters;

import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;

import java.awt.*;

/**
 * Filter for binarization input rgbINT value with input threshold
 *
 * @author Mykola Polonskyi
 *         on 21.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class BinarizationFilter implements OnePixelDependFilter {
    final Color black = Color.BLACK;
    final Color white = Color.WHITE;
    private double rCoefficient = 0.299;
    private double gCoefficient = 0.587;
    private double bCoefficient = 0.114;
    private int rgbColor = 0;
    private double threshold = 0.2;
    private int rgbResultColor = 0;

    public BinarizationFilter() {
    }

    public BinarizationFilter(int rgbColor) {
        this.rgbColor = rgbColor;
    }

    public BinarizationFilter(double THRESHOLD) {
        this.threshold = THRESHOLD;
    }

    public BinarizationFilter(int rgbColor, double THRESHOLD) {
        this.rgbColor = rgbColor;
        this.threshold = THRESHOLD;
    }

    @Override
    public int processing() {
        Color colorTemp = new Color(rgbColor);
        double sum = ((colorTemp.getBlue() + colorTemp.getRed() + colorTemp.getGreen()) / 3) / 255;
        if (sum < this.threshold) {
            return black.getRGB();
        } else {
            return white.getRGB();
        }
    }

    @Override
    public int processing(int color) {
        Color colorTemp = new Color(color);
        double sum = ((colorTemp.getBlue() * bCoefficient + colorTemp.getRed() * rCoefficient + colorTemp.getGreen() * gCoefficient) / 3) / 255;
        if (sum < this.threshold) {
            return black.getRGB();
        } else {
            return white.getRGB();
        }
    }
}
