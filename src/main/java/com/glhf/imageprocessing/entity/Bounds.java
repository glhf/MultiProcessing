package com.glhf.imageprocessing.entity;

/**
 * For creating tasks
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class Bounds {

    private int start = 0;
    private int end = 0;

    public Bounds(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
