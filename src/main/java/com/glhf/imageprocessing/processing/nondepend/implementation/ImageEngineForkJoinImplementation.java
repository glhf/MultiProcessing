package com.glhf.imageprocessing.processing.nondepend.implementation;

import com.glhf.imageprocessing.processing.nondepend.clerks.ForkJoinClerk;
import com.glhf.imageprocessing.processing.nondepend.entity.OutputType;
import com.glhf.imageprocessing.processing.nondepend.interfaces.Clerkable;
import com.glhf.imageprocessing.processing.nondepend.interfaces.ImageEngine;
import com.glhf.imageprocessing.processing.nondepend.interfaces.OnePixelDependFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Convert file with input image-file with path as argument
 * to gray scale image file
 * Implementation multhitreading with Fork/Join framework
 *
 * @author goodvin Mykola Polonskyi
 *         on 16.01.15
 *         github.com/glhf
 *         goodvin4@gmail.com
 */
public class ImageEngineForkJoinImplementation implements ImageEngine {
    private static final Logger LOG = LogManager.getLogger(ImageEngineBaseThreadImplementation.class);

    private String inputPath;
    private String outputPath;
    private OutputType outputType = OutputType.PNG;
    private BufferedImage inImage;
    private BufferedImage outImage;
    private OnePixelDependFilter filter;

    public ImageEngineForkJoinImplementation() {
    }

    public ImageEngineForkJoinImplementation(String inputPath, String outputPath, OnePixelDependFilter filter) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.filter = filter;
    }

    public ImageEngineForkJoinImplementation(String inputPath, String outputPath, OutputType type, OnePixelDependFilter filter) {
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
            this.outImage = new BufferedImage(this.inImage.getWidth(), this.inImage.getHeight(), BufferedImage.TYPE_INT_RGB);
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
        Clerkable clerk = new ForkJoinClerk(this.inImage, this.outImage, Runtime.getRuntime().availableProcessors(), filter);
        clerk.computeImage();
        this.outImage.setRGB(0, 0, inImage.getWidth(), inImage.getHeight(), clerk.getPixelsArray(), 0, inImage.getWidth());
    }

    @Override
    public void write() {
        try {
            File output = new File(new StringBuffer().append(this.outputPath).append("FKJN.").append(outputType.toString()).toString());
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
