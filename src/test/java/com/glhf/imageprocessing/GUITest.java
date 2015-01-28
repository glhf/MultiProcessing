package com.glhf.imageprocessing;

import com.glhf.imageprocessing.gui.swing.MainStageView;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mykola Polonskyi
 *         on 23.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class GUITest {

    @Ignore
    @Test
    public void mainStageTest() {
        ((Runnable) () -> new MainStageView().setVisible(true)).run();
    }
}
