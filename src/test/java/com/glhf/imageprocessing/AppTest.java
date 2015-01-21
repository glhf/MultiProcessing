package com.glhf.imageprocessing;

import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.entity.filters.BinarizationFilter;
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
    @Ignore
    @Test
    public void testBaseImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngine eng = new ImageEngineBaseThreadImplementation(inputFullPath.toString(), "1base", OutputType.PNG, new GrayscaleFilterElemet());
        eng.read();
        long times = 0;
        // for (int i = 0; i < 15; i++) {
            long start = System.currentTimeMillis();
            eng.convert();
            times += (System.currentTimeMillis() - start);
        // }
        System.out.println("Base = " + times / 15);
        eng.write();
    }

    @Ignore
    @Test
    public void testPoolImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngine eng = new ImageEngineThreadPoolImplementation(inputFullPath.toString(), "1pool", OutputType.PNG, new GrayscaleFilterElemet());
        eng.read();
        long times = 0;
        //for (int i = 0; i < 15; i++) {
            long start = System.currentTimeMillis();
            eng.convert();
            times += (System.currentTimeMillis() - start);
        //}
        System.out.println("TPE = " + (times / 15));
        eng.write();
    }

    @Test
    public void testForkJoinImoplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngine eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "111join", OutputType.PNG, new BinarizationFilter());
        eng.read();
        long times = 0;
        // for (int i = 0; i < 15; i++) {
            long start = System.currentTimeMillis();
        eng.convert();
            times += (System.currentTimeMillis() - start);
        // }
        System.out.println("FORK JOIN = " + times / 15);
        eng.write();
    }

    @Ignore
    @Test
    public void testNonThreadImplementation() {
        URL inputFullPath = AppTest.class.getClassLoader().getResource("bmp-img.bmp");
        ImageEngine eng = new NonThreadImplementation(inputFullPath.toString(), "1momthread", OutputType.PNG);
        eng.read();
        long times = 0;
        // for (int i = 0; i < 15; i++) {
            long start = System.currentTimeMillis();
        eng.convert();
            times += (System.currentTimeMillis() - start);
        // }
        System.out.println("NON thread = " + times / 15);
        eng.write();
    }
}
