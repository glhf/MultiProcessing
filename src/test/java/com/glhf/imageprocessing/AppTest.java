package com.glhf.imageprocessing;

import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.implementation.ImageEngineBaseThreadImplementation;
import com.glhf.imageprocessing.implementation.ImageEngineForkJoinImplementation;
import com.glhf.imageprocessing.implementation.ImageEngineThreadPoolImplementation;
import org.junit.Test;

import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testBaseImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineBaseThreadImplementation eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
    }

    @Test
    public void testPoolImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineThreadPoolImplementation eng = new ImageEngineThreadPoolImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
    }

    @Test
    public void testForkJoinImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineForkJoinImplementation eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
    }
}
