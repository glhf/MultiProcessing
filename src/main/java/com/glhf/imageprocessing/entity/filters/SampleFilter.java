package com.glhf.imageprocessing.entity.filters;

import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class SampleFilter implements OnePixelDependFilter {

    @Override
    public int processing() {
        return 0;
    }

    @Override
    public int processing(int color) {
        return 0;
    }
}
