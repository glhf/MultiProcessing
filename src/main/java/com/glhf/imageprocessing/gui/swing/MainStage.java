package com.glhf.imageprocessing.gui.swing;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.awt.image.BufferedImageFilter;
import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;

import javax.imageio.ImageIO;

import javax.swing.*;


/**
 *

 */
public class MainStage extends javax.swing.JFrame {
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenu FileMenuItem;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JMenuItem saveAsMenu;
    private javax.swing.JMenuItem saveMenu;

    private StatusBar statusBar;

    private ImageView imageView = new ImageView();

    public MainStage() {
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
        JPanel pluginListContainer = new JPanel();
        pluginListContainer.setBackground(Color.blue);
        pluginListContainer.setPreferredSize(new Dimension((int) (this.getWidth() * .25), this.getHeight()));
        add(pluginListContainer, BorderLayout.WEST);
        //end

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

        //creating status bar
        statusBar = new StatusBar();
        statusBar.setPreferredSize(new Dimension(this.getWidth(), (int) (this.getHeight() * .05)));
        add(statusBar, BorderLayout.SOUTH);
        //eng

        pack();
    }

    private void initFX(JFXPanel fxPanel, ImageView imageView) {
        //initialization of fx elements
        Scene scene = createScene(imageView);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        fxPanel.setScene(scene);
    }

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

    void setImageOnSwingAppFrame(BufferedImage img) {
        this.statusBar.setImageStatusInfo(img);
        ImageView imageOnSwingAppFrame = this.imageView;
        Platform.runLater(() -> imageOnSwingAppFrame.setImage(SwingFXUtils.toFXImage(img, null)));
    }

    private void setImageStatusInfo(BufferedImage img) {
        // TODO create method that will be show information about loading image
    }

    private void setAlgorythmInvokeInformation() {
        // TODO create method that will be show information about algorythm execution
    }

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // Load image 
        try {
            JFileChooser fileopen = new JFileChooser();
            fileopen.showOpenDialog(this);
            BufferedImage img = ImageIO.read(new File(fileopen.getSelectedFile().getAbsolutePath()));
            setImageOnSwingAppFrame(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void saveAsMenuActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        System.exit(0);
    }
}
