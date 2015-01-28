package com.glhf.imageprocessing.plugin.example;

import com.glhf.imageprocessing.plugin.api.ImageProcessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Filter for binarization input rgbINT value with input threshold
 *
 * @author Mykola Polonskyi
 *         on 21.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class BinarizationFilter implements ImageProcessor {
    final Color black = Color.BLACK;
    final Color white = Color.WHITE;
    private double rCoefficient = 0.299;
    private double gCoefficient = 0.587;
    private double bCoefficient = 0.114;

    private double threshold = 0.22;

    public BinarizationFilter() {

    }

    private int processing(int color) {
        Color colorTemp = new Color(color);
        double sum = ((colorTemp.getBlue() * bCoefficient + colorTemp.getRed() * rCoefficient + colorTemp.getGreen() * gCoefficient) / 3) / 255;
        if (sum < this.threshold) {
            return black.getRGB();
        } else {
            return white.getRGB();
        }
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
        this.threshold = Double.parseDouble(map.get("threshold"));
    }
}
