package com.glhf.imageprocessing;

import com.glhf.imageprocessing.gui.swing.MainStage;
import org.junit.Test;

/**
 * @author Mykola Polonskyi
 *         on 23.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class GUITest {

    @Test
    public void mainStageTest() {
        ((Runnable) () -> new MainStage().setVisible(true)).run();
    }
}
