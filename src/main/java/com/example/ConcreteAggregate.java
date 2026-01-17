package com.example;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate {
    public List<File> getImageFiles() {
        return new ArrayList<>(imageFiles);
    }
    private final List<String> extensions;
    private List<File> imageFiles = new ArrayList<>();
    private String directoryPath = "src/main/resources/images";

    public ConcreteAggregate(String formats) {

        this.extensions = List.of(formats.toLowerCase().split(","));
        loadImages();
    }

    public void setDirectory(String path) {
        this.directoryPath = path;
        loadImages();
    }

    private void loadImages() {
        imageFiles.clear();
        File dir = new File(directoryPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    String name = file.getName().toLowerCase();
                    for (String ext : extensions) {
                        if (name.endsWith("." + ext.trim())) {
                            imageFiles.add(file);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Iterator getIterator() {
        return new ImageIterator();
    }

    private class ImageIterator implements Iterator {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < imageFiles.size();
        }

        @Override
        public Image next() {
            if (hasNext()) {
                File file = imageFiles.get(current++);
                return new Image(file.toURI().toString());
            }
            return null;
        }
    }
}