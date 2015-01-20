package com.glhf.imageprocessing.entity.filters;

import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;

/**
 * Element that using for pixels processing with filters that
 * implements processing in which every single output pixel depends
 * only from appropriate input pixel.
 *
 * Implements OnePixelDependFilter
 *
 * @author goodvin Mykola Polonskyi
 *         on 14.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class GrayscaleFilterElemet implements OnePixelDependFilter {

    private int x = 0;
    private int y = 0;
    private int color;

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public int processing() {
        return (int) (((this.color >> 16) & 0x00ff0000) * 0.299 + ((this.color >> 8) & 0x0000ff00) * 0.587 + ((this.color) & 0x000000ff) * 0.114);
    }

    @Override
    public int processing(int rgb) {
        return (int) (((rgb >> 16) & 0xff0000) * 0.299 + ((rgb >> 8) & 0x0000ff00) * 0.587 + ((rgb) & 0x000000ff) * 0.114);
    }
}
