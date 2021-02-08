package com.hotfixmaker.model;

import java.io.File;

public class SelectedFile {

    public SelectedFile() {}

    public SelectedFile(String name, String path,
                        File file, String pathToCreation) {
        this.name = name;
        this.path = path;
        this.file = file;
        this.pathToCreation = pathToCreation;
    }

    private String name;

    private String path;

    private File file;

    private String pathToCreation;

    public SelectedFile(File file) {
        this.file = file;
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        this.pathToCreation = new File(file.getParent()).getPath().split("classes")[1];
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathToCreation() {
        return pathToCreation;
    }

    public void setPathToCreation(String pathToCreation) {
        this.pathToCreation = pathToCreation;
    }
}
