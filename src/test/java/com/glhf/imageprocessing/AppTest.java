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
        long start = System.currentTimeMillis();

        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineBaseThreadImplementation eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();

        System.out.println("Base " + ((System.currentTimeMillis() - start)));
    }

    @Test
    public void testPoolImoplementation() {
        long start = System.currentTimeMillis();
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineThreadPoolImplementation eng = new ImageEngineThreadPoolImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
        System.out.println("Pool " + ((System.currentTimeMillis() - start)));
    }

    @Test
    public void testForkJoinImoplementation() {
        long start = System.currentTimeMillis();
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineForkJoinImplementation eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "grayscale", OutputType.JPG);
        eng.convert();
        System.out.println("Fork/Join " + ((System.currentTimeMillis() - start)));
    }
}
