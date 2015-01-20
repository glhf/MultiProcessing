package com.glhf.imageprocessing.implementation;

import com.glhf.imageprocessing.clerks.ThreadPoolClerk;
import com.glhf.imageprocessing.entity.OutputType;
import com.glhf.imageprocessing.interfaces.Clerkable;
import com.glhf.imageprocessing.interfaces.ImageEngine;
import com.glhf.imageprocessing.interfaces.OnePixelDependFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.List;

/**
 * Convert file with input image-file with path as argument
 * to gray scale image file
 * Implementation multithreading with base thread functions
 */
public class ImageEngineBaseThreadImplementation implements ImageEngine {
    private static final Logger LOG = LogManager.getLogger(ImageEngineBaseThreadImplementation.class);

    private String inputPath;
    private String outputPath;
    private OutputType outputType = OutputType.JPG;
    private BufferedImage inImage;
    private BufferedImage outImage;
    private OnePixelDependFilter filter;

    public ImageEngineBaseThreadImplementation() {
    }

    public ImageEngineBaseThreadImplementation(String inputPath, String outputPath, OnePixelDependFilter filter) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.filter = filter;
    }

    public ImageEngineBaseThreadImplementation(String inputPath, String outputPath, OutputType type, OnePixelDependFilter filter) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.outputType = type;
        this.filter = filter;
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
        Clerkable clerk = new ThreadPoolClerk(this.inImage, this.outImage, Runtime.getRuntime().availableProcessors(), filter);
        clerk.computeImage();
        this.outImage.setRGB(0, 0, inImage.getWidth(), inImage.getHeight(), clerk.getPixelsArray(), 0, inImage.getWidth());
        LOG.debug("wait main fin");
    }

    @Override
    public void write() {
        try {
            File output = new File(new StringBuffer().append(this.outputPath).append("BASE.").append(outputType.toString()).toString());
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
