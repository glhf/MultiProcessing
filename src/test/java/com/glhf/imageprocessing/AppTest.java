package com.glhf.imageprocessing;

import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.implementation.ImageEngineBaseThreadImplementation;

import com.glhf.imageprocessing.implementation.ImageEngineForkJoinImplementation;
import com.glhf.imageprocessing.implementation.ImageEngineThreadPoolImplementation;

import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testBaseImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineBaseThreadImplementation eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "base", OutputType.PNG);
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Base " + ((System.currentTimeMillis() - start)));
        eng.write();
    }

    @Test
    public void testPoolImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineThreadPoolImplementation eng = new ImageEngineThreadPoolImplementation(inputFullPath.toString(), "pool", OutputType.JPG);
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Pool " + ((System.currentTimeMillis() - start)));
        eng.write();
    }


    @Test
    public void testForkJoinImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngineForkJoinImplementation eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "join", OutputType.PNG);
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Fork/Join " + ((System.currentTimeMillis() - start)));
        eng.write();
    }
}
