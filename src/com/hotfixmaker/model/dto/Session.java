package com.hotfixmaker.model.dto;

import com.hotfixmaker.model.SelectedFile;
import java.util.List;

public class Session {

    private String name;
    private String targetFolder;
    private String defaultServerAppFolder;
    private List<SelectedFile> files;
    private boolean notRemoveTmpFolder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetFolder() {
        return targetFolder;
    }

    public void setTargetFolder(String targetFolder) {
        this.targetFolder = targetFolder;
    }

    public String getDefaultServerAppFolder() {
        return defaultServerAppFolder;
    }

    public void setDefaultServerAppFolder(String defaultServerAppFolder) {
        this.defaultServerAppFolder = defaultServerAppFolder;
    }

    public List<SelectedFile> getFiles() {
        return files;
    }

    public void setFiles(List<SelectedFile> files) {
        this.files = files;
    }

    public boolean isNotRemoveTmpFolder() {
        return notRemoveTmpFolder;
    }

    public void setNotRemoveTmpFolder(boolean notRemoveTmpFolder) {
        this.notRemoveTmpFolder = notRemoveTmpFolder;
    }
}
