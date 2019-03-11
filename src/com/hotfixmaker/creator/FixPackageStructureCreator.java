package com.hotfixmaker.creator;

import com.hotfixmaker.model.SelectedFile;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class FixPackageStructureCreator {

    public static void process(String targetFolderForClasses, ObservableList<SelectedFile> filesForPacking) throws IOException {
        for (SelectedFile selectedFile : filesForPacking) {
            File folderPathToCreation = new File(targetFolderForClasses, selectedFile.getPathToCreation());

            folderPathToCreation.mkdirs();

            File filePathToCopy = new File(folderPathToCreation.getPath(), selectedFile.getFile().getName());
            Files.copy(selectedFile.getFile().toPath(), filePathToCopy.toPath());
        }
    }
}
