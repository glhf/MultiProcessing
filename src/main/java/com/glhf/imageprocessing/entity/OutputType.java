package com.glhf.imageprocessing.entity;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 16.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public enum OutputType {
    JPG {
        @Override
        public String toString() {
            return "jpg";
        }
    },

    BMP {
        @Override
        public String toString() {
            return "bmp";
        }
    },

    PNG {
        @Override
        public String toString() {
            return "png";
        }
    }

}
