package com.glhf.imageprocessing.implementation;

import com.glhf.imageprocessing.entity.Clerk;
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
import java.util.List;

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

    private String outputPath;
    private OutputType outputType = OutputType.JPG;
    private BufferedImage inImage;
    private BufferedImage outImage;

    public ImageEngineForkJoinImplementation() {
    }

    public ImageEngineForkJoinImplementation(String inputPath, String outputPath) {
        this.read(inputPath);
        this.outImage = new BufferedImage(this.inImage.getWidth(), this.inImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.outputPath = outputPath;
    }

    public ImageEngineForkJoinImplementation(String inputPath, String outputPath, OutputType type) {
        this.read(inputPath);
        this.outImage = new BufferedImage(this.inImage.getWidth(), this.inImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.outputPath = outputPath;
        this.outputType = type;
    }

    @Override
    public void read(String path) {
        try {
            URL pathURL = new URL(path);
            this.inImage = ImageIO.read(pathURL);
        } catch (MalformedInputException e) {
            LOG.error("Invalid path!", e);
        } catch (IOException e) {
            LOG.error("Image load error ", e);
        }
    }

    @Override
    public void convert() {
        Clerk clerk = new Clerk(this.inImage, this.outImage, Runtime.getRuntime().availableProcessors());
        List<Thread> tasks = clerk.getTaskList();
        tasks.forEach(el -> el.start());

        tasks.forEach(el -> {
            try {
                el.join();
            } catch (InterruptedException e) {
                LOG.error("Threads error ", e);
            }
        });

        LOG.debug("wait main fin");
        this.write();
    }

    @Override
    public void write() {
        try {
            File output = new File(new StringBuffer().append(this.outputPath).append(".").append(outputType.toString()).toString());
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
