package com.glhf.imageprocessing;

import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.implementation.ImageEngineBaseThreadImplementation;

import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public void testMain() {
        URL inputFullPath = ImageEngineBaseThreadImplementation.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineBaseThreadImplementation eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
    }

}
