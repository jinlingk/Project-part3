package edu.nyu.lk.cookzilla.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

@Configuration
@PropertySource("classpath:application.yml")
public class ImagesProperties {

    @Value("${images.fileSize}")
    private String fileSize;  // Compressed size

    @Value("${images.scaleRatio}")
    private String scaleRatio; //Compression ratio

    @Value("${images.upPath}")
    private String upPath; //Save Path

    @Value("${images.imageType}")
    private String imageType; //pictures type

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(String scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public String getUpPath() {
        return upPath;
    }

    public void setUpPath(String upPath) {
        this.upPath = upPath;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public ImagesProperties() {
    }
}
