package com.glhf.imageprocessing.implementation;

import com.glhf.imageprocessing.clerks.NonThreadClerk;
import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.interfaces.ImageEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by
 *
 * @author goodvin Mykola Polonskyi
 *         on 20.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class NonThreadImplementation implements ImageEngine {
    private static final Logger LOG = LogManager.getLogger(ImageEngineThreadPoolImplementation.class);

    private String inputPath;
    private String outputPath;
    private OutputType outputType = OutputType.JPG;
    private BufferedImage inImage;
    private BufferedImage outImage;


    public NonThreadImplementation() {
    }

    public NonThreadImplementation(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public NonThreadImplementation(String inputPath, String outputPath, OutputType type) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.outputType = type;
    }


    @Override
    public void read(String path) {
        try {
            URL pathURL = new URL(path);
            this.inImage = ImageIO.read(pathURL);
            this.outImage = new BufferedImage(this.inImage.getWidth(), this.inImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        } catch (MalformedInputException e) {
            LOG.error("Invalid path!", e);
        } catch (IOException e) {
            LOG.error("Image load error ", e);
        }
    }

    @Override
    public void read() {
        read(this.inputPath);
    }


    @Override
    public void convert() {
        NonThreadClerk clerk = new NonThreadClerk(inImage, outImage);
        clerk.computeImage();
        this.outImage.setRGB(0, 0, inImage.getWidth(), inImage.getHeight(), clerk.getPixelsArray(), 0, inImage.getWidth());
    }

    @Override
    public void write() {
        try {
            File output = new File(new StringBuffer().append(this.outputPath).append("TPE.").append(outputType.toString()).toString());
            LOG.debug(output.getAbsolutePath());
            ImageIO.write(this.outImage, outputType.toString(), output);
        } catch (IOException e) {
            LOG.error("Image save error ", e);
        }
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
