package com.glhf.imageprocessing;

import com.glhf.imageprocessing.processing.nondepend.entity.OutputType;
import com.glhf.imageprocessing.processing.nondepend.entity.filters.BinarizationFilterElement;
import com.glhf.imageprocessing.processing.nondepend.entity.filters.GrayscaleFilterElemet;
import com.glhf.imageprocessing.processing.nondepend.implementation.ImageEngineBaseThreadImplementation;

import com.glhf.imageprocessing.processing.nondepend.implementation.ImageEngineForkJoinImplementation;
import com.glhf.imageprocessing.processing.nondepend.implementation.ImageEngineThreadPoolImplementation;

import com.glhf.imageprocessing.processing.nondepend.implementation.NonThreadImplementation;
import com.glhf.imageprocessing.processing.nondepend.interfaces.ImageEngine;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

/**
 * Unit test for simple App.
 */
@Ignore
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
        ImageEngine eng = new ImageEngineForkJoinImplementation(inputFullPath.toString(), "111join", OutputType.PNG, new BinarizationFilterElement());
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
