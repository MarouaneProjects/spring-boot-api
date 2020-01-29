package com.youcode.spring.sbootapi.dtos.response.files;

import com.youcode.spring.sbootapi.models.FileUpload;
import com.youcode.spring.sbootapi.models.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

public class ImageDto {

    private final String path;

    public ImageDto(String filePath) {
        this.path = filePath;
    }

    public static ImageDto build(ProductImage image) {
        return new ImageDto(image.getUrlPath());
    }

    public static List<String> buildStrings(List<FileUpload> images) {
        return images.stream().map(i -> i.getFileName()).collect(Collectors.toList());
    }

    public String getPath() {
        return path;
    }
}
