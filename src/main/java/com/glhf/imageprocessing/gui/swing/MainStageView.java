package com.glhf.imageprocessing.gui.swing;

import com.glhf.imageprocessing.settings.Settings;
import com.glhf.imageprocessing.settings.SettingsFactory;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.xml.bind.JAXBException;


/**
 * Class implements features of MVC view part
 * Displaying data, user actions handling
 *
 * @author Mykola Polonskyi
 *         on 27.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class MainStageView extends javax.swing.JFrame {
    private static final Logger LOG = LogManager.getLogger(MainStageView.class);

    private ImageUpdater updater = new ImageUpdater();

    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenu FileMenuItem;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveAsMenu;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JMenuItem exitMenu;

    private StatusBar statusBar;
    private PluginsBar pluginBar;
    private ImageView imageView = new ImageView();

    private Settings settings;

    public MainStageView() {
        initComponents();
    }

    public MainStageView(Settings settings) {
        if (settings == null) {
            LOG.error("No input settings!");
        } else {
            this.settings = settings;
        }
        initComponents();
    }

    private void initComponents() {
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        mainMenuBar = new javax.swing.JMenuBar();
        FileMenuItem = new javax.swing.JMenu();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        saveAsMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ImageProcessing");
        setMinimumSize(new java.awt.Dimension(600, 400));
        setPreferredSize(new java.awt.Dimension(800, 600));

        //creating menuBar component
        FileMenuItem.setText("File");

        openMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenu.setText("Open");
        openMenu.addActionListener(evt -> openMenuActionPerformed(evt));
        FileMenuItem.add(openMenu);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenu.setText("Save");
        saveMenu.addActionListener(evt -> saveMenuActionPerformed(evt));
        FileMenuItem.add(saveMenu);

        saveAsMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        saveAsMenu.setText("Save as...");
        saveAsMenu.addActionListener(evt -> saveAsMenuActionPerformed(evt));
        FileMenuItem.add(saveAsMenu);

        exitMenu.setText("Exit");
        exitMenu.addActionListener(evt -> exitMenuActionPerformed(evt));
        FileMenuItem.add(exitMenu);

        mainMenuBar.add(FileMenuItem);
        setJMenuBar(mainMenuBar);
        //end

        //creating plugin panel
        pluginBar = new PluginsBar(this.updater, this.settings.getPluginsDirectory());
        pluginBar.setPreferredSize(new Dimension((int) (this.getWidth() * .3), this.getHeight()));
        add(pluginBar, BorderLayout.WEST);
        //end


        //creating status bar
        statusBar = new StatusBar();
        statusBar.setPreferredSize(new Dimension(this.getWidth(), (int) (this.getHeight() * .05)));
        add(statusBar, BorderLayout.SOUTH);
        //eng

        //creating viewImageBlock
        JFXPanel imageViewContainer = new JFXPanel();
        add(imageViewContainer, BorderLayout.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(imageViewContainer, imageView);
            }
        });
        //end

        pack();
    }

    //FXcontainer functions block
    /**
     * Initialization swing-container for javafx ImageView class
     *
     * @param fxPanel
     * @param imageView
     */
    private void initFX(JFXPanel fxPanel, ImageView imageView) {
        //initialization of fx elements
        Scene scene = createScene(imageView);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        fxPanel.setScene(scene);
    }

    /**
     * Initialization FX-container for javafx ImageView clas
     * @param imageView
     * @return
     */
    private Scene createScene(ImageView imageView) {
        Group root = new Group();
        Scene scene = new Scene(root);

        // image will be align to the center of scene
        StackPane stack = new StackPane();
        stack.getChildren().add(imageView);

        stack.translateXProperty()
                .bind(scene.widthProperty().subtract(stack.widthProperty())
                        .divide(2));

        stack.translateYProperty()
                .bind(scene.heightProperty().subtract(stack.heightProperty())
                        .divide(2));

        root.getChildren().add(stack);
        return scene;
    }

    /**
     * Method for set images into javafx imageView
     *
     * @param img
     */
    public void setImageToFXContainer(BufferedImage img) {
        this.statusBar.setImageStatusInfo(img);
        ImageView imageOnSwingAppFrame = this.imageView;
        Platform.runLater(() -> imageOnSwingAppFrame.setImage(SwingFXUtils.toFXImage(img, null)));
    }
    //end of FXcontainer functions block

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Load image
        try {
            JFileChooser fileopen = new JFileChooser();
            fileopen.showOpenDialog(this);
            updater.set(ImageIO.read(new File(fileopen.getSelectedFile().getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here: Controller Call for save image
    }

    private void saveAsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // save image
        try {
            JFileChooser fileopen = new JFileChooser();
            fileopen.showSaveDialog(this);
            ImageIO.write(updater.get(), "png", new File(fileopen.getSelectedFile().getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        System.exit(0);
    }

    /**
     * container class for Buffered image that would process in system
     * and it FXcontainer
     */
    public class ImageUpdater {
        BufferedImage img;

        ImageUpdater() {

        }

        ImageUpdater(BufferedImage img) {
            this.img = img;
        }

        public BufferedImage get() {
            return this.img;
        }

        public void set(BufferedImage img) {
            this.img = img;
            setImageToFXContainer(this.img);
        }

    }
}
