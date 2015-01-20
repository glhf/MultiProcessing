package com.glhf.imageprocessing;

import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.entity.filters.GrayscaleFilterElemet;
import com.glhf.imageprocessing.implementation.ImageEngineBaseThreadImplementation;

import com.glhf.imageprocessing.implementation.ImageEngineForkJoinImplementation;
import com.glhf.imageprocessing.implementation.ImageEngineThreadPoolImplementation;

import com.glhf.imageprocessing.implementation.NonThreadImplementation;
import com.glhf.imageprocessing.interfaces.ImageEngine;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testBaseImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("png-img.png");
        ImageEngine eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "1base", OutputType.PNG, new GrayscaleFilterElemet());
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Base " + ((System.currentTimeMillis() - start)));
        eng.write();
    }

    @Test
    public void testPoolImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("png-img.png");
        ImageEngine eng = new ImageEngineThreadPoolImplementation(inputFullPath.toString(), "1pool", OutputType.PNG, new GrayscaleFilterElemet());
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Pool " + ((System.currentTimeMillis() - start)));
        eng.write();
    }

    @Test
    public void testForkJoinImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("png-img.png");
        ImageEngine eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "1join", OutputType.PNG, new GrayscaleFilterElemet());
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Fork/Join " + ((System.currentTimeMillis() - start)));
        eng.write();
    }

    @Test
    public void testNonThreadImplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("png-img.png");
        ImageEngine eng = new NonThreadImplementation(inputFullPath.toString(), "1momthread", OutputType.PNG);
        eng.read();
        long start = System.currentTimeMillis();
        eng.convert();
        System.out.println("Nonthread " + ((System.currentTimeMillis() - start)));
        eng.write();
    }
}
