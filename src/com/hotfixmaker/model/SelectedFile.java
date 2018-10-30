package com.hotfixmaker.model;

import java.io.File;

public class SelectedFile {

    private String name;

    private String path;

    private File file;

    public SelectedFile(File file) {
        this.file = file;
        this.name = file.getName();
        this.path = file.getAbsolutePath();
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
}
